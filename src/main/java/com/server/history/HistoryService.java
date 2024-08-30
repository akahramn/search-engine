package com.server.history;

import com.db.DatabaseService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HistoryService {
    DatabaseService dbService;

    public HistoryService() {
        this.dbService = new DatabaseService();
    }

    public void saveHistory(String keyword) {
        ResultSet resultSet = getHistory(keyword);
        try {
            if (resultSet.isBeforeFirst()) {
                if (resultSet.next()) {
                    dbService.updateHistory(resultSet.getInt("frequency"), resultSet.getInt("record_id"));
                }
            } else {
                //add keyword if dont exist into history table
                dbService.saveHistory(keyword);

//                esClient.index(i -> i
//                        .index("history")
//                        .id(keyword)
//                        .document(new HistoryResult(keyword, 1)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getHistory(String keyword) {
        return dbService.getHistoryWithKeyword(keyword);
    }
}
