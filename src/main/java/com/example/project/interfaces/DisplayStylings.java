package com.example.project.interfaces;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class DisplayStylings{

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
    protected VBox vBoxStyling(String colour, int width, int borderWidth, String borderColour, String radius) {
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

}
