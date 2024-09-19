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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreateNewProjectController implements Initializable {

    @FXML
    private TextField titleField; // TextField for entering project title

    @FXML
    private TextArea descriptionField; // TextArea for entering project description

    @FXML
    private ComboBox<String> visibilityComboBox; // ComboBox for selecting project visibility (Public/Private)

    @FXML
    private ImageView cardImageView; // ImageView for displaying the project's card image (optional)

    @FXML
    private TextField colourField; // TextField for entering project color

    @FXML
    private Button goBack; // Button to navigate back to the dashboard

    @FXML
    private Button create; // Button to publish the new project

    private User userInformation; // Holds information about the user creating the project

    private SqliteProjectDAO projectDAO; // Data access object for interacting with the SQLite database

    /**
     * Initializes the controller, sets up the ComboBox with visibility options
     * @param location Location of the FXML file
     * @param resources Resource bundle for the controller
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        projectDAO = new SqliteProjectDAO(); // Initializes the DAO for database interactions
        ObservableList<String> visibilityOptions = FXCollections.observableArrayList("Private", "Public");
        visibilityComboBox.setItems(visibilityOptions); // Sets visibility options (Private/Public) in ComboBox
    }

    /**
     * Navigates back to the previous screen (Owner Dashboard)
     * @throws IOException if an error occurs during screen transition
     */
    @FXML
    protected void goBack() throws IOException {
        Stage stage = (Stage) goBack.getScene().getWindow(); // Gets the current window (stage)
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("Owner-Dashboard.fxml")); // Loads Owner Dashboard view
        Parent root = fxmlLoader.load(); // Loads the FXML file
        DashboardController dashboardController = fxmlLoader.getController(); // Gets the DashboardController
        userInformation.setProjects(new ArrayList<>()); // Resets the user's project list
        dashboardController.setUserInformation(false, userInformation); // Passes user information to the dashboard
        Scene scene = new Scene(root, ApplicationStart.WIDTH, ApplicationStart.HEIGHT); // Creates a new scene with the loaded dashboard
        stage.setScene(scene); // Sets the scene to the stage (window)
        System.out.println("Going back to the previous screen."); // Logs the navigation action
    }

    /**
     * Publishes the project by gathering input data and adding it to the database
     * @throws IOException if an error occurs during screen transition
     */
    @FXML
    protected void publishProject() throws IOException {
        // Retrieves input data from the form fields
        String title = titleField.getText(); // Gets the title entered in the text field
        String description = descriptionField.getText(); // Gets the description entered in the text area
        String colour = colourField.getText(); // Gets the color entered in the text field

        // Checks if the selected visibility is "True" (Public) or "False" (Private)
        boolean visibility = visibilityComboBox.getValue().equals("True");

        // Logs the project details for debugging
        System.out.println("Publishing project:");
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Visibility: " + visibility);
        System.out.println("Colour: " + colour);

        // Creates a new Project object with the gathered information
        Project newProject = new Project(0, title, description, "Place holder", "none", visibility, colour, 0, "");
        projectDAO.addProject(newProject, userInformation); // Adds the new project to the database

        // Navigates back to the dashboard after the project is created
        Stage stage = (Stage) create.getScene().getWindow(); // Gets the current window (stage)
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("Owner-Dashboard.fxml")); // Loads Owner Dashboard view
        Parent root = fxmlLoader.load(); // Loads the FXML file
        DashboardController dashboardController = fxmlLoader.getController(); // Gets the DashboardController
        userInformation.setProjects(new ArrayList<>()); // Resets the user's project list
        dashboardController.setUserInformation(false, userInformation); // Passes user information to the dashboard
        Scene scene = new Scene(root, ApplicationStart.WIDTH, ApplicationStart.HEIGHT); // Creates a new scene with the loaded dashboard
        stage.setScene(scene); // Sets the scene to the stage (window)
    }

    /**
     * Sets user information for the current session
     * @param user User information to be set
     */
    public void setUserInformation(User user) {
        this.userInformation = user; // Assigns user information to the controller's userInformation variable
    }
}
