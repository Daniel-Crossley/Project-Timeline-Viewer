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
import javafx.scene.layout.GridPane;
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
    public TimelineView(int id, String title, String description, String dateCreated, String dateCompleted, String colour, int likes, List<HashMap<String, String>> cardsToAdd){
        setID(id);
        setTitle(title);
        setDescription(description);
        setDateCreated(dateCreated);
        setDateFinished(dateCompleted);
        setColour(colour);
        setLikes(likes);
        if (generateCardList(cardsToAdd)){
            cardStatus.setText(description);
        }
        else{
            cardStatus.setText("No cards in this project, please click add new:");
        }
    }

    /**
     * Generates cards to be added to the timeline
     * @param cardsToAdd list of hashmap of cards to be added
     * @return Returns true if the cards were added successfully, else returns false
     */
    private boolean generateCardList(List<HashMap<String, String>> cardsToAdd){
        if (cardsToAdd == null || cardsToAdd.isEmpty()) {
            return false; // Indicate failure or nothing to process
        }

        for (HashMap<String,String> importCard : cardsToAdd){
            int addID = parseInt(importCard.get("ID"));
            String addTitle = importCard.get("title");
            String addDescription = importCard.get("description");
            String addDateCreated = importCard.get("dateCreated");
            String addDateFinished = importCard.get("dateFinished");
            cardEntry cardToImport = new cardEntry(addID, addTitle, addDescription, addDateCreated, addDateFinished);
            addCard(cardToImport);
        }
        return false;
    }

    public int getID(){
        return ID;
    }

    private void setID(int projectID){
        this.ID = projectID;
    }

    public String getTitle(){
        return this.titleName;
    }

    private void setTitle(String timelineTitle){
        this.titleName = timelineTitle;
        projectTitle.setText(timelineTitle);
    }

    public String getDescription (){
        return this.description;
    }

    private void setDescription(String timelineDescription){
        this.description = timelineDescription;
    }

    public String getDateCreated(){
        return this.dateCreated;
    }

    private void setDateCreated(String timelineCreatedDate){
        this.dateCreated = timelineCreatedDate;
    }

    public String getDateFinished(){
        return dateFinished;
    }

    private void setDateFinished(String timelineFinishedDate){
        this.dateFinished = timelineFinishedDate;
    }

    public boolean isVisible(){
        return this.visibility;
    }

    public void setVisibility(boolean setVisibility){
        this.visibility = setVisibility;
    }

    public int getLikes(){
        return this.likes;
    }

    public void setLikes(int setLikes){
        this.likes = setLikes;
    }

    public String getTimelineColour(){
        return colour;
    }

    public void setColour(String setColour){
        this.colour = setColour;
        TopBar.setStyle("-fx-background-color: #9FA1AC;");
    }

    public void addCard(cardEntry cardAdd){
        listOfCards.add(cardAdd);
    }


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
