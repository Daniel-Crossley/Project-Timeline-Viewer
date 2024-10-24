package com.example.project.controller;

import com.example.project.ApplicationStart;
import com.example.project.OOD.ProjectsDisplay;
import com.example.project.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Handles the controller for the dashboard, such as displaying the projects and buttons to create them
 */
public class DashboardController extends ProjectsDisplay implements Initializable {
    public Pane Menu;
    public Button Button_Logout;
    public Button Button_Search;
    public HBox Container_In_Progress;
    public HBox Container_Completed;
    public ScrollPane Scrollpane_Completed;
    public ScrollPane Scrollpane_Progress;



    @FXML
    private Label Label_Username;

    private User userInformation;
    private SqliteProjectDAO projectDAO;
    private boolean guest = false;

    private List<Project> projectList = new ArrayList<>();

    //Generic Styling
    HashMap<Object, Object> stylings_dictionary = SetStylings();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        projectDAO = new SqliteProjectDAO();
        updateUserName();
        Container_In_Progress.prefHeightProperty().bind(Scrollpane_Progress.heightProperty().multiply(0.88));
        Container_Completed.prefHeightProperty().bind(Scrollpane_Completed.heightProperty().multiply(0.88));
    }


    /**
     * Retrieves user type and user information
     * @param guest Set to true if person is a guest, false if not
     * @param user User information
     */
    public void setUserInformation(boolean guest, User user) {
        this.guest = guest;
        this.userInformation = user;
        updateUserName();
        UpdateLists();
        addProjectsToDash(userInformation, stylings_dictionary, Container_In_Progress, Container_Completed, Scrollpane_Progress, Scrollpane_Completed, projectList, guest);
    }

    /**
     * Update username in topbar based on user information
     */
    private void updateUserName(){
        if (guest) {
            Label_Username.setText("Welcome Guest");
        } else if (userInformation != null && userInformation.getUsername() != null) {
            Label_Username.setText("Welcome, " + userInformation.getUsername());
        }
    }

    /**
     * Updates project list using user information
     */
    private void UpdateLists(){
        projectDAO.getProjects(userInformation);
        this.projectList =  userInformation.getProjects();
    }



    /**
     * Action of logging out
     * @param actionEvent Clicking Action
     * @throws IOException IOException
     */
    @FXML
    public void onLogoutClick(ActionEvent actionEvent) throws IOException {
        // Call the inherited Logout method from BaseController
        Logout(actionEvent);
    }

    /**
     * Action of opening the pane for search
     * @param actionEvent Clicking Action
     */
    public void Open_Search(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) Button_Search.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("Search.fxml"));
            Parent root = fxmlLoader.load();
            SearchController searchController = fxmlLoader.getController();
            searchController.setUserInformation(guest, userInformation);
            Scene scene = new Scene(root, ApplicationStart.WIDTH, ApplicationStart.HEIGHT);
            stage.setScene(scene);
        } catch (IOException e){
            e.printStackTrace();
        }
    }


}
