package com.server;

import com.autocomplete.Autocomplete;
import com.autocomplete.Trie;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TomcatHostLifecycleListener implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent e) {
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

    }
}
