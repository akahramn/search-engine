package com.abdullahkahraman.search_engine.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.abdullahkahraman.search_engine.model.PageIndex;
import com.abdullahkahraman.search_engine.repository.PageIndexRepository;
import com.abdullahkahraman.search_engine.util.ESUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PageIndexService {

    private final PageIndexRepository pageIndexRepository;
    private final ElasticsearchClient elasticsearchClient;

    public void savePageIndex(PageIndex pageIndex) {
        pageIndexRepository.save(pageIndex);
    }

    public void find() {
        pageIndexRepository.findAll();
    }

    public List<PageIndex> fetchPageIndexesWithKeyword(String keyword) {
        SearchResponse<PageIndex> response = null;
        try {
            //sorgu olustur
            Supplier<Query> querySupplier = ESUtil.buildQueryForKeyword(keyword);

            //sorguyu calistir ve cevabi alir
            response = elasticsearchClient.search(q -> q.index("page_index")
                    .query(querySupplier.get()), PageIndex.class);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return extractItemsFromResponse(response);
    }

    public List<PageIndex> extractItemsFromResponse(SearchResponse<PageIndex> response) {
        return response
                .hits()
                .hits()
                .stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }

    @KafkaListener(topics = "crawler-operation")
    public void listenGroupFoo(String message) throws JsonProcessingException {
        System.out.println("Received Message in group foo: " + message);

        PageIndex pageIndex = new ObjectMapper().readValue(message, PageIndex.class);
        savePageIndex(pageIndex);
    }
}
