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
    @Column(name = "id")
    private Long id;

    @Column(name = "link", length = 1000)
    private String link;

    @Column(name = "text", length = 1000)
    private String text;

    @Column(name = "title", length = 1000)
    private String title;
}
