package com.abdullahkahraman.search_engine.page;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/page")
@RequiredArgsConstructor
public class PageController {

    private final PageService pageService;

    @GetMapping("/search")
    public ResponseEntity<?> search() {
        return ResponseEntity.ok().body("Hello World");
    }

    @PostMapping("/save")
    public ResponseEntity<?> savePage(@RequestBody Page page) {
        pageService.savePage(page);
        return ResponseEntity.ok().body("Hello World");
    }
}
