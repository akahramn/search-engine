package com.abdullahkahraman.web_crawler.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "outbox", schema = "public")
@Data
public class Outbox {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "payload", columnDefinition = "TEXT")
    private String payload;

    public Outbox() {
    }

    public Outbox(String type, String payload) {
        this.type = type;
        this.payload = payload;
    }
}
