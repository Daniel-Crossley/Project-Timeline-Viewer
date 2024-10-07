package com.example.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationStart extends Application {
    public static final String TITLE = "Model Hut";
    public static final int WIDTH = 800;
    public static final int HEIGHT = 500;


    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("hello-view.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("create-new-project.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),WIDTH,HEIGHT);
        scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
