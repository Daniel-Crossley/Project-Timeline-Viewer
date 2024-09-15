package com.example.project.controller;

import com.example.project.model.User;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

public class DashboardController {
    User userInformation;
    boolean Guest = false;
   @FXML
   private Label UserID;

    @FXML
    void recieveData(){

            //System.out.println(userInformation.getUsername());
            UserID.setText(userInformation.getUsername());
    }

    @FXML
    public void initialize() {
        // This method is called after the FXML file has been loaded
        recieveData();
    }

    public DashboardController(boolean Guest, User user){
        this.Guest = Guest;
        this.userInformation = user;
    }
}
