package com.example.project.controller;

import com.example.project.ApplicationStart;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Base controller used to handle logging out user
 */
public class BaseController {
    public void Logout(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node)  actionEvent.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, ApplicationStart.WIDTH, ApplicationStart.HEIGHT);
        stage.setScene(scene);
    }
}
