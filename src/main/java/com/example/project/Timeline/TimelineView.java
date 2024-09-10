package com.example.project.Timeline;

import com.example.project.Cards.*;
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

import java.util.HashMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class TimelineView {
    public ScrollPane cardSpace;
    public FlowPane TopBar;
    public Button buttonAddCard;
    public Button buttonExport;
    public Label cardStatus;
    public HBox cardHolder;

    @FXML
    private Label projectTitle;
    private int ID = 0;


    private List<cardEntry> listOfCards = new ArrayList<>();

    private String titleName = "Project Default Title";
    private String description = "Project Default Description";
    private String dateCreated = "DD/MM/YYYY";
    private String dateFinished = "DD/MM/YYYY";
    private boolean visibility = true;
    private int likes = 0;
    private String colour = "#9FA1AC";

    /**
     * Initiates project's timeline, set up
     * @param id the id of the timeline
     * @param title the title of the timeline
     * @param description the description of the timeline
     * @param dateCreated the date the project was published
     * @param dateCompleted the date the project was completed
     * @param colour the colour of the top bar of the project
     * @param likes the number of likes the project has
     * @param cardsToAdd list of HashMap to add cards
     */
    public TimelineView(int id, String title, String description, String dateCreated, String dateCompleted, String colour, int likes, List<cardEntry> cardsToAdd){
        setID(id);
        setTitle(title);
        setDescription(description);
        setDateCreated(dateCreated);
        setDateFinished(dateCompleted);
        setColour(colour);
        setLikes(likes);

        // Replace below with:
        // getCards(id)

        // Need to sort cards by date
        if (generateCardList(cardsToAdd)){
            cardStatus.setText(description);

            if (!addCardsToTimeline()){
                cardStatus.setText("Error adding cards");
            }
        }
        else{
            cardStatus.setText("No cards in this project, please click add new.");
        }
    }

    /**
     * Generates cards to be added to the timeline
     * @param cardsToAdd list of hashmap of cards to be added
     * @return Returns true if the cards were added successfully, else returns false
     */
    private boolean generateCardList(List<cardEntry> cardsToAdd){
        if (cardsToAdd == null || cardsToAdd.isEmpty()) {
            return false; // Indicate failure or nothing to process
        }

        try{
            for (cardEntry importCard : cardsToAdd){
                int addID = importCard.getID();
                String addTitle = importCard.getTitle();
                String addDescription = importCard.getDescription();
                String addDateCreated = importCard.getDateCreated();
                String addDateFinished = importCard.getDateFinished();
                cardEntry cardToImport = new cardEntry(addID, addTitle, addDescription, addDateCreated, addDateFinished);
                addCard(cardToImport);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Generates cards to the timeline
     * @return returns true if the cards were succesfully added, else return false
     */
    private boolean addCardsToTimeline(){
        try {
            if (cardHolder == null) {
                return false;
            }

            boolean allCardsAdded = true;

            for (cardEntry cardToAdd : listOfCards) {
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
                    cardOverlay.setId("card_" + cardToAdd.getID());

                    // Add to main container
                    cardLayout.getChildren().addAll(topMenuBar, mediaContainer, descriptionContainer);
                    cardOverlay.getChildren().addAll(cardLayout);
                    cardHolder.getChildren().add(cardOverlay);
                } catch (Exception e) {
                    e.printStackTrace();
                    allCardsAdded = false;
                }
            }

            return allCardsAdded;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves the project(timeline) ID
     * @return Returns the project ID as an int
     */
    public int getID(){
        return ID;
    }

    /**
     * Sets the project(timeline) ID
     * @param projectID The ID of the project(timeline)
     */
    private void setID(int projectID){
        this.ID = projectID;
    }

    /**
     * Retrieves the project(timeline) title
     * @return Returns the project(timeline) title as a String
     */
    public String getTitle(){
        return this.titleName;
    }

    /**
     * Sets the project(timeline) title
     * @param timelineTitle String of the project title
     */
    private void setTitle(String timelineTitle){
        this.titleName = timelineTitle;
        projectTitle.setText(timelineTitle);
    }

    /**
     * Retrieves the description of the project(timeline)
     * @return Returns the project(timeline) description as a String
     */
    public String getDescription (){
        return this.description;
    }

    /**
     * Sets the project(timeline) description
     * @param timelineDescription The project(timeline) description
     */
    private void setDescription(String timelineDescription){
        this.description = timelineDescription;
    }

    /**
     * Retrieves the project(timeline) date of creation
     * @return Returns the project(timeline) date of creation as a string
     */
    public String getDateCreated(){
        return this.dateCreated;
    }

    /**
     * Sets the project(timeline) date of creation
     * @param timelineCreatedDate Date of the project(timeline) commencement as a string
     */
    private void setDateCreated(String timelineCreatedDate){
        this.dateCreated = timelineCreatedDate;
    }

    /**
     * Retrieves the project(timeline) date of completion
     * @return Returns the project(timeline) date of completion as a string
     */
    public String getDateFinished(){
        return dateFinished;
    }

    /**
     * Sets the project(timeline) date of completion
     * @param timelineFinishedDate Date of the project(timeline) completion as a string
     */
    private void setDateFinished(String timelineFinishedDate){
        this.dateFinished = timelineFinishedDate;
    }

    /**
     * Retrieves the project visibility
     * @return returns the project visibility as a boolean value
     */
    public boolean isVisible(){
        return this.visibility;
    }

    /**
     * Sets the visibility of the project
     * @param setVisibility Sets the visibility of the project
     */
    public void setVisibility(boolean setVisibility){
        this.visibility = setVisibility;
    }

    /**
     * Retrieves the project(timeline) likes
     * @return Returns the project(timeline) likes as an int
     */
    public int getLikes(){
        return this.likes;
    }

    /**
     * Set the project(timeline) likes
     * @param setLikes Set the number of likes the project has
     */
    public void setLikes(int setLikes){
        this.likes = setLikes;
    }

    /**
     * Retrieves the project(timeline) colour
     * @return Returns the project(timeline) colour as a string (hexcode)
     */
    public String getTimelineColour(){
        return colour;
    }

    /**
     * Set the colour of the project
     * @param setColour the colour of the project as a hexcode
     */
    public void setColour(String setColour){
        this.colour = setColour;
        TopBar.setStyle("-fx-background-color: #9FA1AC;");
    }

    /**
     * Add card to list of cards
     * @param cardAdd Card to add to list
     */
    public void addCard(cardEntry cardAdd){
        listOfCards.add(cardAdd);
    }

    /**
     * On click return to the dashboard
     * @param actionEvent Click Action
     */
    @FXML
    private void onReturnClick(ActionEvent actionEvent) {
        try {
            // Load dashboard scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
            Parent newRoot = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Set to dashboard scene
            stage.setScene(new Scene(newRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * On click opens up edit page for project(timeline)
     * @param actionEvent Click Action
     */
    @FXML
    private void onEditClick(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditProject.fxml"));
            Parent newRoot = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            stage.setScene(new Scene(newRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
