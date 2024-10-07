package com.example.project.model;

import java.util.Date;

public class Card {
    private int id;
    private String title;
    private String description;
    private Date dateCreated;
    private Date dateFinished;
    // Uncomment when comment class is created
    //private List<Comment> comments = new ArrayList<>();

    public Card(int setID, String setTitle, String setDescription, Date setDateCreated, Date setDateFinished){
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

    public java.sql.Date getDateCreated(){
        return (java.sql.Date) this.dateCreated;
    }

    public java.sql.Date getDateFinished(){
        return (java.sql.Date) this.dateFinished;
    }
}
