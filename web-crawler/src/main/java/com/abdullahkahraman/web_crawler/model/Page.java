package com.abdullahkahraman.web_crawler.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "page", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
public class Page {
    @Id
    @GeneratedValue
    @Column(name = "record_id")
    private Long recordId;

    @Column(name = "link")
    private String link;

    @Column(name = "text")
    private String text;

    @Column(name = "title")
    private String title;
}
