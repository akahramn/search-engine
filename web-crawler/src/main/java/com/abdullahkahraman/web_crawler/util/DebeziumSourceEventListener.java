package com.abdullahkahraman.web_crawler.util;

import com.abdullahkahraman.web_crawler.model.Page;
import com.abdullahkahraman.web_crawler.service.PageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.debezium.config.Configuration;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.Json;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class DebeziumSourceEventListener {

    private final Executor executor;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final PageService pageService;

    //DebeziumEngine serves as an easy-to-use wrapper around any Debezium connector
    private final DebeziumEngine<ChangeEvent<String, String>> debeziumEngine;

    public DebeziumSourceEventListener(Configuration postgresConnector, PageService pageService) {
        // Create a new single-threaded executor.
        this.executor = Executors.newSingleThreadExecutor();
        // Create a new DebeziumEngine instance.
        this.debeziumEngine = DebeziumEngine.create(Json.class)
                .using(postgresConnector.asProperties())
                //This is where your CDC events will be passed to
                .notifying(this::handleEvent)
                .build();
        this.pageService = pageService;
    }

    private void handleEvent(ChangeEvent<String, String> event) {
        String value = event.value();
        Map<String, Object> map = new HashMap<>();
        JsonNode payload;
        try {
            payload = objectMapper.readTree(value).get("payload");
        }  catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        //getting saved value
        JsonNode after = payload.get("after");
        if (after != null) {
            Page pageIndex = new Page();
            try {
                pageIndex = new ObjectMapper().treeToValue(after, Page.class);
            } catch (JsonProcessingException e) {
                System.out.println(e.getMessage());
            }
            pageService.debeziumDatabaseChange(pageIndex);
        }
    }

    @PostConstruct
    private void start() {
        this.executor.execute(debeziumEngine);
    }

    @PreDestroy
    private void stop() throws IOException {
        if (this.debeziumEngine != null) {
            this.debeziumEngine.close();
        }
    }
}
