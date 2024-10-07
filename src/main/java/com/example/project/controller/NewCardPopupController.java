package com.example.project.controller;

import com.example.project.model.Card;
import com.example.project.model.SqliteCardDAO;
import com.example.project.model.Project;
import com.example.project.model.User;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.scene.image.Image;

import java.time.LocalDate;

public class NewCardPopupController {

    private SqliteCardDAO cardDAO;

    @FXML
    private TextField titleField;

    @FXML
    private TextField descriptionField;

    @FXML
    private DatePicker dateCreatedField;

    @FXML
    private DatePicker datePublishedField;

    @FXML
    private TextField imageField;

    @FXML
    private Button addCardButton;

    private Card newCard;
    private Stage popupStage;
    private Project project;

    private SimpleObjectProperty<Image> image = new SimpleObjectProperty<>();

    public void setProject(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }

    public void setStage(Stage stage) {
        this.popupStage = stage;
    }

    public NewCardPopupController() {
        cardDAO = new SqliteCardDAO();
    }

    @FXML
    private void onAddCard() {
        //Create a new Card object with the inputted data
        int id = 1;
        String title = titleField.getText();
        String description = descriptionField.getText();
        LocalDate dateCreated = dateCreatedField.getValue();
        LocalDate datePublished = datePublishedField.getValue();
        String imageUrl = imageField.getText();

        newCard = new Card(title, description, dateCreated.toString(), datePublished.toString(), image.get());

        cardDAO.addCard(newCard, getProject());

        popupStage.close();
    }

    @FXML
    private void onChooseImage() {
        //Create a FileChooser to select the image
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        //Show the file chooser dialog
        File selectedImageFile = fileChooser.showOpenDialog(popupStage);
        if (selectedImageFile != null) {
            //Set the file path to the imageField
            imageField.setText(selectedImageFile.getAbsolutePath());
            image.set(new Image(selectedImageFile.toURI().toString()));

        }
    }
}