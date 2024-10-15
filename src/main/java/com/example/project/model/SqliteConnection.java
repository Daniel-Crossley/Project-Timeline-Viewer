package com.example.project.model;


import java.sql.*;

/**
 * Used to establish connection to SQLite database
 */
public class SqliteConnection {
    private static Connection instance = null;

    /**
     * Set ups connection with sqlite driver
     */
    private SqliteConnection() {
        String url = "jdbc:sqlite:contacts.db";
        try {
            instance = DriverManager.getConnection(url);
        } catch (SQLException sqlEx) {
            System.err.println(sqlEx);
        }
    }

    /**
     * get instance of db
     * @return instance of db
     */
    public static Connection getInstance() {
        if (instance == null) {
            new SqliteConnection();
        }
        return instance;
    }
}
