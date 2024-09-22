package com.abdullahkahraman.web_crawler.service;

import com.abdullahkahraman.web_crawler.model.Page;
import com.abdullahkahraman.web_crawler.publisher.KafkaPublisher;
import com.abdullahkahraman.web_crawler.repository.PageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PageService {

    private final PageRepository pageRepository;
    private final KafkaPublisher kafkaPublisher;

    public void save(String url, String text, String title) {
        Page page = new Page();
        page.setLink(url);
        page.setText(text);
        page.setTitle(title);
        pageRepository.save(page);
    }

    public void debeziumDatabaseChange(Page pageIndex) {
        try {
            kafkaPublisher.publish("crawler-operation", new ObjectMapper().writeValueAsString(pageIndex));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
