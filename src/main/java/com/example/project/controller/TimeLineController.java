package com.example.project.controller;

import com.example.project.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import com.example.project.model.Project;
import com.example.project.model.Card;

public class TimeLineController {


    private User user;
    private Project project;

    public void initialize(int id, String title, String description, String dateCreated, String dateCompleted, String colour, int likes) {
        // Initialize the model

        // Set initial UI state
        updateView();
    }

    public void setUser(User user){
        this.user = user;
    }
    public void setProject(Project project){
        this.project = project;
    }

    /**
     * Updates the timeline view based on stored data in timeline
     */
    private void updateView() {
        projectTitle.setText(project.getTitle());
        TopBar.setStyle("-fx-background-color: " + project.getColour() + ";");

        // Update other UI elements as needed
        if (project.getListOfCards().isEmpty()) {
            cardStatus.setText("No cards in this project, please click add new.");
        } else {
            cardStatus.setText(project.getDescription());
            addCardsToTimeline();
        }
    }

    /**
     * Temporary method for returning to timeline
     * @param actionEvent Click action
     */
    @FXML
    private void onReturnClick(ActionEvent actionEvent) {
        navigateTo("Dashboard.fxml", actionEvent);
    }

    /**
     * Temporary method for editing timeline details
     * @param actionEvent Click action
     */
    @FXML
    private void onEditClick(ActionEvent actionEvent) {
        navigateTo("EditProject.fxml", actionEvent);
    }


    private void navigateTo(String fxmlFile, ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent newRoot = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(newRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates cards to the timeline
     */
    private void addCardsToTimeline(){
        try {
            if (cardHolder == null) {
                return;
            }

            boolean allCardsAdded = true;

            for (Card cardToAdd : project.getListOfCards()) {
                try {
                    StackPane cardOverlay = new StackPane();
                    cardOverlay.prefWidthProperty().bind(cardHolder.widthProperty().multiply(0.3));

                    VBox cardLayout = new VBox();

                    // Top Menu Bar
                    HBox topMenuBar = new HBox();
                    Button returnButton = new Button("Return");
                    Label cardTitle = new Label(cardToAdd.getTitle());
                    topMenuBar.getChildren().addAll(returnButton, cardTitle);

                    // Media Container
                    HBox mediaContainer = new HBox();

                    // Description
                    VBox descriptionContainer = new VBox();
                    Label descriptionTitle = new Label("Description:");
                    Label descriptionContent = new Label(cardToAdd.getDescription());
                    descriptionContainer.getChildren().addAll(descriptionTitle, descriptionContent);

                    // Set ID
                    cardOverlay.setId("card_" + cardToAdd.getId());

                    // Add to main container
                    cardLayout.getChildren().addAll(topMenuBar, mediaContainer, descriptionContainer);
                    cardOverlay.getChildren().addAll(cardLayout);
                    cardHolder.getChildren().add(cardOverlay);
                } catch (Exception e) {
                    e.printStackTrace();
                    allCardsAdded = false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Opens the card as a popup
     * @param stage The stage of the application
     * @param cardToRead The card to be used to construct the card popup
     */
    public void OpenCard(Stage stage, Card cardToRead) {
        // Title of the card
        Label cardTitle = new Label("Card - " + cardToRead.getTitle());
        Pane titleContainer = new Pane(cardTitle);

        // Media Container for Images
        HBox mediaContainer = new HBox();
        if (!cardToRead.getmediaImages().isEmpty()) {
            Image firstImage = new Image(cardToRead.getmediaImages().get(0));
            ImageView firstImageView = new ImageView(firstImage);
            mediaContainer.getChildren().add(firstImageView);
        }

        // Adding up to three more images
        if (cardToRead.getmediaImages().size() >= 3) {
            VBox leftMediaContainer = new VBox();
            for (int i = 1; i < 4; i++) {
                Image otherImages = new Image(cardToRead.getmediaImages().get(i));
                ImageView otherImageView = new ImageView(otherImages);
                otherImageView.setFitWidth(30);
                otherImageView.setPreserveRatio(true);
                leftMediaContainer.getChildren().add(otherImageView);
            }
            mediaContainer.getChildren().add(leftMediaContainer);
        }

        // Card information
        Label dateAddedTitle = new Label("Date Added:");
        Label dateAdded = new Label(cardToRead.getDateCreated());
        Label descriptionTitle = new Label("Description:");
        Label description = new Label(cardToRead.getDescription());
        HBox cardInformation = new HBox(dateAddedTitle, dateAdded, descriptionTitle, description);

        // Create the popup
        Popup cardPopup = new Popup();
        VBox cardContainer = new VBox(titleContainer, mediaContainer, cardInformation);
        cardPopup.getContent().add(cardContainer);

        // Set the size of the popup based on the size of the stage
        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            cardContainer.setMinWidth(newVal.doubleValue() * 0.5);
        });
        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            cardContainer.setMinHeight(newVal.doubleValue() * 0.5);
        });

        // (Haven't tested this), close if clicked outside the popup
        stage.getScene().addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (cardPopup.isShowing() && !cardPopup.getContent().get(0).isHover()) {
                cardPopup.hide();
            }
        });
    }
}