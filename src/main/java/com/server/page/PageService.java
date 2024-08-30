package com.server.page;

import com.db.DatabaseService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PageService {

    DatabaseService dbService;

    public PageService() {
        this.dbService = new DatabaseService();
    }

    public List<SearchResult> getPages(String keyword) {
        ArrayList<SearchResult> results = new ArrayList<>();
        try {
            ResultSet resultSet = dbService.getPagesWithKeyword(keyword);

            while (resultSet.next()){
                SearchResult searchResult = new SearchResult();
                searchResult.setTitle(resultSet.getString("title"));
                searchResult.setLink(resultSet.getString("link"));
                results.add(searchResult);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return results;
    }
}
