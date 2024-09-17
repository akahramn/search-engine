package com.abdullahkahraman.search_engine.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "page_index")
@Data
public class PageIndex {
    @Id
    private String id;
    @Field(name = "title", type = FieldType.Text)
    private String title;
    @Field(name = "link", type = FieldType.Text)
    private String link;
    @Field(name = "text", type = FieldType.Text)
    private String text;
}
