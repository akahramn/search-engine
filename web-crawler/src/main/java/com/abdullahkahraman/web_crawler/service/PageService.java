package com.abdullahkahraman.web_crawler.service;

import com.abdullahkahraman.web_crawler.model.Page;
import com.abdullahkahraman.web_crawler.repository.PageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PageService {

    private final PageRepository pageRepository;
    private final OutboxService outboxService;

    public void save(String url, String text, String title) {
        Page page = new Page();
        page.setLink(url);
        page.setText(text);
        page.setTitle(title);
        Page savedPage = pageRepository.save(page);
        outboxService.createOutbox(savedPage);
    }
}
