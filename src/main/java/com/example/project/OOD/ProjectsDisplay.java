package com.example.project.OOD;

import java.util.*;
import com.example.project.ApplicationStart;
import com.example.project.controller.CreateNewProjectController;
import com.example.project.controller.TimeLineController;
import com.example.project.model.Card;
import com.example.project.model.Project;
import com.example.project.model.SqliteCardDAO;
import com.example.project.model.User;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ProjectsDisplay extends DisplayStylings{

    SqliteCardDAO cardDAO = new SqliteCardDAO();



    protected HashMap<Object, Object> SetStylings(){
        HashMap<Object,Object> stylings_dictionary = new HashMap<>();
        stylings_dictionary.put("projectWidth", 150.00);
        stylings_dictionary.put("projectColour", "#f1d9b7");
        stylings_dictionary.put("projectRadius", 10);
        stylings_dictionary.put("projectBorderColour",  "#c27c18");
        stylings_dictionary.put("projectBorderWidth", 2);
        return stylings_dictionary;
    }

    /**
     * Adds project containers to the different containers based on if they have a completion date set
     */
    protected void addProjectsToDash( User userInformation, HashMap<Object,Object> projectStylings,HBox Container_In_Progress, HBox Container_Completed, ScrollPane Scrollpane_Progress, ScrollPane Scrollpane_Completed, List<Project> projectList, boolean guest){
        if (!guest){
            newCardContainer(Container_In_Progress, Scrollpane_Progress, projectStylings, guest, userInformation);
        }
        else{
            Scrollpane_Progress.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                int widthCalculation = (int) (newWidth.doubleValue() + Double.parseDouble((String) projectStylings.get("projectWidth")));

                Container_In_Progress.setPrefWidth(widthCalculation);
            });
            Scrollpane_Completed.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                int widthCalculation = (int) (newWidth.doubleValue() + Double.parseDouble((String) projectStylings.get("projectWidth")));
                Container_Completed.setPrefWidth(widthCalculation);
            });
        }


        for(Project projectToAdd: projectList){
            if (Objects.equals(projectToAdd.getDateFinished(), "none")){
                generateContainer(projectToAdd, Container_In_Progress, Scrollpane_Progress, userInformation, projectStylings);
            }
            else{
                generateContainer(projectToAdd, Container_Completed, Scrollpane_Completed, userInformation, projectStylings);
            }
        }
    }

    /**
     * Generates the container for generating a new project
     * @param parentContainer container to add the project to
     * @param scrollPane container to be used as reference
     */
    private void newCardContainer(HBox parentContainer, ScrollPane scrollPane, HashMap<Object,Object> projectStylings, boolean guest, User userInformation){
        VBox projectContainer = vBoxStyling((String) projectStylings.get("projectColour"), projectStylings.get("projectWidth"), projectStylings.get("projectBorderWidth"), projectStylings.get("projectBorderColour"), projectStylings.get("projectRadius"));
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
            int widthCalculation = (int) (newWidth.doubleValue() + (double)projectStylings.get("projectWidth"));
            parentContainer.setPrefWidth(widthCalculation);
        });
    }

    /**
     * Generates the container of project information to add
     * @param projectToAdd project to generate container from
     * @param parentContainer container to add the project to
     * @param scrollPane container to be used as reference
     */
    private void generateContainer(Project projectToAdd, HBox parentContainer, ScrollPane scrollPane, User userInformation, HashMap<Object,Object> projectStylings){
        int TitleSize = 15;
        int ContentSize = 10;

        VBox projectContainer = vBoxStyling(projectToAdd.getColour(), projectStylings.get("projectWidth"), projectStylings.get("projectBorderWidth"), projectStylings.get("projectBorderColour"), projectStylings.get("projectRadius"));

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
            int widthCalculation = (int) (newWidth.doubleValue() + (double)projectStylings.get("projectWidth"));
            parentContainer.setPrefWidth(widthCalculation);
        });
    }
}
