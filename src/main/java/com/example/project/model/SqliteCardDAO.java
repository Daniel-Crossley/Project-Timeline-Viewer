package com.example.project.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SqliteCardDAO {
    private Connection connection;

    public SqliteCardDAO(){
        connection = SqliteConnection.getInstance();
        createTable();
    }

    private void createTable(){
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS Cards ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "projectId INTEGER NOT NULL,"
                    + "title VARCHAR,"
                    + "description VARCHAR,"
                    + "dateCreated VARCHAR,"
                    + "dateFinished VARCHAR"
                    + ")";
            statement.execute(query);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Card getCard(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Cards WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String dateCreated = resultSet.getString("dateCreated");
                String dateFinished = resultSet.getString("dateFinished");
                Card card = new Card(id, title, description, dateCreated, dateFinished);
                return card;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getCards(Project project) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Cards WHERE projectId = ?");
            statement.setInt(1, project.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String dateCreated = resultSet.getString("dateCreated");
                String dateFinished = resultSet.getString("dateFinished");
                Card card = new Card(id, title, description, dateCreated, dateFinished);
                project.addCard(card);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
