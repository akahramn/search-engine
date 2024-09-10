package com.abdullahkahraman.search_engine.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PageService {

    private final PageRepository pageRepository;

    public void savePage(Page page) {
        pageRepository.save(page);
    }

}
