package com.abdullahkahraman.web_crawler.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "outbox", schema = "public")
@Data
public class Outbox {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "payload")
    private String payload;

    public Outbox() {
    }

    public Outbox(String type, String payload) {
        this.type = type;
        this.payload = payload;
    }
}
