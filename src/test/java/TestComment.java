import com.example.project.model.CardComment;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestComment {

    /**
     * Checks to see if the ID of the comment has been properly stored
     */
    @Test
    void commentID(){
        int id = 0;
        String comment = "This is a comment.";
        String date = "23/11/1963";
        CardComment commentData = new CardComment(id, comment, date);

        assertEquals(id, commentData.getCommentID());
    }

    /**
     * Checks to see if the text content of the comment has been properly stored
     */
    @Test
    void commentText(){
        int id = 0;
        String comment = "This is a comment.";
        String date = "23/11/1963";
        CardComment commentData = new CardComment(id, comment, date);

        assertEquals(comment, commentData.getText());
    }

    /**
     * Checks to see if the publish date of the comment has been properly stored
     */
    @Test
    void commentDatePublished(){
        int id = 0;
        String comment = "This is a comment.";
        String date = "23/11/1963";
        CardComment commentData = new CardComment(id, comment, date);

        assertEquals(date, commentData.getDatePublished());
    }
}
