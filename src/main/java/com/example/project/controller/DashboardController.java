package com.example.project.controller;

import com.example.project.model.Dashboard;
import javafx.fxml.FXML;

public class DashboardController {
    Dashboard dashboardData;
    boolean Guest = false;

    public DashboardController(String username, String password, String email, boolean Guest){
        if (!Guest){
            dashboardData = new Dashboard(username, password, email);
        }
    }
}
