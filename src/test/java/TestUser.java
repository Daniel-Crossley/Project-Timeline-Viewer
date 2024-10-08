import com.example.project.model.Project;
import com.example.project.model.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUser {

    /**
     * Generates a list of projects to be used for testing
     * @return list of projects
     */
    private List<Project> ProjectLists(){
        List<Project> listOfProjects = new ArrayList<>();
        Project projectToTest = new Project(0,"TestTitle","TestDescription","23/11/1963","23/11/1963", true, "#", 0, Arrays.asList("Tag"));
        listOfProjects.add(projectToTest);
        return listOfProjects;
    }


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
