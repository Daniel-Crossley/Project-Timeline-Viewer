package com.example.project.Cards;

public class cardEntry {
    private int ID;
    private String title;
    private String description;
    private String dateCreated;
    private String dateFinished;

    /**
     * Sets up the card
     * @param setID The id of the card
     * @param setTitle The title of the card
     * @param setDescription The description of the card
     * @param setDateCreated The date of the card's event start
     * @param setDateFinished The data of the card's event finish
     */
    public cardEntry(int setID, String setTitle, String setDescription, String setDateCreated, String setDateFinished){
        this.ID = setID;
        this.title = setTitle;
        this.description = setDescription;
        this.dateCreated = setDateCreated;
        this.dateFinished = setDateFinished;
    }

    /**
     * Retrieves the project(timeline) ID
     * @return Returns the project ID as an int
     */
    public int getID(){
        return this.ID;
    }

    /**
     * Retrieves the project(timeline) title
     * @return Returns the project(timeline) title as a String
     */
    public String getTitle(){
        return this.title;
    }

    /**
     * Retrieves the description of the project(timeline)
     * @return Returns the project(timeline) description as a String
     */
    public String getDescription(){
        return this.description;
    }

    /**
     * Retrieves the project(timeline) date of creation
     * @return Returns the project(timeline) date of creation as a string
     */
    public String getDateCreated(){
        return this.dateCreated;
    }

    /**
     * Retrieves the project(timeline) date of completion
     * @return Returns the project(timeline) date of completion as a string
     */
    public String getDateFinished(){
        return this.dateFinished;
    }
}
