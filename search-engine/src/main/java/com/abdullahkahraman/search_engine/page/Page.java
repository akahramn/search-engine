package com.abdullahkahraman.search_engine.page;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Table(name = "page", schema = "searchengine")
@Entity
@Data
@RequiredArgsConstructor
public class Page {
    @Id
    @GeneratedValue
    private Long record_id;

    @Column(name = "title")
    private String title;

    @Column(name = "link")
    private String link;

    @Column(name = "text")
    private String text;
}
