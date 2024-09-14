import com.example.project.model.Card;
import com.example.project.model.CardComment;
import com.example.project.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestComment {
    @Test
    void commentID(){
        int id = 0;
        String comment = "This is a comment.";
        String date = "23/11/1963";
        CardComment commentData = new CardComment(id, comment, date);

        assertEquals(id, commentData.getCommentID());
    }

    @Test
    void commentText(){
        int id = 0;
        String comment = "This is a comment.";
        String date = "23/11/1963";
        CardComment commentData = new CardComment(id, comment, date);

        assertEquals(comment, commentData.getText());
    }

    @Test
    void commentDatePublished(){
        int id = 0;
        String comment = "This is a comment.";
        String date = "23/11/1963";
        CardComment commentData = new CardComment(id, comment, date);

        assertEquals(date, commentData.getDatePublished());
    }
}
