package com.abdullahkahraman.search_engine.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.abdullahkahraman.search_engine.dto.SearchResponseDto;
import com.abdullahkahraman.search_engine.mapper.PageIndexMapper;
import com.abdullahkahraman.search_engine.model.PageIndex;
import com.abdullahkahraman.search_engine.repository.PageIndexRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    public List<SearchResponseDto> fetchPageIndexesWithKeyword(String keyword) {
        List<SearchResponseDto> response = new ArrayList<>();
        try {
            SearchResponse<PageIndex> searchResponse = elasticsearchClient.search(s -> s
                            .index("page_index")
                            .query(q -> q
                                    .multiMatch(v -> v
                                            .fields("text")
                                            .query(keyword)
                                            .operator(Operator.And)
                                            .type(TextQueryType.BestFields)
                                            .fuzziness("2")
                                            .fuzzyTranspositions(true)
                                            .minimumShouldMatch("3")
                                    )
                            ),
                    PageIndex.class
            );
            // Sonuçları işleme
            List<PageIndex> pageIndexList = extractItemsFromResponse(searchResponse);

            for (PageIndex pageIndex : pageIndexList) {
                SearchResponseDto searchResponseDto = PageIndexMapper.INSTANCE.pageIndexToSearchResponseDto(pageIndex);
                response.add(searchResponseDto);
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
        if (pageIndex != null && pageIndex.getId() != null) {
            savePageIndex(pageIndex);
        }

    }
}
