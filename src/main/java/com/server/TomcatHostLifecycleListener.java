package com.server;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.autocomplete.Trie;
import com.elasticsearch.ElasticSearchConnection;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TomcatHostLifecycleListener implements ServletContextListener{
    public static Trie trie = new Trie();
    public static ElasticsearchClient esClient;
    @Override
    public void contextInitialized(ServletContextEvent e) {
        ElasticSearchConnection.getConnection();
        Connection connection = DatabaseConnection.getConnection();
        ArrayList<HistoryResult> results = new ArrayList<HistoryResult>();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("select * from history");

            while (resultSet.next()) {
                HistoryResult searchResult = new HistoryResult();
                searchResult.setKeyword(resultSet.getString("query"));
                searchResult.setFrequency(resultSet.getInt("frequency"));
                results.add(searchResult);
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        for (HistoryResult result : results) {
            trie.insertWord(result.getKeyword(), result.getFrequency());
        }
    }
}
