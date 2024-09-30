package com.example.project.controller;

import com.example.project.model.Card;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Popup;

public class Card_Popup{
    public void start(Stage stage, Card cardToRead){
        Label cardTitle = new Label("Card - " + cardToRead.getTitle());

        Pane titleContainer = new Pane(cardTitle);

        if (cardToRead.)
        HBox mediaContainer = new mediaContainer


        Popup cardPopup = new Popup();
        VBox cardContainer= new VBox(titleContainer);

        cardPopup.getContent().add(cardContainer);
        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            cardContainer.setMinWidth(newVal.doubleValue() * 0.5);
        });
        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            cardContainer.setMinHeight(newVal.doubleValue() * 0.5);
        });

        stage.getScene().addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (cardPopup.isShowing() && !CardContainer.isHover()) {
                cardPopup.hide();
            }
        });
    }
}
