import com.example.project.model.Card;
import com.example.project.model.Project;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestProject {

    /**
     * Generates project to be used for testing
     * @return Returns a generic project to be used for testing
     */
    public Project projectSetup(){
        int testID = 0;
        String testTitle = "testTitle";
        String testDescription = "testDescription";
        String testDateCreated = "23/11/1963";
        String testDateCompleted = "23/11/1963";
        String testColour = "#ffffff";
        int testLikes = 0;
        return new Project(testID, testTitle, testDescription, testDateCreated, testDateCompleted, true, testColour, testLikes, "Tag");
    }

    /**
     * Generates a random number of cards
     * @param numberOfCards The number of random cards to be generated
     * @return a list of randomly generated cards
     */
    public List<Card> randomCardListSetup(int numberOfCards){
        List<Card> setOfCards = new ArrayList<>();

        if (numberOfCards != 0){
            for (int i = 0; i < numberOfCards; i++){
                String cardTitle = RandomString(5);
                String cardDescription = RandomString(20);
                String dateCreated = "23/11/1963";
                String dateFinished = "23/11/1963";
                Card randomCard = new Card(i,cardTitle,cardDescription,dateCreated,dateFinished);
                setOfCards.add(randomCard);
            }
        }
        return setOfCards;
    }

    /**
     * Generates a string of a given length with random characters
     * @param CharacterLength The length of the randomly generated string
     * @return randomly generated string
     */
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

    /**
     * Checks to see if 0 cards are able to be stored properly
     */
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

    /**
     * Checks to see if 1 card is imported properly
     */
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

    /**
     * Checks to see if 5 cards are imported properly
     */
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


    /**
     * Checks to see if a card can be added
     */
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

    /**
     * Checks to see if 5 cards can be added separately
     */

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
