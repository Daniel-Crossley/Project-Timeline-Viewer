package com.example.project.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreateNewProjectController implements Initializable {

    // CheckBoxes for selecting tags
    @FXML
    private CheckBox tag3DModeling;
    @FXML
    private CheckBox tagMetal;
    @FXML
    private CheckBox tagSculpting;
    @FXML
    private CheckBox tagClayWork;
    @FXML
    private CheckBox tagWoodWork;
    @FXML
    private CheckBox tagPaperWork;

    // Input fields for project details
    @FXML
    private TextField titleField;  // TextField for project title
    @FXML
    private TextArea descriptionField;  // TextArea for project description
    @FXML
    private ComboBox<String> visibilityComboBox;  // ComboBox for visibility options (Public/Private)

    @FXML
    private ImageView cardImageView;  // ImageView for displaying card image
    @FXML
    private TextField colourField;  // TextField for project color

    // Buttons and Labels
    @FXML
    private Button goBack;  // Button to navigate back to dashboard
    @FXML
    private Button create;  // Button to publish project
    @FXML
    private Label InvalidProject;  // Label to display validation error message

    // User and DAO for database interaction
    private User userInformation;  // Holds the user information
    private SqliteProjectDAO projectDAO;  // DAO for interacting with SQLite database

    /**
     * Initializes the controller. Sets up visibility options.
     * @param location Location of the FXML file
     * @param resources Resource bundle for the controller
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        projectDAO = new SqliteProjectDAO();  // Initialize the DAO
        ObservableList<String> visibilityOptions = FXCollections.observableArrayList("Private", "Public");
        visibilityComboBox.setItems(visibilityOptions);  // Set visibility options in ComboBox
        visibilityComboBox.setValue("Public");  // Default to Public visibility
    }

    /**
     * Navigates back to the Owner Dashboard screen.
     * @throws IOException if an error occurs while loading the view
     */
    @FXML
    protected void goBack() throws IOException {
        Stage stage = (Stage) goBack.getScene().getWindow();  // Get current window (stage)
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("Owner-Dashboard.fxml"));
        Parent root = fxmlLoader.load();  // Load the Owner Dashboard view
        DashboardController dashboardController = fxmlLoader.getController();  // Get controller
        userInformation.setProjects(new ArrayList<>());  // Reset user's project list
        dashboardController.setUserInformation(false, userInformation);  // Pass user information to dashboard
        Scene scene = new Scene(root, ApplicationStart.WIDTH, ApplicationStart.HEIGHT);  // Create a new scene
        stage.setScene(scene);  // Set the new scene
    }

    /**
     * Publishes the project by gathering input data and adding it to the database.
     * @throws IOException if an error occurs while loading the view
     */
    @FXML
    protected void publishProject() throws IOException {
        // Get the input values
        String title = titleField.getText();  // Project title
        String description = descriptionField.getText();  // Project description
        String colour = colourField.getText();  // Project color
        boolean visibility = visibilityComboBox.getValue().equals("Public");  // Project visibility

        // Collect selected tags
        ObservableList<String> selectedTags = FXCollections.observableArrayList();
        if (tag3DModeling.isSelected()) selectedTags.add("3D Modeling");
        if (tagMetal.isSelected()) selectedTags.add("Metal");
        if (tagSculpting.isSelected()) selectedTags.add("Sculpting");
        if (tagClayWork.isSelected()) selectedTags.add("Clay-Work");
        if (tagWoodWork.isSelected()) selectedTags.add("Wood-Work");
        if (tagPaperWork.isSelected()) selectedTags.add("Paper-Work");

        // Validate input fields
        if (title.isEmpty() || description.isEmpty() || colour.isEmpty() || selectedTags.isEmpty()) {
            InvalidProject.setVisible(true);  // Show validation error message if any field is empty
            return;
        }

        // Log selected tags for debugging
        System.out.println("Selected Tags: " + selectedTags);

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = today.format(formatter);

        // Create a new project with the gathered data
        Project newProject = new Project(0, title, description, formattedDate, "none", visibility, colour, 0, selectedTags);
        projectDAO.addProject(newProject, userInformation);  // Add project to the database

        // Navigate back to the Owner Dashboard after project creation
        Stage stage = (Stage) create.getScene().getWindow();  // Get current window (stage)
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("Owner-Dashboard.fxml"));
        Parent root = fxmlLoader.load();  // Load the Owner Dashboard view
        DashboardController dashboardController = fxmlLoader.getController();  // Get controller
        userInformation.setProjects(new ArrayList<>());  // Reset user's project list
        dashboardController.setUserInformation(false, userInformation);  // Pass user information to dashboard
        Scene scene = new Scene(root, ApplicationStart.WIDTH, ApplicationStart.HEIGHT);  // Create a new scene
        stage.setScene(scene);  // Set the new scene
    }

    /**
     * Sets the user information for the current session.
     * @param user The user information to be set
     */
    public void setUserInformation(User user) {
        this.userInformation = user;  // Assign user information to the controller
    }
}
