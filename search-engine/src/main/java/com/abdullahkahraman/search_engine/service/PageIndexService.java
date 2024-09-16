package com.abdullahkahraman.search_engine.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PageIndexService {

    @KafkaListener(topics = "crawler-operation")
    public void listenGroupFoo(String message) {
        System.out.println("Received Message in group foo: " + message);
    }
}
