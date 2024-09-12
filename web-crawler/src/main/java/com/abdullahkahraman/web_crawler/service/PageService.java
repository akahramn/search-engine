package com.abdullahkahraman.web_crawler.service;

import com.abdullahkahraman.web_crawler.model.Page;
import com.abdullahkahraman.web_crawler.repository.PageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PageService {

    private final PageRepository pageRepository;

    public void save(String url, String text) {
        Page page = new Page();
        page.setLink(url);
        page.setText(text);
        pageRepository.save(page);
    }
}
