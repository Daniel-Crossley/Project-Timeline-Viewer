package com.example.project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import com.example.project.model.Project;
import com.example.project.model.Card;

public class TimeLineController {
    @FXML
    private ScrollPane cardSpace;
    @FXML
    private FlowPane TopBar;
    @FXML
    private Button buttonAddCard;
    @FXML
    private Button buttonExport;
    @FXML
    private Label cardStatus;
    @FXML
    private HBox cardHolder;
    @FXML
    private Label projectTitle;

    private Project project;

    public void initialize(int id, String title, String description, String dateCreated, String dateCompleted, String colour, int likes) {
        // Initialize the model
        project = new Project(id, title, description, dateCreated, dateCompleted, colour, likes);

        // Set initial UI state
        updateView();
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
}