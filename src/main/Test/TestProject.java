import com.example.project.model.Card;
import com.example.project.model.Project;
import org.junit.jupiter.api.Test;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestProject {

    public Project projectSetup(){
        int testID = 0;
        String testTitle = "testTitle";
        String testDescription = "testDescription";
        Date testDateCreated = Date.valueOf(LocalDate.now());
        Date testDateCompleted = Date.valueOf(LocalDate.now());
        String testColour = "#ffffff";
        int testLikes = 0;
        return new Project(testID, testTitle, testDescription, testDateCreated, testDateCompleted, true, testColour, testLikes, "Tag");
    }

    public List<Card> randomCardListSetup(int numberOfCards) {
        List<Card> setOfCards = new ArrayList<>();

        if (numberOfCards != 0) {
            for (int i = 0; i < numberOfCards; i++) {
                String cardTitle = RandomString(5);
                String cardDescription = RandomString(20);
                Date dateCreated = Date.valueOf(LocalDate.now());
                Date dateFinished = Date.valueOf(LocalDate.now());
                Card randomCard = new Card(i, cardTitle, cardDescription, dateCreated, dateFinished);
                setOfCards.add(randomCard);
            }
        }
        return setOfCards;
    }

    public String RandomString(int CharacterLength){
        StringBuilder randomString = new StringBuilder();
        String setCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int characterSetLength = setCharacters.length();
        int counter = 0;
        while (counter < CharacterLength) {
            randomString.append(setCharacters.charAt((int) Math.floor(Math.random() * characterSetLength)));
            counter += 1;
        }
        return randomString.toString();
    }

    @Test
    void testCardImportNone() {
        int numberTested = 0;
        //Setup List of Cards
        List<Card> testCardList = randomCardListSetup(numberTested);

        //Setup Timeline Object
        Project testProject = projectSetup();
        testProject.SetListofCards(testCardList);

        //Test
        assertEquals(testProject.getListOfCards(),testCardList,"No cards added test");
    }

    @Test
    void testCardImportOne() {
        int numberTested = 1;
        //Setup List of Cards
        List<Card> testCardList = randomCardListSetup(numberTested);

        //Setup Timeline Object
        Project testProject = projectSetup();
        testProject.SetListofCards(testCardList);

        //Test
        assertEquals(testProject.getListOfCards(),testCardList,"No cards added test");
    }

    @Test
    void testCardImportFive() {
        int numberTested = 5;
        //Setup List of Cards
        List<Card> testCardList = randomCardListSetup(numberTested);

        //Setup Timeline Object
        Project testProject = projectSetup();
        testProject.SetListofCards(testCardList);

        //Test
        assertEquals(testProject.getListOfCards(),testCardList,"No cards added test");
    }

    @Test
    void testCardAddOne(){
        int numberTested = 1;
        //Setup List of Cards
        List<Card> testCardList = randomCardListSetup(1);

        //Setup Timeline Object
        Project testProject = projectSetup();
        testProject.SetListofCards(testCardList);

        //New Card
        List<Card> newCard = randomCardListSetup(numberTested);

        for (Card card : newCard) {
            testProject.addCard(card);
            testCardList.add(card);
        }

        //Test
        assertEquals(testProject.getListOfCards(),testCardList,"No cards added test");
    }

    @Test
    void testCardAddFive(){
        int numberTested = 5;
        //Setup List of Cards
        List<Card> testCardList = randomCardListSetup(1);

        //Setup Timeline Object
        Project testProject = projectSetup();
        testProject.SetListofCards(testCardList);

        //New Card
        List<Card> newCard = randomCardListSetup(numberTested);

        for (Card card : newCard) {
            testProject.addCard(card);
            testCardList.add(card);
        }

        //Test
        assertEquals(testProject.getListOfCards(),testCardList,"No cards added test");
    }


}
