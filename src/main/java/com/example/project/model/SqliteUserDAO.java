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

    /**
     * Gets user based on the primary key username
     * @param username the primary key
     * @return A User object
     */
    public User getUser(String username){
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM UserAccounts WHERE username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                return new User(username, password, email);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Adds user to database
     * @param user User object
     * @return True if upload was successful False if otherwise
     */
    public boolean AddUser(User user){
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO UserAccounts (username, password, email) VALUES (?, ?, ?)");
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
