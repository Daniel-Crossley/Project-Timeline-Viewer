package com.example.project.controller;

import com.example.project.ApplicationStart;
import com.example.project.model.SqliteProjectDAO;
import com.example.project.model.User;
import com.example.project.model.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateNewProjectController implements Initializable {

    @FXML
    private TextField titleField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private ComboBox<String> visibilityComboBox;

    @FXML
    private ImageView cardImageView;

    @FXML
    private TextField colourField;

    @FXML
    private Button goBack;

    @FXML
    private Button create;

    private User userInformation;

    private SqliteProjectDAO projectDAO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        projectDAO = new SqliteProjectDAO();
        ObservableList<String> visibilityOptions = FXCollections.observableArrayList("Private", "Public");
        visibilityComboBox.setItems(visibilityOptions);
    }

    @FXML
    protected void goBack() throws IOException {
        Stage stage = (Stage) goBack.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("Owner-Dashboard.fxml"));
        Parent root = fxmlLoader.load();
        DashboardController dashboardController = fxmlLoader.getController();
        dashboardController.setUserInformation(false, userInformation);
        Scene scene = new Scene(root, ApplicationStart.WIDTH, ApplicationStart.HEIGHT);
        stage.setScene(scene);
        System.out.println("Going back to the previous screen.");
    }

    @FXML
    protected void publishProject() throws IOException {

        String title = titleField.getText();
        String description = descriptionField.getText();
        String colour = colourField.getText();

        boolean visibility = visibilityComboBox.getValue().equals("True");

        System.out.println("Publishing project:");
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Visibility: " + visibility);
        System.out.println("Colour: " + colour);

        Project newProject = new Project(0, title, description, "Place holder", "none", visibility, colour, 0, "");
        projectDAO.addProject(newProject, userInformation);

        Stage stage = (Stage) create.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("Owner-Dashboard.fxml"));
        Parent root = fxmlLoader.load();
        DashboardController dashboardController = fxmlLoader.getController();
        dashboardController.setUserInformation(false, userInformation);
        Scene scene = new Scene(root, ApplicationStart.WIDTH, ApplicationStart.HEIGHT);
        stage.setScene(scene);
    }

    public void setUserInformation(User user) {
        this.userInformation = user;
    }
}
