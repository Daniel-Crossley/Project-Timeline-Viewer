package com.example.project.controller;

import com.example.project.ApplicationStart;
import com.example.project.interfaces.DisplayStylings;
import com.example.project.model.SqliteCardDAO;
import com.example.project.model.User;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
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
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import com.example.project.model.Project;
import com.example.project.model.Card;

import javax.swing.text.LabelView;

/**
 * Controller to handle project information as well as link buttons to create popups for creating and viewing cards
 */
public class TimeLineController extends DisplayStylings {


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
    public HBox statusContainer;
    public Label cardStatusLabel;
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

    public void onEditProject() {
        Stage stage = (Stage) statusContainer.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("edit-project.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
            EditProjectController newEditController = fxmlLoader.getController();
            newEditController.setUserInformation(user, project);
            Scene scene = new Scene(root, ApplicationStart.WIDTH, ApplicationStart.HEIGHT);
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Updates the timeline view based on stored data in timeline
     */
    public void updateView(Stage stage) {
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
            statusContainer.setAlignment(Pos.CENTER);
            cardStatusLabel.setText("Description: ");
            cardStatusLabel.setFont(Font.font("System", FontWeight.BOLD, 15));
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
     * Adds action to button to navigate to another scene
     * @param fxmlFile The scene's fxml filepath
     * @param actionEvent The action
     */
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
     * Used to generate the display for the date and start
     * @return The HBox for display
     */
    private HBox GenerateStart(){

        Label Date = new Label(project.getDateCreated());


        VBox LeftBracket = new VBox (new HBox(new Label("     ")));
        LeftBracket.setAlignment(Pos.CENTER);

        VBox StartLine = new VBox (new HBox(new Label(" ")));
        StartLine.setAlignment(Pos.CENTER);
        StartLine.setStyle("-fx-background-color: #3d3d3d;");

        HBox StartLineContainer = new HBox(StartLine);
        StartLineContainer.setMaxHeight(30);


        Label Space = new Label("");
        HBox Line = new HBox(Space);
        Space.setFont(Font.font("System", FontWeight.BOLD, 5));
        Line.setStyle("-fx-background-color: #3d3d3d;");

        VBox spacer = new VBox(LeftBracket,Line);

        spacer.setAlignment(Pos.CENTER);

        VBox DateContainer = new VBox(Date, spacer);
        DateContainer.setAlignment(Pos.CENTER);

        HBox Start = new HBox (LeftBracket,StartLineContainer, DateContainer);
        Start.setAlignment(Pos.CENTER);
        return Start;
    }

    /**
     * Generates timeline spacer
     * @return VBox used to display the spacer
     */
    private HBox GenerateBetween(){
        Label SpaceText = new Label(" ");

        VBox StartLine = new VBox (new HBox(new Label(" ")));
        StartLine.setAlignment(Pos.CENTER);
        StartLine.setStyle("-fx-background-color: #3d3d3d;");

        HBox StartLineContainer = new HBox(StartLine);
        StartLineContainer.setMaxHeight(30);


        Label Space = new Label("");
        HBox Line = new HBox(Space);
        Space.setFont(Font.font("System", FontWeight.BOLD, 5));
        Line.setStyle("-fx-background-color: #3d3d3d;");

        VBox spacer = new VBox(Line);

        spacer.setAlignment(Pos.CENTER);

        VBox DateContainer = new VBox(SpaceText, spacer);
        DateContainer.setAlignment(Pos.CENTER);

        HBox Start = new HBox (DateContainer);
        Start.setAlignment(Pos.CENTER);
        return Start;
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
            Cards_Container.getChildren().add(GenerateStart());
            for (Card cardToAdd : project.getListOfCards()) {
                try {
                    //Add Spacer
                    Cards_Container.getChildren().add(GenerateBetween());
                    //Containers for Cards
                    StackPane cardOverlay = StackPaneStyling(projectColour, projectWidth, projectBorderWidth, projectRadius, projectBorderColour);
                    cardOverlay.prefWidthProperty().bind(Cards_Container.widthProperty().multiply(0.3));
                    // Card
                    int TitleSize = 15;
                    int ContentSize = 10;
                    Label cardTitle = titleLabel("Title:", TitleSize);
                    Label cardTitleContent = contentLabel(cardToAdd.getTitle(), ContentSize);
                    Label cardDate = titleLabel("Date:", TitleSize);
                    Label cardDateContent = contentLabel(cardToAdd.getDateCreated(), ContentSize);
                    VBox cardLayout = new VBox();
                    // Set Controller stuff
                    cardOverlay.setId("card_" + cardToAdd.getId());

                    cardOverlay.setOnMouseClicked(event -> {
                        System.out.println("Card clicked: " + cardToAdd.getTitle());
                        OpenCard(stage, cardToAdd);
                    });
                    // Add to main container
                    HBox mediaContainer = DisplayImage(cardToAdd, 150, 100);
                    cardLayout.getChildren().add(mediaContainer);

                    VBox textContent = new VBox(cardTitle, cardTitleContent, cardDate, cardDateContent);
                    textContent.setPadding(new Insets(0, 0, 5, 5));
                    cardLayout.getChildren().addAll(textContent);
                    cardOverlay.getChildren().addAll(cardLayout);
                    Cards_Container.getChildren().add(cardOverlay);
                    System.out.println("Card added: " + cardToAdd.getTitle());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates an ImageView for displaying the media stored in the card
     * @param card The card used as reference
     * @param fitWidth The width of ImageView
     * @param fitHeight The height of ImageView
     * @return generated imageview
     */
    private HBox DisplayImage (Card card, int fitWidth, int fitHeight){
        // Media Container for Images
        Image mediaImage = card.getMediaImage();


        if (card.getMediaImage() != null) {
            ImageView mediaImageView = new ImageView(card.getMediaImage());
            mediaImageView.setFitWidth(fitWidth);
            mediaImageView.setFitHeight(fitHeight);
            mediaImageView.setPreserveRatio(true);

            HBox mediaContainer = new HBox();
            mediaContainer.setAlignment(Pos.CENTER);
            mediaContainer.getChildren().add(mediaImageView);

            return mediaContainer;
        }
        return null;
    }

    /**
     * Opens popup for creating a new card
     * @param event event
     */
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
        cardTitle.setFont(Font.font("System", FontWeight.BOLD, 15));

        VBox titleContainer = new VBox(cardTitle);
        titleContainer.setAlignment(Pos.CENTER);
        titleContainer.setPadding(new Insets(10));

        // Media Container for Images
        HBox mediaContainer = DisplayImage(cardToRead, 250, 200);


        // Card information
        int TitleSize = 15;
        int ContentSize = 10;

        Label dateAddedTitle = new Label("Date Added:");
        Label dateAdded = new Label(cardToRead.getDateCreated());
        Label descriptionTitle = new Label("Description:");
        Label description = new Label(cardToRead.getDescription());
        VBox cardInformation = new VBox(dateAddedTitle, dateAdded, descriptionTitle, description);
        cardInformation.setPadding(new Insets(0, 0, 10, 10));

        dateAddedTitle.setFont(Font.font("System", FontWeight.BOLD, TitleSize));
        descriptionTitle.setFont(Font.font("System", FontWeight.BOLD, TitleSize));
        dateAdded.setFont(Font.font("System", FontWeight.NORMAL, ContentSize));
        description.setFont(Font.font("System", FontWeight.NORMAL, ContentSize));

        // Create the popup
        Popup cardPopup = new Popup();
        VBox cardContainer = vBoxStyling(projectColour, projectWidth, projectBorderWidth, projectBorderColour, String.valueOf(projectRadius));
        cardContainer.getChildren().addAll(titleContainer, mediaContainer, cardInformation);
        cardPopup.getContent().add(cardContainer);

        // Set the size of the popup based on the size of the stage
        SetPopUpSize(stage, cardContainer, 4, 4);

        //Close
        stage.getScene().addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (cardPopup.isShowing() && cardPopup.getContent().stream().noneMatch(Node::isHover)) {
                cardPopup.hide();
            }
        });

        cardPopup.show(stage);
    }

    /**
     * Sets the size of the Popup
     * @param stage The stage to base the ratio off of
     * @param popUpContainer The container to set the size of
     * @param widthRatio The width ratio
     * @param heightRatio The height ratio
     */
    private void SetPopUpSize(Stage stage, VBox popUpContainer, int widthRatio, int heightRatio){
        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            popUpContainer.setMinWidth(newVal.doubleValue() * widthRatio);
        });
        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            popUpContainer.setMinHeight(newVal.doubleValue() * heightRatio);
        });
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