package com.example.project.controller;

import com.example.project.ApplicationStart;
import com.example.project.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class SearchController {


    private boolean guest;
    private User userInformation;
    @FXML
    private Label Label_Username;
    public Button Button_Logout;
    @FXML
    public Button DashboardSearch;
    /**
     * Retrieves user type and user information
     * @param guest Set to true if person is a guest, false if not
     * @param user User information
     */
    public void setUserInformation(boolean guest, User user) {
        this.guest = guest;
        this.userInformation = user;
        updateUserName();

    }

    /**
     * Update username in topbar based on user information
     */
    private void updateUserName(){
        if (guest) {
            Label_Username.setText("Welcome Guest");
        } else if (userInformation != null && userInformation.getUsername() != null) {
            Label_Username.setText("Welcome, " + userInformation.getUsername());
        }
    }

    /**
     * Action of logging out
     * @param actionEvent Clicking Action
     * @throws IOException IOException
     */
    public void Logout(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) Button_Logout.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, ApplicationStart.WIDTH, ApplicationStart.HEIGHT);
        stage.setScene(scene);
    }


    public void Open_Dashboard() throws IOException{
        Stage stage = (Stage) DashboardSearch.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("Owner-Dashboard.fxml"));
        Parent root = fxmlLoader.load();
        DashboardController dashboardController = fxmlLoader.getController();
        userInformation.setProjects(new ArrayList<>());
        dashboardController.setUserInformation(false, userInformation);
        Scene scene = new Scene(root, ApplicationStart.WIDTH, ApplicationStart.HEIGHT);
        stage.setScene(scene);
        System.out.println("Going back to the previous screen.");
    }

}