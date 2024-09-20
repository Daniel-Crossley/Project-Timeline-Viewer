package com.example.project.model;

/**
 * Card class to store card data
 */
public class Card {
    private int id;
    private String title;
    private String description;
    private String dateCreated;
    private String dateFinished;
    // Uncomment when comment class is created
    //private List<Comment> comments = new ArrayList<>();

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
