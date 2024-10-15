package com.example.project.OOJ;

import com.example.project.controller.BaseController;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Holds the classes that would be used to style cards and projects
 */
public class DisplayStylings extends BaseController {

    /**
     * Creates a styled stackpane
     * @param colour The background colour of the stackpane
     * @param width The width of the stackpane
     * @param borderWidth The width of the border
     * @param radius The radius of the stackbane
     * @param borderColour The colour of the border
     * @return stylised stackpane
     */
    protected StackPane StackPaneStyling(String colour, int width, int borderWidth, int radius, String borderColour) {
        StackPane projectContainer = new StackPane();
        projectContainer.setAlignment(Pos.CENTER);
        projectContainer.setPrefWidth(width);
        projectContainer.setStyle(
                "-fx-border-width: " + borderWidth + "; " +
                        "-fx-background-color: " + colour + "; " +
                        "-fx-background-radius: "  + radius + "; " +
                        "-fx-border-color: " + borderColour + "; " +
                        "-fx-border-radius: " + radius + "; "
        );

        return projectContainer;
    }

    /**
     * Generates a stylised VBox
     * @param colour The background colour of the VBox
     * @param width The width of the VBox
     * @param borderWidth The width of the VBox's border
     * @param borderColour The colour of the VBox's border
     * @param radius The radius of the VBox
     * @return Stylised VBox
     */
    protected VBox vBoxStyling(String colour, int width, int borderWidth, String borderColour, int radius) {
        VBox projectContainer = new VBox();
        projectContainer.setAlignment(Pos.CENTER);
        projectContainer.setPrefWidth(width);
        projectContainer.setStyle(
                "-fx-border-width: " + borderWidth + "; " +
                        "-fx-background-color: " + colour + "; " +
                        "-fx-background-radius: "  + radius + "; " +
                        "-fx-border-color: " + borderColour + "; " +
                        "-fx-border-radius: " + radius + "; "
        );

        return projectContainer;
    }

    /**
     * Creates Label for Titles
     * @param textContent The content of the label
     * @param fontSize The font size
     * @return Label
     */
    protected Label titleLabel(String textContent, int fontSize){
        Label titleLabel = new Label(textContent);
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, fontSize));
        return titleLabel;
    }

    /**
     * Creates Label for text content
     * @param textContent The content of the label
     * @param fontSize The font size
     * @return Label
     */
    protected Label contentLabel(String textContent, int fontSize){
        Label titleLabel = new Label(textContent);
        titleLabel.setFont(Font.font("System", FontWeight.NORMAL, fontSize));
        return titleLabel;
    }
}
