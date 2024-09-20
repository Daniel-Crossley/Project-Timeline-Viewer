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

    public String getTitle(){
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }

    public String getDateCreated(){
        return this.dateCreated;
    }

    public String getDateFinished(){
        return this.dateFinished;
    }
}
