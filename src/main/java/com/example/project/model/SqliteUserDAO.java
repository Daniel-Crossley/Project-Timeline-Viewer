package com.example.project.model;

import com.example.project.model.SqliteConnection;


import java.sql.*;


public class SqliteUserDAO {
    private Connection connection;

    public SqliteUserDAO(){
        connection = SqliteConnection.getInstance();
        createTable();
    }

    private void createTable(){
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS UserAccounts ("
                    + "username VARCHAR PRIMARY KEY,"
                    + "password VARCHAR NOT NULL,"
                    + "email VARCHAR NOT NULL UNIQUE"
                    + ")";
            statement.execute(query);
        } catch (Exception e){
            e.printStackTrace();
        }
    }



    public boolean verifyUser(String username, String Password) {
        String selectSQL = "SELECT * FROM UserAccounts WHERE username = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(selectSQL)) {
            statement.setString(1, username);
            statement.setString(2, Password);
            ResultSet resultSet = statement.executeQuery();
            System.out.println(resultSet);
            // true if user exists and password matches
            return resultSet.next();
        } catch (SQLException e) {
            System.err.println("SQL error code: " + e.getErrorCode());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Message: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

}
