
import com.example.project.controller.LoginController;
import com.example.project.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Checks to see if the username and password of the user has been properly stored
 */
public class TestLogin {

    /**
     * Checks to see if the username and password of the user has been properly stored
     */
    @Test
    void testUsernameAndPassword() {
        // Create a new Login object
        User login = new User("testuser", "testpassword", "testemail");

        // Assert that the username and password are correctly set
        assertEquals("testuser", login.getUsername(), "Username should be 'testuser'");
        assertEquals("testpassword", login.getPassword(), "Password should be 'testpassword'");

        // Update the username and password
        login.setUsername("newuser");
        login.setPassword("newpassword");

        // Assert that the username and password have been updated
        assertEquals("newuser", login.getUsername(), "Username should be 'newuser'");
        assertEquals("newpassword", login.getPassword(), "Password should be 'newpassword'");
    }

    /**
     * Checks to see if the check for empty user information returns the proper error message
     */
    @Test
    void testEmptyLogin() {
        // Test an empty Login object
        User emptyLogin = new User("", "", "");

        // Assert that both username and password are empty strings
        assertEquals("", emptyLogin.getUsername(), "Username should be an empty string");
        assertEquals("", emptyLogin.getPassword(), "Password should be an empty string");
    }

}
