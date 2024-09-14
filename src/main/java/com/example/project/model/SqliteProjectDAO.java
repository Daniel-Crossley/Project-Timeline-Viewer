package com.example.project.model;

import java.sql.Connection;
import java.sql.Statement;

public class SqliteProjectDAO {
    private Connection connection;

    public SqliteProjectDAO(){
        connection = SqliteConnection.getInstance();
        createTable();
    }

    private void createTable(){
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS Projects ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "title VARCHAR,"
                    + "description VARCHAR,"
                    + "dateCreated VARCHAR,"
                    + "dateFinished VARCHAR,"
                    + "visibility INTEGER,"
                    + "likes INTEGER,"
                    + "colour VARCHAR,"
                    + "tags VARCHAR"
                    + ")";
            statement.execute(query);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
