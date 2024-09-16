
import com.example.project.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRegister {
    @Test
    void testUsernameAndPassword() {
        // Create a new Login object
        User register = new User("testUser", "testEmail", "testPassword");

        // Assert that the username and password are correctly set
        assertEquals("testUser", register.getUsername(), "Username should be 'testUser'");
        assertEquals("testPassword", register.getPassword(), "Password should be 'testPassword'");

        // Update the username and password
        register.setUsername("newUser");
        register.setPassword("newPassword");

        // Assert that the username and password have been updated
        assertEquals("newUser", register.getUsername(), "Username should be 'newUser'");
        assertEquals("newPassword", register.getPassword(), "Password should be 'newPassword'");
    }

    @Test
    void testEmptyRegister() {
        // Test an empty Login object
        User emptyRegister = new User("", "", "");

        // Assert that both username and password are empty strings
        assertEquals("", emptyRegister.getUsername(), "Username should be an empty string");
        assertEquals("", emptyRegister.getPassword(), "Password should be an empty string");
    }


}
