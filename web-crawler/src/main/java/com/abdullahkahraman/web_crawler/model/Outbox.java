package com.abdullahkahraman.web_crawler.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "outbox")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Outbox {
    @Id
    @GeneratedValue
    private String id;

    @Column(name = "type")
    private String type;

    @Column(name = "payload")
    private String payload;
}
