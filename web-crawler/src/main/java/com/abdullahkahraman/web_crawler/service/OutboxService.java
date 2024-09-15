package com.abdullahkahraman.web_crawler.service;

import com.abdullahkahraman.web_crawler.model.Outbox;
import com.abdullahkahraman.web_crawler.publisher.KafkaPublisher;
import com.abdullahkahraman.web_crawler.repository.OutboxRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import static io.debezium.data.Envelope.Operation;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OutboxService {

    private final OutboxRepository outboxRepository;
    private final ObjectMapper MAPPER = new ObjectMapper();
    private final KafkaPublisher kafkaPublisher;

    public Outbox createOutbox(Outbox outbox) {
        return outboxRepository.save(outbox);
    }

    public void debeziumDatabaseChange(Map<String, Object> pageData, String operation) {
        Outbox outbox = new Outbox();
        outbox.setType(operation);
        outbox.setPayload(pageData.toString());

        try {
            kafkaPublisher.publish("crawler-operation", MAPPER.writeValueAsString(outbox));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
