import com.example.project.model.Project;
import com.example.project.model.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Used to test functions relating to the user such as creation and setting user properties
 */
public class TestUser {

    /**
     * Checks to see if the username is stored properly
     */
    @Test
    void setUsername(){
        String username = "testUsername";
        String password = "testPassword";
        String email = "testEmail";

        User tempUser = new User(username,password,email);

        assertEquals(username,tempUser.getUsername());
    }

    /**
     * Checks to see if the password is stored properly
     */
    @Test
    void setPassword(){
        String username = "testUsername";
        String password = "testPassword";
        String email = "testEmail";

        User tempUser = new User(username,password,email);

        assertEquals(password,tempUser.getPassword());
    }

    /**
     * Checks to see if the email is stored properly
     */
    @Test
    void setEmail(){
        String username = "testUsername";
        String password = "testPassword";
        String email = "testEmail";


        User tempUser = new User(username,password,email);

        assertEquals(email,tempUser.getEmail());
    }

    /**
     * Checks to see if projects are  stored properly
     */
    @Test
    void setProjects(){
        String username = "testUsername";
        String password = "testPassword";
        String email = "testEmail";

        User tempUser = new User(username,password,email);
        List<Project> projectList = new ArrayList<>();
        assertEquals(projectList,tempUser.getProjects());
    }



}
