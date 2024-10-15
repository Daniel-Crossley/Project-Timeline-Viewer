package com.example.project.model;


import com.example.project.OOD.ISqliteDAO;

import java.sql.*;

/**
 * Used to retrieve, update and upload user information for database
 */
public class SqliteUserDAO implements ISqliteDAO {
    private Connection connection;

    /**
     * Create instance of userDAO
     */
    public SqliteUserDAO(){
        connection = SqliteConnection.getInstance();
        createTable();
    }

    /**
     * Creates table for User accounts
     */
    public void createTable(){
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
     * Gets user based on the secondary key email
     * @param email the primary key
     * @return A User object
     */
    public User getEmail(String email){
        try {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM UserAccounts WHERE email = ?");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String password = resultSet.getString("password");
                String username = resultSet.getString("username");
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
    public boolean addUser(User user){
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
