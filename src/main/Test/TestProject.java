import com.example.project.model.Card;
import com.example.project.model.Project;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestProject {


    @Test
    void testCardImportNone() {
        //Setup Timeline Object
        int testID = 0;
        String testTitle = "testTitle";
        String testDescription = "testDescription";
        String testDateCreated = "23/11/1963";
        String testDateCompleted = "23/11/1963";
        String testColour = "#ffffff";
        int testLikes = 0;
        Project testProject = new Project(testID, testTitle, testDescription, testDateCreated, testDateCompleted, testColour, testLikes);

        //Setup List of Cards
        List<Card> testCardList = new ArrayList<>();

        assertEquals(testProject.getListOfCards(),testCardList,"No cards added test");


    }


}
