package com.abdullahkahraman.search_engine.repository;

import com.abdullahkahraman.search_engine.model.PageIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageIndexRepository extends ElasticsearchRepository<PageIndex, String> {

    PageIndex findByTitle(String title);
}
