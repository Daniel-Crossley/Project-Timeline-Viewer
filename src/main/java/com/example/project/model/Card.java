package com.example.project.model;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Card class to store card data
 */
public class Card {
    private int id;
    private String title;
    private String description;
    private String dateCreated;
    private String dateFinished;
    private List<CardComment> comments = new ArrayList<>();
    private List<String> mediaImages = new ArrayList<>();


    /**
     * Sets the list of image's
     * @param setmediaImages The media images associated with the card
     */
    public void setmediaImages(List<String> setmediaImages) {
        this.mediaImages = setmediaImages;
    }

    /**
     * Retrieves the card's set media images
     * @return The media images addresses
     */
    public List<String> getmediaImages(){return mediaImages;}

    /**
     * Sets the list of comments
     * @param setComments The comments associated with the card
     */
    public void setComments(List<CardComment> setComments) {
        this.comments = setComments;
    }

    /**
     * Retrieves the card's set comments
     * @return The media comments
     */
    public List<CardComment> getComments(){return comments;}

    public Card(int setID, String setTitle, String setDescription, String setDateCreated, String setDateFinished){
        this.id = setID;
        this.title = setTitle;
        this.description = setDescription;
        this.dateCreated = setDateCreated;
        this.dateFinished = setDateFinished;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the title of the card
     * @return the title of the card
     */

    public String getTitle(){
        return this.title;
    }

    /**
     * Retrieves the description of the card
     * @return the description of the card
     */
    public String getDescription(){
        return this.description;
    }

    /**
     * Retrieves the card's date of creation as a string
     * @return the date the card was created
     */
    public String getDateCreated(){
        return this.dateCreated;
    }

    /**
     * Retrieves the card's date of finished as a string
     * @return the date the card was finished
     */
    public String getDateFinished(){
        return this.dateFinished;
    }
}
