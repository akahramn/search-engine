package com.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseService {
    Connection connection;

    public DatabaseService() {
        connection = DatabaseConnection.getConnection();
    }

    public ResultSet getPagesWithKeyword(String keyword) throws SQLException {
        ResultSet resultSet = null;
        try {
            resultSet = connection.createStatement().executeQuery("select title, link, (length(lower(text))-length(replace(lower(text), '" + keyword + "', \"\")))/length('" + keyword + "') as countoccurence from page order by countoccurence desc limit 30;");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return resultSet;
    }

    public void saveHistory(String keyword) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("Insert into history values(?, ?, ?, ?)");
            preparedStatement.setString(1, keyword);
            preparedStatement.setString(2, "http://localhost:8080/SearchEngineAccio/Search?keyword="+keyword);
            preparedStatement.setInt(3, 0);
            preparedStatement.setInt(4, 0);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            rollbackTransaction();
            sqlException.printStackTrace();
        }
    }


    public void updateHistory(Integer frequency, Integer recordId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update history set frequency = ? where record_id = ?");
            preparedStatement.setInt(1,frequency + 1);
            preparedStatement.setInt(2, recordId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            rollbackTransaction();
            e.printStackTrace();
        }
    }

    public ResultSet getHistoryWithKeyword(String keyword) {
        try {
            return connection.createStatement().executeQuery("select * from history where query like '" + keyword + "%' limit 1;");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }

    private void rollbackTransaction() {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace(); // Hataları loglayın
            }
        }
    }


}
