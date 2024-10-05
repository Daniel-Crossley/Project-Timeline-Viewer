package com.example.project.controller;

import com.example.project.ApplicationStart;
import com.example.project.model.Project;
import com.example.project.model.SqliteProjectDAO;
import com.example.project.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class SearchController {


    private boolean guest;
    private User userInformation;
    @FXML
    private Label Label_Username;
    public Button Button_Logout;
    @FXML
    public Button DashboardSearch;
    @FXML
    private Button threeDTag;
    @FXML
    private Button metalTag;
    @FXML
    private Button clayTag;
    @FXML
    private Button sculptingTag;
    @FXML
    private Button woodTag;
    @FXML
    private Button paperTag;
    @FXML
    private Button searchButton;
    public HBox topHBOX;
    public HBox midHBOX;
    public HBox botHBOX;
    public ScrollPane SearchScroll;
    private Map<Button, Boolean> buttonStates = new HashMap<>();
    private SqliteProjectDAO projectDAO;
    private List<Project> projectList = new ArrayList<>();
    @FXML
    private TextField titleSearch;

    private final int projectWidth = 150;
    private final String projectColour = "#f1d9b7";
    private final int projectRadius = 10;
    private final String projectBorderColour = "#c27c18";
    private final int projectBorderWidth = 2;

    @FXML
    public void initialize(){

        topHBOX.prefHeightProperty().bind(SearchScroll.heightProperty().multiply(0.88));
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
     * Retrieves user type and user information
     * @param guest Set to true if person is a guest, false if not
     * @param user User information
     */
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



    private void SearchButton() {
        if (titleSearch != null) {
            String searchText = titleSearch.getText();
            System.out.println("Searching for: " + searchText);
            List<Project> searchResults = projectDAO.getSearchProjects(searchText);
            if (searchResults !=null && !searchResults.isEmpty()){
                this.projectList = searchResults;
                System.out.println("Found " + searchResults.size() + " results.");
                addProjectsToDash();
            } else {
                System.out.println("No results found or an error occurred.");
            }


        } else {
            System.out.println("Error: titleSearch TextField is null");
        }
    }



    public boolean getButtonState(Button button) {
        return buttonStates.getOrDefault(button, false);
    }

    private void addProjectsToDash(){

            SearchScroll.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                int widthCalculation = (int) (newWidth.doubleValue() + projectWidth);
                topHBOX.setPrefWidth(widthCalculation);
            });

        for(Project projectToAdd: projectList){
            if (Objects.equals(projectToAdd.getDateFinished(), "none")){
                generateContainer(projectToAdd, topHBOX, SearchScroll);
            }

        }
    }

    private VBox projectVBoxStyling(String colour) {
        VBox projectContainer = new VBox();
        projectContainer.setAlignment(Pos.CENTER);
        projectContainer.setSpacing(10);
        projectContainer.prefHeightProperty().bind(topHBOX.heightProperty().multiply(0.95));
        projectContainer.setPrefWidth(projectWidth);
        projectContainer.setStyle(
                "-fx-border-width: " + this.projectBorderWidth + "; " +
                        "-fx-background-color: " + colour + "; " +
                        "-fx-background-radius: "  + this.projectRadius + "; " +
                        "-fx-border-color: " + this.projectBorderColour + "; " +
                        "-fx-border-radius: " + this.projectRadius + "; "
        );

        return projectContainer;
    }

    private void generateContainer(Project projectToAdd, HBox parentContainer, ScrollPane scrollPane){
        VBox projectContainer = projectVBoxStyling(projectToAdd.getColour());
        Label cardTitle = new Label(projectToAdd.getTitle());
        Label DateCommenced = new Label ("Commenced: " + projectToAdd.getDateCreated());
        projectContainer.getChildren().addAll(cardTitle,DateCommenced);

        if (!Objects.equals(projectToAdd.getDateCreated(), "none")){
            Label DateCompleted = new Label ("Completed: " + projectToAdd.getDateFinished());
            projectContainer.getChildren().addAll(DateCompleted);
        }

        projectContainer.setOnMouseClicked(event -> {
            System.out.println("Project clicked: " + projectToAdd.getTitle());
            Stage stage = (Stage) projectContainer.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("timeline-view.fxml"));
            Parent root = null;
            try {
                root = fxmlLoader.load();
                TimeLineController timelineController = fxmlLoader.getController();
                timelineController.setProject(projectToAdd);
                timelineController.setUser(userInformation);
                Scene scene = new Scene(root, ApplicationStart.WIDTH, ApplicationStart.HEIGHT);
                stage.setScene(scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        parentContainer.getChildren().addAll(projectContainer);
        scrollPane.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            int widthCalculation = (int) (newWidth.doubleValue() + projectWidth);
            parentContainer.setPrefWidth(widthCalculation);
        });
    }

}