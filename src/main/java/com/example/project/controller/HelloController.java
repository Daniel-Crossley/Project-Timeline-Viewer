package com.example.project.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import com.example.project.ApplicationStart;

import java.io.IOException;

/**
 * Main controller for starting the application
 */
public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private Button registerButton;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    /**
     * Opens the scene with the register view
     * @throws IOException
     */
    @FXML
    protected void onRegisterButtonClick() throws IOException {
        Stage stage = (Stage) registerButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("register-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), ApplicationStart.WIDTH, ApplicationStart.HEIGHT);
        stage.setScene(scene);
    }
}