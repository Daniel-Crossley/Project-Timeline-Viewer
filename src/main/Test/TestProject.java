import com.example.project.model.Card;
import com.example.project.model.Project;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestProject {

    public Project projectSetup(){
        int testID = 0;
        String testTitle = "testTitle";
        String testDescription = "testDescription";
        String testDateCreated = "23/11/1963";
        String testDateCompleted = "23/11/1963";
        String testColour = "#ffffff";
        int testLikes = 0;
        return new Project(testID, testTitle, testDescription, testDateCreated, testDateCompleted, testColour, testLikes);
    }

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
        //Setup List of Cards
        List<Card> testCardList = randomCardListSetup(0);

        //Setup Timeline Object
        Project testProject = projectSetup();
        testProject.SetListofCards(testCardList);

        //Test
        assertEquals(testProject.getListOfCards(),testCardList,"No cards added test");
    }

    @Test
    void testCardImportOne() {
        //Setup List of Cards
        List<Card> testCardList = randomCardListSetup(1);

        //Setup Timeline Object
        Project testProject = projectSetup();
        testProject.SetListofCards(testCardList);

        //Test
        assertEquals(testProject.getListOfCards(),testCardList,"No cards added test");
    }

    @Test
    void testCardImportFive() {
        //Setup List of Cards
        List<Card> testCardList = randomCardListSetup(5);

        //Setup Timeline Object
        Project testProject = projectSetup();
        testProject.SetListofCards(testCardList);

        //Test
        assertEquals(testProject.getListOfCards(),testCardList,"No cards added test");
    }



}
