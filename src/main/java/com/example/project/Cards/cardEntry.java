package com.example.project.Cards;

public class cardEntry {
    private int ID;
    private String title;
    private String description;
    private String dateCreated;
    private String dateFinished;

    public cardEntry(int setID, String setTitle, String setDescription, String setDateCreated, String setDateFinished){
        this.ID = setID;
        this.title = setTitle;
        this.description = setDescription;
        this.dateCreated = setDateCreated;
        this.dateFinished = setDateFinished;
    }
}
