package com.db.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;

public class ElasticSearchService {
    ElasticsearchClient esClient;
    
    public ElasticSearchService() {
        esClient = ElasticSearchConnection.getConnection();
    }

    //LogStash kullanımı arastırılacak
}
