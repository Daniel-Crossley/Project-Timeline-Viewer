package com.example.project.controller;

import com.example.project.ApplicationStart;
import com.example.project.OOD.DisplayStylings;
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
public class DashboardController extends DisplayStylings implements Initializable {
    public Pane Menu;
    public Button Button_Logout;
    public Button Button_Search;
    public HBox Container_In_Progress;
    public HBox Container_Completed;
    public ScrollPane Scrollpane_Completed;
    public ScrollPane Scrollpane_Progress;

    SqliteCardDAO cardDAO = new SqliteCardDAO();

    @FXML
    private Label Label_Username;

    private User userInformation;
    private SqliteProjectDAO projectDAO;
    private boolean guest = false;

    private List<Project> projectList = new ArrayList<>();

    //Generic Styling
    private final int projectWidth = 150;
    private final String projectColour = "#f1d9b7";
    private final int projectRadius = 10;
    private final String projectBorderColour = "#c27c18";
    private final int projectBorderWidth = 2;

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
        addProjectsToDash();
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
     * Adds project containers to the different containers based on if they have a completion date set
     */
    private void addProjectsToDash(){
        if (!guest){
            newCardContainer(Container_In_Progress, Scrollpane_Progress);
        }
        else{
            Scrollpane_Progress.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                int widthCalculation = (int) (newWidth.doubleValue() + projectWidth);
                Container_In_Progress.setPrefWidth(widthCalculation);
            });
            Scrollpane_Completed.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                int widthCalculation = (int) (newWidth.doubleValue() + projectWidth);
                Container_Completed.setPrefWidth(widthCalculation);
            });
        }


        for(Project projectToAdd: projectList){
            if (Objects.equals(projectToAdd.getDateFinished(), "none")){
                generateContainer(projectToAdd, Container_In_Progress, Scrollpane_Progress);
            }
            else{
                generateContainer(projectToAdd, Container_Completed, Scrollpane_Completed);
            }
        }
    }


    /**
     * Generates the container of project information to add
     * @param projectToAdd project to generate container from
     * @param parentContainer container to add the project to
     * @param scrollPane container to be used as reference
     */
    private void generateContainer(Project projectToAdd, HBox parentContainer, ScrollPane scrollPane){
        int TitleSize = 15;
        int ContentSize = 10;

        VBox projectContainer = vBoxStyling(projectToAdd.getColour(), projectWidth, this.projectBorderWidth, this.projectBorderColour, this.projectRadius);

        Label cardTitle = titleLabel(projectToAdd.getTitle(), 15);
        projectContainer.getChildren().addAll(cardTitle);

        try {
            List<Card> listofCards = cardDAO.getCards(projectToAdd);
            if (listofCards.getFirst().getMediaImage() != null) {
                ImageView mediaImageView = new ImageView(listofCards.getFirst().getMediaImage());
                mediaImageView.setFitWidth(100);
                mediaImageView.setFitHeight(50);
                mediaImageView.setPreserveRatio(true);
                projectContainer.getChildren().add(mediaImageView);
            }
        } catch(NoSuchElementException ignored) {

        }


        Label DateCommenced = titleLabel ("Start: ", 12);
        Label DateCommencedContent = contentLabel(projectToAdd.getDateCreated(), 12);
        HBox dateCommencedContainer = new HBox(DateCommenced, DateCommencedContent);

        VBox projectInformation = new VBox(dateCommencedContainer);
        projectInformation.setPadding(new Insets(0, 0, 5, 5));

        projectContainer.getChildren().addAll(projectInformation);

        if (!Objects.equals(projectToAdd.getDateCreated(), " none")){
            Label DateCompleted = titleLabel ("Finish: ", 12);
            Label DateCompletedContent = contentLabel(projectToAdd.getDateFinished(), 12);

            HBox DateCompletedContainer = new HBox(DateCompleted, DateCompletedContent);
            projectInformation.getChildren().addAll(DateCompletedContainer);
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
                timelineController.updateView(stage);
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

    /**
     * Generates the container for generating a new project
     * @param parentContainer container to add the project to
     * @param scrollPane container to be used as reference
     */
    private void newCardContainer(HBox parentContainer, ScrollPane scrollPane){
        VBox projectContainer = vBoxStyling(projectColour, projectWidth, this.projectBorderWidth, this.projectBorderColour, this.projectRadius);
        Label cardTitle = new Label("Create Project");

        projectContainer.getChildren().addAll(cardTitle);

        projectContainer.setOnMouseClicked(event -> {
            if (!guest) {
                System.out.println("New Project Button clicked");
                Stage stage = (Stage) projectContainer.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("create-new-project.fxml"));
                Parent root = null;
                try {
                    root = fxmlLoader.load();
                    CreateNewProjectController newProjectController = fxmlLoader.getController();
                    newProjectController.setUserInformation(userInformation);
                    Scene scene = new Scene(root, ApplicationStart.WIDTH, ApplicationStart.HEIGHT);
                    stage.setScene(scene);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        parentContainer.getChildren().addAll(projectContainer);
        scrollPane.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            int widthCalculation = (int) (newWidth.doubleValue() + projectWidth);
            parentContainer.setPrefWidth(widthCalculation);
        });
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
