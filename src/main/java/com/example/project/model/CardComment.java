package com.example.project.model;

public class CardComment {
    int ID;
    String text;
    String date;

    public CardComment(int id,String text,String date){
        this.ID = id;
        this.text = text;
        this.date = date;
    }

    /**
     * Retrieves the comment's id
     * @return the id of the comment
     */
    public int getCommentID() {return ID;}

    /**
     * Retrieves the comment's text content
     * @return the commment's text content
     */
    public String getText() {return text;}

    /**
     * Retrieves the comment's publish date
     * @return the date published as a String
     */
    public String getDatePublished() {return this.date;}
}
