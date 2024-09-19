package com.abdullahkahraman.search_engine.controller;

import com.abdullahkahraman.search_engine.service.PageIndexService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/page-index")
@RequiredArgsConstructor
public class PageIndexController {

    private final PageIndexService pageIndexService;

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam("query") String query) {
        return ResponseEntity.ok().body(pageIndexService.fetchPageIndexesWithKeyword(query));
    }
}
