package com.example.project.controller;

import com.example.project.model.User;
import javafx.fxml.FXML;

public class DashboardController {
    User userInformation;
    boolean Guest = false;

    public DashboardController(String username, String password, String email, boolean Guest){
        if (!Guest){
            userInformation = new User(username, password, email);
        }
    }
}
