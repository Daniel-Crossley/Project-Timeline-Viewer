package com.example.project.Timeline;

import com.example.project.Cards.cardEntry;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;

public class TimelineView {
    @FXML
    private Label projectTitle;
    private int projectID = 0;
    private String projectColour = "#9FA1AC";
    private cardEntry[] listOfCards;

    private LocalDateTime projectTimeStart;
    private LocalDateTime projectTimeEnd;
    private String projectDescription = "Project Test Description";


    private GridPane cardSpace;


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
            // Load dashboard scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditProject.fxml"));
            Parent newRoot = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Set to Edit Project Scene
            stage.setScene(new Scene(newRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean addCards(){
        return false;
    }
}
