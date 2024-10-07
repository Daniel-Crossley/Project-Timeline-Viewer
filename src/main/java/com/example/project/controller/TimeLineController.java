package com.example.project.controller;

import com.example.project.ApplicationStart;
import com.example.project.model.SqliteCardDAO;
import com.example.project.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import com.example.project.model.Project;
import com.example.project.model.Card;

public class TimeLineController {


    public Button Button_Return;
    public Label Label_Project_Title;
    public Button Button_Edit_Project;
    public HBox Cards_Container;
    public Button Button_New_Card;
    public Button Button_Export;
    public Pane Top_Bar;
    public Label cardStatus;
    public Pane Buffer_Cards;
    public ScrollPane scroll_Container;
    public VBox vBox_Cards;
    private User user;
    private Project project;


    private final int projectWidth = 150;
    private final String projectColour = "#f1d9b7";
    private final int projectRadius = 10;
    private final String projectBorderColour = "#c27c18";
    private final int projectBorderWidth = 2;

    private SqliteCardDAO cardDAO = new SqliteCardDAO();

    public void initialize(User user, Project projectReference) {
        setUser(user);
        setProject(projectReference);
        project.setListOfCards(cardDAO.getCards(projectReference));
    }

    public void setUser(User user){
        this.user = user;
    }
    public void setProject(Project project){
        this.project = project;
    }

    /**
     * Updates the timeline view based on stored data in timeline
     */
    void updateView(Stage stage) {
        project.setListOfCards(cardDAO.getCards(project));

        // Change width to fit size of stage
        stage.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            int widthCalculation = (int) (newWidth.doubleValue() + projectWidth);
            Cards_Container.setPrefWidth(widthCalculation);
            Buffer_Cards.setPrefWidth(widthCalculation);
        });


        //Set colour
        Label_Project_Title.setText(project.getTitle());
        if(project.getColour() != null){
            String color = project.getColour();
            if (color.matches("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{8})$")){
                System.out.println("Project Colour: " + color);
                Top_Bar.setStyle("-fx-background-color: " + project.getColour() + ";");
            } else{
                System.out.println("Project Colour is Wack: " + color);
            }
        }

        // Update other UI elements as needed
        if (project.getListOfCards().isEmpty()) {
            System.out.println("Project has no cards to add");
            cardStatus.setText("No cards in this project, please click add new.");
        } else {
            cardStatus.setText(project.getDescription());
            System.out.println("Adding Cards");
            addCardsToTimeline(stage);
        }
    }

    /**
     * Temporary method for returning to timeline
     * @param actionEvent Click action
     */
    @FXML
    private void onReturnClick(ActionEvent actionEvent) {
        navigateTo("Dashboard.fxml", actionEvent);
    }

    /**
     * Temporary method for editing timeline details
     * @param actionEvent Click action
     */
    @FXML
    private void onEditClick(ActionEvent actionEvent) {
        navigateTo("EditProject.fxml", actionEvent);
    }


    private void navigateTo(String fxmlFile, ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent newRoot = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(newRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Generates card containers to display on timeline
     */
    private void addCardsToTimeline(Stage stage){
        System.out.println("addCardsToTimeline was run");
        try {
            Cards_Container.getChildren().clear();
            if (project.getListOfCards() == null) {
                System.out.println("No cards stored in project!");
                return;
            }

            boolean allCardsAdded = true;

            for (Card cardToAdd : project.getListOfCards()) {
                try {
                    StackPane cardOverlay = projectCardStyling(projectColour);


                    cardOverlay.prefWidthProperty().bind(Cards_Container.widthProperty().multiply(0.3));

                    // Card
                    Label cardTitle = new Label("Title: " + cardToAdd.getTitle());
                    Label cardDate = new Label("Date: " + cardToAdd.getDateCreated());
                    VBox cardLayout = new VBox();

                    // Set Controller stuff
                    cardOverlay.setId("card_" + cardToAdd.getId());

                    cardOverlay.setOnMouseClicked(event -> {
                        System.out.println("Card clicked: " + cardToAdd.getTitle());
                        OpenCard(stage, cardToAdd);
                    });

                    // Add to main container
                    cardLayout.getChildren().addAll(cardTitle, cardDate);
                    cardOverlay.getChildren().addAll(cardLayout);
                    Cards_Container.getChildren().add(cardOverlay);
                    System.out.println("Card added: " + cardToAdd.getTitle());
                } catch (Exception e) {
                    e.printStackTrace();
                    allCardsAdded = false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private StackPane projectCardStyling(String colour) {
        StackPane projectContainer = new StackPane();
        projectContainer.setAlignment(Pos.CENTER);
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

    @FXML
    private void onNewCardClick(ActionEvent event) {
        try {
            //Load the popup
            FXMLLoader loader = new FXMLLoader(ApplicationStart.class.getResource("new-card-popup.fxml"));
            Parent popupRoot = loader.load();
            NewCardPopupController controller = loader.getController();
            Stage popupStage = new Stage();
            controller.setStage(popupStage);

            controller.setProject(project);

            //make only popup clickable
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.initOwner(primaryStage);


            //set popup position and size
            popupStage.setWidth(500);
            popupStage.setHeight(300);

            popupStage.setX(500);
            popupStage.setY(200);

            popupStage.setScene(new Scene(popupRoot));
            popupStage.setTitle("Add New Card");

            popupStage.showAndWait();

            updateView((Stage) ((Node) event.getSource()).getScene().getWindow());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Opens the card as a popup
     * @param stage The stage of the application
     * @param cardToRead The card to be used to construct the card popup
     */
    public void OpenCard(Stage stage, Card cardToRead) {
        // Title of the card
        Label cardTitle = new Label("Card - " + cardToRead.getTitle());
        Pane titleContainer = new Pane(cardTitle);

        // Media Container for Images
        HBox mediaContainer = new HBox();
        Image mediaImage = cardToRead.getMediaImage();

        if (mediaImage != null) {
            ImageView mediaImageView = new ImageView(mediaImage);
            mediaImageView.setFitWidth(200);
            mediaImageView.setFitHeight(150);
            mediaImageView.setPreserveRatio(true);
            mediaContainer.getChildren().add(mediaImageView);
        }

        // Card information
        Label dateAddedTitle = new Label("Date Added:");
        Label dateAdded = new Label(cardToRead.getDateCreated());
        Label descriptionTitle = new Label("Description:");
        Label description = new Label(cardToRead.getDescription());
        VBox cardInformation = new VBox(dateAddedTitle, dateAdded, descriptionTitle, description);

        // Create the popup
        Popup cardPopup = new Popup();
        VBox cardContainer = popupCardStyling(projectColour);
        cardContainer.getChildren().addAll(titleContainer, mediaContainer, cardInformation);
        cardPopup.getContent().add(cardContainer);

        // Set the size of the popup based on the size of the stage
        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            cardContainer.setMinWidth(newVal.doubleValue() * 3);
        });
        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            cardContainer.setMinHeight(newVal.doubleValue() * 3);
        });

        // (Haven't tested this), close if clicked outside the popup
        stage.getScene().addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (cardPopup.isShowing() && cardPopup.getContent().stream().noneMatch(Node::isHover)) {
                cardPopup.hide();
            }
        });

        cardPopup.show(stage);
    }

    private VBox popupCardStyling(String colour) {
        VBox projectContainer = new VBox();
        projectContainer.setAlignment(Pos.CENTER);
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
     * This will return the scene to the dashboard
     * @throws IOException Issues with login process
     */

    @FXML
    protected void returnToDashboard(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) Button_Return.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("Owner-Dashboard.fxml"));
        Parent root = fxmlLoader.load();
        DashboardController dashboardController = fxmlLoader.getController();
        user.setProjects(new ArrayList<>()); // Resets the user's project list
        dashboardController.setUserInformation(false, user);
        Scene scene = new Scene(root, ApplicationStart.WIDTH, ApplicationStart.HEIGHT);
        stage.setScene(scene);
    }
}