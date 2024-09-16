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
                    + "username VARCHAR,"
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
                int likes = resultSet.getInt("likes");
                String colour = resultSet.getString("colour");
                String tags = resultSet.getString("tags");
                Project project = new Project(id, title, description, dateCreated, dateFinished, visibility, colour, likes, tags);
                return project;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getProjects(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Projects WHERE username = ?");
            statement.setString(1, user.getUsername());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String dateCreated = resultSet.getString("dateCreated");
                String dateFinished = resultSet.getString("dateFinished");
                boolean visibility = resultSet.getInt("visiblity") != 0;
                int likes = resultSet.getInt("likes");
                String colour = resultSet.getString("colour");
                String tags = resultSet.getString("tags");
                Project project = new Project(id, title, description, dateCreated, dateFinished, visibility, colour, likes, tags);
                user.addProject(project);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addProject(Project project, User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Projects (username, title, description, dateCreated, dateFinsihed, visibility, likes, colour, tags) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, user.getUsername());
            statement.setString(2, project.getTitle());
            statement.setString(3, project.getDescription());
            statement.setString(4, project.getDateCreated());
            statement.setString(5, project.getDateFinished());
            statement.setBoolean(6, project.isVisible());
            statement.setInt(7, project.getLikes());
            statement.setString(8, project.getColour());
            statement.setString(9, project.getTags());
            statement.executeUpdate();
            // Set the id of the new project
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                project.setId(generatedKeys.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
