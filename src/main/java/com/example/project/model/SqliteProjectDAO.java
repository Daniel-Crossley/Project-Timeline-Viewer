package com.example.project.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SqliteProjectDAO {
    private Connection connection;

    /**
     * Create instance of project DAO
     */
    public SqliteProjectDAO(){
        connection = SqliteConnection.getInstance();
        createTable();
    }

    /**
     * Creates the project table inside DB
     */
    private void createTable(){
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS Projects ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "username VARCHAR,"
                    + "title VARCHAR,"
                    + "description VARCHAR,"
                    + "dateCreated DATE,"
                    + "dateFinished DATE,"
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

    /**
     * @param id primary key of project
     * @return returns project object with given id
     */
    public Project getProject(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Projects WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                Date dateCreated = new java.sql.Date(resultSet.getLong("dateCreated"));
                Date dateFinished = resultSet.getDate("dateFinished");
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

    /**
     * Adds projects to user objects for every
     * project they have created
     * @param user User object to add projects to
     */
    public void getProjects(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Projects WHERE username = ?");
            statement.setString(1, user.getUsername());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                Date dateCreated = new java.sql.Date(resultSet.getLong("dateCreated"));
                Date dateFinished = null;
                boolean visibility = resultSet.getInt("visibility") != 0;
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

    /**
     * @param project the project to be added
     * @param user the use to add the foreign key
     */
    public void addProject(Project project, User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Projects (username, title, description, dateCreated, dateFinished, visibility, likes, colour, tags) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, user.getUsername());
            statement.setString(2, project.getTitle());
            statement.setString(3, project.getDescription());
            statement.setDate(4, project.getDateCreated());
            statement.setDate(5, project.getDateFinished());
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

    /**
     *
     * @param titleSearch
     * @param  date
     * @return list of projects containing title
     */
    public List<Project> getSearchProjects(String titleSearch , LocalDate date) {
        List<Project> projects = new ArrayList<>();
        try {
            PreparedStatement statement = null;
            if (date == null && (titleSearch == null || titleSearch.isEmpty())) {
                statement = connection.prepareStatement("SELECT * FROM Projects");
            } else if (date == null) {
                statement = connection.prepareStatement("SELECT * FROM Projects WHERE title LIKE ?");
                statement.setString(1, "%" + titleSearch + "%");
            } else if (titleSearch == null || titleSearch.isEmpty()) {
                statement = connection.prepareStatement("SELECT * FROM Projects WHERE " +
                        "DATETIME(CAST(dateCreated AS INTEGER) / 1000, 'unixepoch') " +
                        "BETWEEN DATETIME(?) AND DATETIME('now')");
                statement.setString(1, date.toString());
            } else {
                statement = connection.prepareStatement("SELECT * FROM Projects WHERE " +
                        "DATETIME(CAST(dateCreated AS INTEGER) / 1000, 'unixepoch') " +
                        "BETWEEN DATETIME(?) AND DATETIME('now')" +
                        " AND title LIKE ?");
                statement.setString(1, date.toString());
                statement.setString(2, "%" + titleSearch + "%");
            }
            System.out.println("Date: " + date);
            System.out.println("Title Search: " + titleSearch);
            System.out.println("SQL: " + statement.toString());
            try (ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()){
                    int id = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    String description = resultSet.getString("description");
                    Date dateCreated = new java.sql.Date(resultSet.getLong("dateCreated"));
                    Date dateFinished = null;
                    boolean visibility = resultSet.getInt("visibility") != 0;
                    int likes = resultSet.getInt("likes");
                    String colour = resultSet.getString("colour");
                    String tags = resultSet.getString("tags");
                    Project project = new Project(id, title, description, dateCreated, dateFinished, visibility, colour, likes, tags);
                    projects.add(project);
                    System.out.println("Found project: " + title);
                }
            }
            System.out.println("Total projects found: " + projects.size());
        } catch (Exception e) {
            System.out.println("Error in getSearchProjects: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
        return projects;
    }


}


