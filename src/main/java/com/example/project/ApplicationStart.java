package com.example.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Start of application
 */
public class ApplicationStart extends Application {
    public static final String TITLE = "Model Hut";
    public static final int WIDTH = 800;
    public static final int HEIGHT = 400;


    /**
     * Used to start the program, opens at hello-view
     * @param stage Stage that will display the program
     * @throws IOException Exception
     */
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("edit-project.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("hello-view.fxml"));
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
