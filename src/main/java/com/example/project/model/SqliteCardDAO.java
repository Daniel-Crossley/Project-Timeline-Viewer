package com.example.project.model;

import javafx.scene.image.Image;
import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import java.sql.*;

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
                    + "dateFinished VARCHAR,"
                    + "image BLOB"
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
                Blob imageBlob = resultSet.getBlob("image");
                InputStream inputStream = imageBlob.getBinaryStream();
                Image image = new Image(inputStream);
                Card card = new Card(id, title, description, dateCreated, dateFinished, image);
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
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String dateCreated = resultSet.getString("dateCreated");
                String dateFinished = resultSet.getString("dateFinished");
                Blob imageBlob = resultSet.getBlob("image");
                InputStream inputStream = imageBlob.getBinaryStream();
                Image image = new Image(inputStream);
                Card card = new Card(id, title, description, dateCreated, dateFinished, image);
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
                    "INSERT INTO Projects (projectId, title, description, dateCreated, dateFinished, image) VALUES (?, ?, ?, ?, ?, ?, ?)");
            statement.setInt(1, project.getId());
            statement.setString(2, card.getTitle());
            statement.setString(3, card.getDescription());
            statement.setString(4, card.getDateCreated());
            statement.setString(5, card.getDateFinished());
            Image image = card.getMediaImage();
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);
            byte[] imageBytes = baos.toByteArray();
            statement.setBytes(6, imageBytes);
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
