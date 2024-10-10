package com.example.project.model;

import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

/**
 * Project class to store project data
 */
public class Project {
    private int id;
    private Label projectTitle;
    private List<Card> listOfCards = new ArrayList<>();
    private String titleName = "Project Default Title";
    private String description = "Project Default Description";
    private String dateCreated = "DD/MM/YYYY";
    private String dateFinished = "DD/MM/YYYY";
    private boolean visibility = true;
    private int likes = 0;
    private String colour = "#9FA1AC";
    private List<String> tags = new ArrayList<>();

    /**
     * Initiates project's timeline, set up
     * @param id the id of the timeline
     * @param title the title of the timeline
     * @param description the description of the timeline
     * @param dateCreated the date the project was published
     * @param dateCompleted the date the project was completed
     * @param colour the colour of the top bar of the project
     * @param likes the number of likes the project has
     */
    public Project(int id, String title, String description, String dateCreated, String dateCompleted, boolean visibility, String colour, int likes, List<String> tags){
        this.id = id;
        this.titleName = title;
        this.description = description;
        this.dateCreated = dateCreated;
        this.dateFinished = dateCompleted;
        this.visibility = visibility;
        this.colour = colour;
        this.likes = likes;
        this.tags = tags;
    }

    public void SetListofCards(List<Card> cardSet){
        this.listOfCards = cardSet;
    }

    // I dont think we need this as DOA or Controller will handle this
    ///**
    // * Generates cards to be added to the timeline
    // * @param cardsToAdd list of hashmap of cards to be added
    // * @return Returns true if the cards were added successfully, else returns false
    // */
    //private boolean generateCardList(List<HashMap<String, String>> cardsToAdd){
    //    if (cardsToAdd == null || cardsToAdd.isEmpty()) {
    //        return false; // Indicate failure or nothing to process
    //    }

    //    for (HashMap<String,String> importCard : cardsToAdd){
    //        int addID = parseInt(importCard.get("ID"));
    //        String addTitle = importCard.get("title");
    //        String addDescription = importCard.get("description");
    //        String addDateCreated = importCard.get("dateCreated");
    //        String addDateFinished = importCard.get("dateFinished");
    //        Card cardToImport = new Card(addID, addTitle, addDescription, addDateCreated, addDateFinished);
    //        addCard(cardToImport);
    //    }
    //    return false;
    //}



    /**
     * Retrieves the project(timeline) ID
     * @return Returns the project ID as an int
     */
    public int getId(){
        return id;
    }

    /**
     * Sets the project(timeline) ID
     * @param projectID The ID of the project(timeline)
     */
    public void setId(int projectID){
        this.id = projectID;
    }

    /**
     * Retrieves the project(timeline) title
     * @return Returns the project(timeline) title as a String
     */
    public String getTitle(){
        return this.titleName;
    }

    /**
     * Sets the project(timeline) title
     * @param timelineTitle String of the project title
     */
    private void setTitle(String timelineTitle){
        this.titleName = timelineTitle;
    }

    /**
     * Retrieves the description of the project(timeline)
     * @return Returns the project(timeline) description as a String
     */
    public String getDescription (){
        return this.description;
    }

    /**
     * Sets the project(timeline) description
     * @param timelineDescription The project(timeline) description
     */
    private void setDescription(String timelineDescription){
        this.description = timelineDescription;
    }

    /**
     * Retrieves the project(timeline) date of creation
     * @return Returns the project(timeline) date of creation as a string
     */
    public String getDateCreated(){
        return this.dateCreated;
    }

    /**
     * Sets the project(timeline) date of creation
     * @param timelineCreatedDate Date of the project(timeline) commencement as a string
     */
    private void setDateCreated(String timelineCreatedDate){
        this.dateCreated = timelineCreatedDate;
    }

    /**
     * Retrieves the project(timeline) date of completion
     * @return Returns the project(timeline) date of completion as a string
     */
    public String getDateFinished(){
        return dateFinished;
    }

    /**
     * Sets the project(timeline) date of completion
     * @param timelineFinishedDate Date of the project(timeline) completion as a string
     */
    private void setDateFinished(String timelineFinishedDate){
        this.dateFinished = timelineFinishedDate;
    }

    /**
     * Retrieves the project visibility
     * @return returns the project visibility as a boolean value
     */
    public boolean isVisible(){
        return this.visibility;
    }

    /**
     * Sets the visibility of the project
     * @param setVisibility Sets the visibility of the project
     */
    public void setVisibility(boolean setVisibility){
        this.visibility = setVisibility;
    }

    /**
     * Retrieves the project(timeline) likes
     * @return Returns the project(timeline) likes as an int
     */
    public int getLikes(){
        return this.likes;
    }

    /**
     * Set the project(timeline) likes
     * @param setLikes Set the number of likes the project has
     */
    public void setLikes(int setLikes){
        this.likes = setLikes;
    }

    /**
     * Retrieves the project(timeline) colour
     * @return Returns the project(timeline) colour as a string (hexcode)
     */
    public String getColour(){
        return colour;
    }

    /**
     * Set the colour of the project
     * @param setColour the colour of the project as a hexcode
     */
    public void setColour(String setColour){this.colour = setColour;}

    /**
     * Add card to list of cards
     * @param cardAdd Card to add to list
     */
    public void addCard(Card cardAdd){
        listOfCards.add(cardAdd);
    }

    public void setListOfCards(List<Card> importedCards){
        this.listOfCards = importedCards;
    }

    public List<Card> getListOfCards(){
        return listOfCards;
    }

    public List<String> getTags() {return tags;}

    public void addTag(String tag){this.tags.add(tag);}
}
