package com.abdullahkahraman.web_crawler;

import com.abdullahkahraman.web_crawler.model.Outbox;
import com.abdullahkahraman.web_crawler.service.OutboxService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/outbox")
@RequiredArgsConstructor
public class OutboxController {

    private final OutboxService outboxService;

    @PostMapping
    public String post() {
        outboxService.createOutbox(new Outbox("test", "test"));
        return "Posted";
    }
}
