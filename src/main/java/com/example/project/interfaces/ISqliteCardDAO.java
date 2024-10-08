package com.example.project.interfaces;

import com.example.project.model.Card;
import com.example.project.model.Project;

import java.util.List;

public interface ISqliteCardDAO {
    /**
     * @param id primary key of card
     * @return a card object
     */
    public Card getCard(int id);

    /**
     * get all cards owned by project
     * @param project project to add cards to
     */
    public List<Card> getCards(Project project);

    /**
     * @param card card object
     * @param project project object for foreign key
     */
    public void addCard(Card card, Project project);
}
