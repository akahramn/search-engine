package com.abdullahkahraman.web_crawler.service;

import com.abdullahkahraman.web_crawler.model.Outbox;
import com.abdullahkahraman.web_crawler.publisher.KafkaPublisher;
import com.abdullahkahraman.web_crawler.repository.OutboxRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OutboxService {

    private final OutboxRepository outboxRepository;
    private final KafkaPublisher kafkaPublisher;
    private final ObjectMapper MAPPER = new ObjectMapper();

    public Outbox createOutbox(Outbox outbox) {
        return outboxRepository.save(outbox);
    }

    public void debeziumDatabaseChange(Map<String, Object> payload) {
        log.info("Debezium payload: {}", payload);
        try {
            kafkaPublisher.publish("account-created", MAPPER.writeValueAsString(payload));
            var x = MAPPER.writeValueAsString(payload);
            log.info("Debezium payload string: {}", x);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
