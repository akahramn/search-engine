package com.abdullahkahraman.web_crawler.publisher;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void publish(String topicName, String message) {
        kafkaTemplate.send(topicName, message);
    }
}
