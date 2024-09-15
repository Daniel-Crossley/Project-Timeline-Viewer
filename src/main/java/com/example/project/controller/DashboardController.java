package com.example.project.controller;

import com.example.project.model.User;
import com.example.project.model.UserHolder;
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

        UserHolder holder = UserHolder.getInstance();
        User u= holder.getUser();
        if (u !=null){
            System.out.println(u.getUsername());
            UserID.setText(u.getUsername());
        }

       // UserID.setText(u.getUsername());
    }

    @FXML
    public void initialize() {
        // This method is called after the FXML file has been loaded
        recieveData();
    }

    public DashboardController(boolean Guest, User user){

    }
}
