package com.example.project.controller;

import com.example.project.ApplicationStart;
import com.example.project.model.Project;
import com.example.project.model.User;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public Pane Menu;
    public Button Button_Logout;
    public Button Button_Search;
    public Button Button_Dashboard;
    public HBox Container_In_Progress;
    public HBox Container_Completed;
    public ScrollPane Scrollpane_Completed;
    public ScrollPane Scrollpane_Progress;

    @FXML
    private Label Label_Username;

    private User userInformation;
    private boolean guest = false;

    private List<Project> projectList = new ArrayList<>();

    private int projectWidth = 150;
    private String projectColour = "#f1d9b7";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateUserName();
        addProjectsToDash();

    }

    public void setUserInformation(boolean guest, User user) {
        this.guest = guest;
        this.userInformation = user;
        updateUserName();
    }

    private void updateUserName(){
        if (guest) {
            Label_Username.setText("Welcome Guest");
        } else if (userInformation != null && userInformation.getUsername() != null) {
            Label_Username.setText("Welcome, " + userInformation.getUsername());
        }
    }

    private void UpdateLists(){
        this.projectList =  userInformation.getProjects();
    }

    private void addProjectsToDash(){
        newCardContainer(Container_In_Progress, Scrollpane_Progress);

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
     */
    private void generateContainer(Project projectToAdd, HBox parentContainer, ScrollPane scrollPane){
        VBox projectContainer = new VBox();
        projectContainer.setSpacing(10);
        projectContainer.setStyle("-fx-background-color: " + projectToAdd.getColour());
        projectContainer.prefHeightProperty().bind(Container_In_Progress.heightProperty().multiply(0.95));
        projectContainer.setPrefWidth(projectWidth);
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

    private void newCardContainer(HBox parentContainer, ScrollPane scrollPane){
        VBox projectContainer = new VBox();
        projectContainer.setSpacing(10);
        projectContainer.setStyle("-fx-background-color: " + projectColour);
        projectContainer.prefHeightProperty().bind(Container_In_Progress.heightProperty().multiply(0.95));
        projectContainer.setPrefWidth(projectWidth);
        Label cardTitle = new Label("Create Project");

        projectContainer.getChildren().addAll(cardTitle);

        projectContainer.setOnMouseClicked(event -> {
            System.out.println("New Project Button clicked");
            Stage stage = (Stage) projectContainer.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("create-new-project.fxml"));
            Parent root = null;
            try {
                root = fxmlLoader.load();
                CreateNewProjectController newProjectController = fxmlLoader.getController();
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

    public void Logout(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) Button_Logout.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, ApplicationStart.WIDTH, ApplicationStart.HEIGHT);
        stage.setScene(scene);
    }

    public void Open_Search(ActionEvent actionEvent) {
    }


}
