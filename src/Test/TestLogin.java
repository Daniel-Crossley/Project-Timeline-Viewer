
import com.example.project.model.Login;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLogin {
    @Test
    void testUsernameAndPassword() {
        // Create a new Login object
        Login login = new Login("testuser", "testpassword");

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

    @Test
    void testEmptyLogin() {
        // Test an empty Login object
        Login emptyLogin = new Login("", "");

        // Assert that both username and password are empty strings
        assertEquals("", emptyLogin.getUsername(), "Username should be an empty string");
        assertEquals("", emptyLogin.getPassword(), "Password should be an empty string");
    }


}
