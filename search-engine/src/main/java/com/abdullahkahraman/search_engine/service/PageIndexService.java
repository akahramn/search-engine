package com.abdullahkahraman.search_engine.service;

import com.abdullahkahraman.search_engine.model.PageIndex;
import com.abdullahkahraman.search_engine.repository.PageIndexRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PageIndexService {

    private final PageIndexRepository pageIndexRepository;

    public void savePageIndex(PageIndex pageIndex) {
        pageIndexRepository.save(pageIndex);
    }

    public void find() {
        pageIndexRepository.findAll();
    }

    @KafkaListener(topics = "crawler-operation")
    public void listenGroupFoo(String message) throws JsonProcessingException {
        System.out.println("Received Message in group foo: " + message);

        PageIndex pageIndex = new ObjectMapper().readValue(message, PageIndex.class);
        savePageIndex(pageIndex);
    }
}
