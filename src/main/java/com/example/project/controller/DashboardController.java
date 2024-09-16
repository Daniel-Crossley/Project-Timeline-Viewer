package com.example.project.controller;

import com.example.project.ApplicationStart;
import com.example.project.model.Project;
import com.example.project.model.SqliteProjectDAO;
import com.example.project.model.User;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
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
     * Generates generic vbox for project container
     * @param colour Colour of the vbox
     * @return Generated project container
     */
    private VBox projectVBoxStyling(String colour) {
        VBox projectContainer = new VBox();
        projectContainer.setAlignment(Pos.CENTER);
        projectContainer.setSpacing(10);
        projectContainer.prefHeightProperty().bind(Container_In_Progress.heightProperty().multiply(0.95));
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

    /**
     * Generates the container of project information to add
     * @param projectToAdd project to generate container from
     * @param parentContainer container to add the project to
     * @param scrollPane container to be used as reference
     */
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

    /**
     * Generates the container for generating a new project
     * @param parentContainer container to add the project to
     * @param scrollPane container to be used as reference
     */
    private void newCardContainer(HBox parentContainer, ScrollPane scrollPane){
        VBox projectContainer = projectVBoxStyling(projectColour);
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
    public void Logout(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) Button_Logout.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, ApplicationStart.WIDTH, ApplicationStart.HEIGHT);
        stage.setScene(scene);
    }

    /**
     * Action of opening the pane for search
     * @param actionEvent Clicking Action
     */
    public void Open_Search(ActionEvent actionEvent) {
    }


}
