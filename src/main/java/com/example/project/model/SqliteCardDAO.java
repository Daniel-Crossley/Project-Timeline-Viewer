package com.example.project.model;

import com.example.project.OOJ.ISqliteDAO;
import javafx.scene.image.Image;
import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Used to handle database functions relating to retrieving, updating and uploading card information
 */
public class SqliteCardDAO implements ISqliteDAO {
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
    public void createTable(){
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
                Card card = new Card(title, description, dateCreated, dateFinished, image);
                return card;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get all cards owned by project
     *
     * @param project project to add cards to
     */
    public List<Card> getCards(Project project) {
        List<Card> listOfCards = new ArrayList<>();

        String query = "SELECT * FROM Cards WHERE projectId = ? ORDER BY dateCreated ASC";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, project.getId());

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String title = resultSet.getString("title");
                    String description = resultSet.getString("description");
                    String dateCreated = resultSet.getString("dateCreated");
                    String dateFinished = resultSet.getString("dateFinished");

                    byte[] imageBytes = resultSet.getBytes("image");
                    Image image = null;
                    if (imageBytes != null) {
                        try (InputStream inputStream = new ByteArrayInputStream(imageBytes)) {
                            image = new Image(inputStream);
                        }
                    }

                    Card card = new Card(title, description, dateCreated, dateFinished, image);
                    listOfCards.add(card);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

        return listOfCards;
    }

    /**
     * @param card the card to update with
     */
    public void updateCard(Card card) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE Cards SET title = ?, " +
                            "description = ?, " +
                            "dateCreated = ?, " +
                            "dateFinished = ?, " +
                            "image = ?, " +
                            "WHERE id = ?");
            statement.setString(1, card.getTitle());
            statement.setString(2, card.getDescription());
            statement.setString(3, card.getDateCreated());
            statement.setString(4, card.getDateFinished());
            Image image = card.getMediaImage();
            byte[] imageBytes = null;
            if (image != null) {
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
                try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                    ImageIO.write(bufferedImage, "png", baos);
                    imageBytes = baos.toByteArray();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            statement.setBytes(5, imageBytes);
            statement.setInt(6, card.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param card card object
     * @param project project object for foreign key
     */
    public void addCard(Card card, Project project) {
        String query = "INSERT INTO Cards (projectId, title, description, dateCreated, dateFinished, image) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, project.getId());
            statement.setString(2, card.getTitle());
            statement.setString(3, card.getDescription());
            statement.setString(4, card.getDateCreated());
            statement.setString(5, card.getDateFinished());

            Image image = card.getMediaImage();
            byte[] imageBytes = null;
            if (image != null) {
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
                try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                    ImageIO.write(bufferedImage, "png", baos);
                    imageBytes = baos.toByteArray();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            statement.setBytes(6, imageBytes);

            statement.executeUpdate();

            // Retrieve the generated key (id)
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    card.setId(generatedKeys.getInt(1));  // Set the id of the new card
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();  // Consider logging or throwing a custom exception
        }
    }

}
