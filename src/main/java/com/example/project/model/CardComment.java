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

    public int getCommentID() {return ID;}

    public String getText() {return text;}

    public String getDatePublished() {return this.date;}
}
