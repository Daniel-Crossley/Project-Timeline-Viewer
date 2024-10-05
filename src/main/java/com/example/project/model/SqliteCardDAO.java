package com.example.project.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SqliteCardDAO {
    private Connection connection;

    /**
     * Create instance of CardDAO
     */
    public SqliteCardDAO(){
        connection = SqliteConnection.getInstance();
        createTable();
    }

    /**
     * Create Cards table
     */
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

    /**
     * @param id primary key of card
     * @return a card object
     */
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
                Card card = new Card(title, description, dateCreated, dateFinished);
                return card;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get all cards owned by project
     * @param project project to add cards to
     */
    public void getCards(Project project) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Cards WHERE projectId = ?");
            statement.setInt(1, project.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String dateCreated = resultSet.getString("dateCreated");
                String dateFinished = resultSet.getString("dateFinished");
                Card card = new Card(title, description, dateCreated, dateFinished);
                project.addCard(card);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param card card object
     * @param project project object for foreign key
     */
    public void addCard(Card card, Project project) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Cards (projectId, title, description, dateCreated, dateFinished) VALUES (?, ?, ?, ?, ?)");
            statement.setInt(1, project.getId());
            statement.setString(2, card.getTitle());
            statement.setString(3, card.getDescription());
            statement.setString(4, card.getDateCreated());
            statement.setString(5, card.getDateFinished());
            statement.executeUpdate();
            // Set the id of the new project
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                card.setId(generatedKeys.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
