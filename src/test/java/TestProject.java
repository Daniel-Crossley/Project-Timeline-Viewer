import com.example.project.model.Card;
import com.example.project.model.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestProject {

    private Project testProject;

    /**
     * Generates project to be used for testing.
     * Initializes the project before each test.
     */
    @BeforeEach
    public void projectSetup() {
        int testID = 0;
        String testTitle = "testTitle";
        String testDescription = "testDescription";
        String testDateCreated = LocalDate.now().toString();
        String testDateCompleted = LocalDate.now().toString();
        String testColour = "#ffffff";
        int testLikes = 0;
        testProject = new Project(testID, testTitle, testDescription, testDateCreated, testDateCompleted, true, testColour, testLikes, Arrays.asList("Place holder"));
    }

    /**
     * Generates a random number of cards.
     * @param numberOfCards The number of random cards to be generated
     * @return a list of randomly generated cards
     */
    public List<Card> randomCardListSetup(int numberOfCards) {
        List<Card> setOfCards = new ArrayList<>();

        if (numberOfCards != 0) {
            for (int i = 0; i < numberOfCards; i++) {
                String cardTitle = RandomString(5);
                String cardDescription = RandomString(20);
                String dateCreated = LocalDate.now().toString();
                String dateFinished = LocalDate.now().toString();
                Image image = null;
                Card randomCard = new Card(cardTitle, cardDescription, dateCreated, dateFinished, image);
                setOfCards.add(randomCard);
            }
        }
        return setOfCards;
    }

    /**
     * Generates a string of a given length with random characters.
     * @param characterLength The length of the randomly generated string
     * @return randomly generated string
     */
    public String RandomString(int characterLength) {
        StringBuilder randomString = new StringBuilder();
        String setCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789./?";
        int characterSetLength = setCharacters.length();
        int counter = 0;
        while (counter < characterLength) {
            randomString.append(setCharacters.charAt((int) Math.floor(Math.random() * characterSetLength)));
            counter += 1;
        }
        return randomString.toString();
    }

    @Test
    void testCardImportNone() {
        int numberTested = 0;
        List<Card> testCardList = randomCardListSetup(numberTested);

        testProject.SetListofCards(testCardList);

        assertEquals(testProject.getListOfCards(), testCardList, "No cards added test");
    }

    @Test
    void testCardImportOne() {
        int numberTested = 1;
        List<Card> testCardList = randomCardListSetup(numberTested);

        testProject.SetListofCards(testCardList);

        assertEquals(testProject.getListOfCards(), testCardList, "One card added test");
    }

    @Test
    void testCardImportFive() {
        int numberTested = 5;
        List<Card> testCardList = randomCardListSetup(numberTested);

        testProject.SetListofCards(testCardList);

        assertEquals(testProject.getListOfCards(), testCardList, "Five cards added test");
    }

    @Test
    void testCardAddOne() {
        int numberTested = 1;
        List<Card> testCardList = randomCardListSetup(1);

        testProject.SetListofCards(testCardList);

        List<Card> newCard = randomCardListSetup(numberTested);
        for (Card card : newCard) {
            testProject.addCard(card);
            testCardList.add(card);
        }

        assertEquals(testProject.getListOfCards(), testCardList, "One card added test");
    }

    @Test
    void testCardAddFive() {
        int numberTested = 5;
        List<Card> testCardList = randomCardListSetup(1);

        testProject.SetListofCards(testCardList);

        List<Card> newCard = randomCardListSetup(numberTested);
        for (Card card : newCard) {
            testProject.addCard(card);
            testCardList.add(card);
        }

        assertEquals(testProject.getListOfCards(), testCardList, "Five cards added test");
    }
}
