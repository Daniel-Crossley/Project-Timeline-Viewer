package com.example.project.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public Project getProject(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Projects WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String dateCreated = resultSet.getString("dateCreated");
                String dateFinished = resultSet.getString("dateFinished");
                boolean visibility = resultSet.getInt("visiblity") != 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
