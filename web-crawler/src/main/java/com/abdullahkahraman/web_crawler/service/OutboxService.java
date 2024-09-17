package com.abdullahkahraman.web_crawler.service;

import com.abdullahkahraman.web_crawler.model.Outbox;
import com.abdullahkahraman.web_crawler.model.Page;
import com.abdullahkahraman.web_crawler.publisher.KafkaPublisher;
import com.abdullahkahraman.web_crawler.repository.OutboxRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OutboxService {

    private final OutboxRepository outboxRepository;
    private final ObjectMapper MAPPER = new ObjectMapper();
    private final KafkaPublisher kafkaPublisher;

    public Outbox createOutbox(Page page) {
        Outbox outbox = new Outbox();
        outbox.setPayload(page.toString());
        outbox.setType("create");
        return outboxRepository.save(outbox);
    }

    public void debeziumDatabaseChange(Page pageIndex) {
        try {
            kafkaPublisher.publish("crawler-operation", new ObjectMapper().writeValueAsString(pageIndex));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
