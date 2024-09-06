package com.example.project.Timeline;

import com.example.project.Cards.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TimelineView {
    public ScrollPane cardSpace;
    public FlowPane TopBar;
    @FXML
    private Label projectTitle;
    private int ID = 0;


    private List<cardEntry> listOfCards = new ArrayList<cardEntry>();

    private String titleName = "Project Default Title";
    private String description = "Project Default Description";
    private String dateCreated = "DD/MM/YYYY";
    private String dateFinished = "DD/MM/YYYY";
    private boolean visibility = true;
    private int likes = 0;
    private String colour = "#9FA1AC";

    public TimelineView(int id, String title, String description, String timeCreated, String timeCompleted, String colour, int likes, List<HashMap<String, String>> cardsToAdd){
        setID(id);
        setTitle(title);
        setDescription(description);
        setDateCreated(timeCreated);
        setDateFinished(timeCompleted);
        setColour(colour);
        setLikes(likes);
    }

    private boolean generateCardList(List<HashMap<String, String>> cardsToAdd){
        if (cardsToAdd == null || cardsToAdd.isEmpty()) {
            return false; // Indicate failure or nothing to process
        }

        for (HashMap<String,String> card : cardsToAdd){

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

    public void addTimelineCard(cardEntry cardAdd){
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
