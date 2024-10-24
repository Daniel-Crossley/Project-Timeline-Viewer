package com.example.project.controller;

import com.example.project.ApplicationStart;
import com.example.project.OOD.DisplayStylings;
import com.example.project.OOD.ProjectsDisplay;
import com.example.project.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

/**
 * Controller to handle searches of different projects
 */
public class SearchController extends ProjectsDisplay {

    private boolean guest;
    private User userInformation;
    @FXML
    public Label Label_Username;
    public Button Button_Logout;
    @FXML
    public Button DashboardSearch;
    @FXML
    public Button threeDTag;
    @FXML
    public Button metalTag;
    @FXML
    public Button clayTag;
    @FXML
    public Button sculptingTag;
    @FXML
    public Button woodTag;
    @FXML
    public Button paperTag;
    @FXML
    private Button searchButton;
    public HBox Container_Search_Progress;
    public HBox Container_Search_Completed;
    public ScrollPane Scrollpane_Search_Progress;
    public ScrollPane Scrollpane_Search_Completed;
    private Map<Button, Boolean> buttonStates = new HashMap<>();
    public SqliteProjectDAO projectDAO;
    public List<Project> projectList = new ArrayList<>();
    @FXML
    public TextField titleSearch;
    @FXML
    public DatePicker DateSearch;

    SqliteCardDAO cardDAO = new SqliteCardDAO();

    //Generic Styling
    HashMap<Object, Object> stylings_dictionary = SetStylings();


    public void setUserInformation(boolean guest, User user) {
        this.guest = guest;
        this.userInformation = user;
        updateUserName();
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
     * Action of logging out
     * @param actionEvent Clicking Action
     * @throws IOException IOException
     */
    public void Logout(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) Button_Logout.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, ApplicationStart.WIDTH, ApplicationStart.HEIGHT);
        stage.setScene(scene);
    }


    /**
     * Goes back to the dashboard scene
     * @throws IOException
     */
    public void Open_Dashboard() throws IOException{
        Stage stage = (Stage) DashboardSearch.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("Owner-Dashboard.fxml"));
        Parent root = fxmlLoader.load();
        DashboardController dashboardController = fxmlLoader.getController();
        userInformation.setProjects(new ArrayList<>());
        dashboardController.setUserInformation(false, userInformation);
        Scene scene = new Scene(root, ApplicationStart.WIDTH, ApplicationStart.HEIGHT);
        stage.setScene(scene);
        System.out.println("Going back to the previous screen.");
    }


    /**
     * starts the tags to false and prepares the scroller
     */
    @FXML
    public void initialize(){

        Container_Search_Progress.prefHeightProperty().bind(Scrollpane_Search_Progress.heightProperty().multiply(0.88));
        projectDAO = new SqliteProjectDAO();
        buttonStates.put(threeDTag, false);
        buttonStates.put(metalTag, false);
        buttonStates.put(clayTag, false);
        buttonStates.put(sculptingTag, false);
        buttonStates.put(woodTag, false);
        buttonStates.put(paperTag, false);

        threeDTag.setOnAction(event -> TagColourChange(threeDTag));
        metalTag.setOnAction(event -> TagColourChange(metalTag));
        clayTag.setOnAction(event -> TagColourChange(clayTag));
        sculptingTag.setOnAction(event -> TagColourChange(sculptingTag));
        woodTag.setOnAction(event -> TagColourChange(woodTag));
        paperTag.setOnAction(event -> TagColourChange(paperTag));

        searchButton.setOnAction(event -> SearchButton());

    }

    /**
     * Used to update the colour of the tag button
     * @param clickedButton The button to be modified
     */
    public void TagColourChange(Button clickedButton){
        boolean newState =!buttonStates.get(clickedButton);
        buttonStates.put(clickedButton,newState);
        if (newState) {
            clickedButton.setStyle("-fx-background-color: #cc8b2e; -fx-background-radius: 50;");
        } else {
            clickedButton.setStyle("-fx-background-color: #e5c08b; -fx-background-radius: 50;");
        }

        System.out.println(clickedButton.getId() + " is now " + (newState ? "ON" : "OFF"));
    }

    /**
     * Handles the logic for displaying the cards
     */
    public void SearchButton() {
        Container_Search_Progress.getChildren().clear();
        String searchText = titleSearch.getText();
        LocalDate selectedDate = DateSearch.getValue();

        String dateString;
        if (selectedDate == null){
            dateString = "none";
        }
        else {
            dateString = selectedDate.toString();
        }

        StringBuilder selectedTags = new StringBuilder();
        for (Map.Entry<Button, Boolean> entry : buttonStates.entrySet()) {
            if (entry.getValue()) {
                if (selectedTags.length() >0){
                    selectedTags.append(", ");
                }
                selectedTags.append(entry.getKey().getText());
            }
        }

        String SearchTags = selectedTags.toString();

        List<Project> searchResults = projectDAO.getSearchProjects(searchText, dateString, SearchTags);


        if (searchResults !=null && !searchResults.isEmpty()){
            this.projectList =searchResults;
            System.out.println("Found " + searchResults.size() + " results.");

            addProjectsToDash(userInformation, stylings_dictionary, Container_Search_Progress, Container_Search_Completed, Scrollpane_Search_Progress, Scrollpane_Search_Completed, projectList, guest);
        }

    }




}
