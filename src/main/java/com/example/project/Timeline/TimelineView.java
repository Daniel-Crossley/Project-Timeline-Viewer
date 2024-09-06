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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TimelineView {
    public ScrollPane cardSpace;
    @FXML
    private Label projectTitle;
    private int timelineID = 0;


    private List<cardEntry> listOfCards = new ArrayList<cardEntry>();

    private String timelineTitleName = "Project Default Title";
    private String timelineDescription = "Project Default Description";
    private String timelineTimeCreated = "DD/MM/YYYY";
    private String timelineTimeFinished = "DD/MM/YYYY";
    private boolean timelineVisibility = true;
    private int timelineLikes = 0;
    private String timelineColour = "#9FA1AC";



    @FXML

    public String getTitle(){
        return timelineTitleName;
    }

    private void setTitle(String timelineTitle){
        this.timelineTitleName = timelineTitle;
    }

    public String getDescription (){
        return timelineDescription;
    }

    private void setDescription(String timelineDescription){
        this.timelineDescription = timelineDescription;
    }

    public String getDateCreated(){
        return timelineTimeCreated;
    }

    private void setDateCreated(String timelineCreatedDate){
        this.timelineTimeCreated = timelineCreatedDate;
    }

    public String getDateFinished(){
        return timelineTimeFinished;
    }

    private void setDateFinished(String timelineFinishedDate){
        this.timelineTimeFinished = timelineFinishedDate;
    }

    public boolean isVisible(){
        return timelineVisibility;
    }

    public void setTimelineVisibility(boolean visibility){
        this.timelineVisibility = visibility;
    }

    public int getTimelineLikes(){
        return this.timelineLikes;
    }

    public void setTimelineLikes(int likes){
        this.timelineLikes = likes;
    }

    public String getColour(){
        return timelineColour;
    }

    public void setColour(String colour){
        this.timelineColour = colour;
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
