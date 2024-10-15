package com.example.project.model;

import com.example.project.OOD.ISqliteDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Used to retrieve, update and upload project information for database
 */
public class SqliteProjectDAO implements ISqliteDAO {
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
    public void createTable(){
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
                String dateCreated = resultSet.getString("dateCreated");
                String dateFinished = resultSet.getString("dateFinished");
                boolean visibility = resultSet.getInt("visiblity") != 0;
                int likes = resultSet.getInt("likes");
                String colour = resultSet.getString("colour");
                String tags = resultSet.getString("tags");
                List<String> tagsList = Arrays.asList(tags.split(",\\s*"));
                Project project = new Project(id, title, description, dateCreated, dateFinished, visibility, colour, likes, tagsList);
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
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Projects WHERE username = ? ORDER BY dateCreated ASC");
            statement.setString(1, user.getUsername());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String dateCreated = resultSet.getString("dateCreated");
                String dateFinished = resultSet.getString("dateFinished");
                boolean visibility = resultSet.getInt("visibility") != 0;
                int likes = resultSet.getInt("likes");
                String colour = resultSet.getString("colour");
                String tags = resultSet.getString("tags");
                List<String> tagsList = Arrays.asList(tags.split(",\\s*"));
                Project project = new Project(id, title, description, dateCreated, dateFinished, visibility, colour, likes, tagsList);
                user.addProject(project);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param project the project to update with
     */
    public void updateProject(Project project) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE Projects SET title = ?, " +
                            "description = ?, " +
                            "dateCreated = ?, " +
                            "dateFinished = ?, " +
                            "visibility = ?, " +
                            "likes = ?," +
                            "colour = ?," +
                            "tags = ?" +
                            "WHERE id = ?");
            statement.setString(1, project.getTitle());
            statement.setString(2, project.getDescription());
            statement.setString(3, project.getDateCreated());
            statement.setString(4, project.getDateFinished());
            statement.setBoolean(5, project.isVisible());
            statement.setInt(6, project.getLikes());
            statement.setString(7, project.getColour());
            statement.setString(8, String.join(", ", project.getTags()));
            statement.setInt(9, project.getId());
            statement.executeUpdate();
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
            statement.setString(4, project.getDateCreated());
            statement.setString(5, project.getDateFinished());
            statement.setBoolean(6, project.isVisible());
            statement.setInt(7, project.getLikes());
            statement.setString(8, project.getColour());
            statement.setString(9, String.join(", ", project.getTags()));
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
     * @param SearchTags
     * @return list of projects containing title
     */
    public List<Project> getSearchProjects(String titleSearch , String date,String SearchTags) {
        StringBuilder queryBuilder = new StringBuilder("");
        List<Project> projects = new ArrayList<>();
        List<String> parameters = new ArrayList<>();


        if (SearchTags != null && !SearchTags.isEmpty()) {
            String[] tags = SearchTags.split(",\\s*");
            for (String tag : tags) {
                queryBuilder.append(" AND tags LIKE ?");
                parameters.add("%" + tag.trim() + "%");
            }
        }

        try {
            PreparedStatement statement = null;
            //Querry for blank inputs
            if (date == "none" && (titleSearch == null || titleSearch.isEmpty()) && SearchTags == null) {
                statement = connection.prepareStatement("SELECT * FROM Projects WHERE visibility == 1");

            }
            //search for title only
            else if (titleSearch != null && date =="none" && SearchTags.isEmpty()) {
                statement = connection.prepareStatement("SELECT * FROM Projects WHERE title LIKE ? AND visibility == 1");
                statement.setString(1, "%" + titleSearch + "%");

            }
            //search for date only
            else if (titleSearch == null || titleSearch.isEmpty() && SearchTags.isEmpty() && date !="none") {
                //search for date only
                statement = connection.prepareStatement("SELECT * FROM Projects WHERE " +
                        "dateCreated >= ? AND dateCreated <= date('now') " +
                        "AND visibility = 1");
                statement.setString(1, date);
            //tag search
            } else if (titleSearch == null || titleSearch.isEmpty() && date == "none" && !SearchTags.isEmpty()) {
                statement = connection.prepareStatement("SELECT * FROM Projects WHERE visibility = 1" + queryBuilder.toString());
                for (int i=0; i<parameters.size();i++){
                    statement.setString(i + 1, parameters.get(i));
                }
            }
            //search for date, tag and title
            else {

                statement = connection.prepareStatement("SELECT * FROM Projects WHERE " +
                        "dateCreated >= ? AND dateCreated <= date('now') " +
                        "AND title LIKE ? AND visibility = 1"+ queryBuilder.toString());

                statement.setString(1, date);
                statement.setString(2, "%" + titleSearch + "%");
                for (int i=0; i<parameters.size();i++){
                    statement.setString(i + 3, parameters.get(i));
                }
            }
            //System.out.println("Date: " + date);
            //System.out.println("Title Search: " + titleSearch);
            //System.out.println("Selected tags: " + SearchTags);
            System.out.println("SQL: " + statement.toString());
            try (ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()){
                    int id = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    String description = resultSet.getString("description");
                    String dateCreated = resultSet.getString("dateCreated");
                    String dateFinished = resultSet.getString("dateFinished");
                    boolean visibility = resultSet.getInt("visibility") != 0;
                    int likes = resultSet.getInt("likes");
                    String colour = resultSet.getString("colour");
                    String tags = resultSet.getString("tags");
                    Project project = new Project(id, title, description, dateCreated, dateFinished, visibility, colour, likes, Collections.singletonList(tags));
                    projects.add(project);
                    System.out.println("Found project: " + title);
                    System.out.println("Found end: " + dateFinished);
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
