import com.example.project.model.Project;
import com.example.project.model.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUser {

    private List<Project> ProjectLists(){
        List<Project> listOfProjects = new ArrayList<>();
        Project projectToTest = new Project(0,"TestTitle","TestDescription","23/11/1963","23/11/1963", "#", 0);
        listOfProjects.add(projectToTest);
        return listOfProjects;
    }


    @Test
    void setUsername(){
        String username = "testUsername";
        String password = "testPassword";
        String email = "testEmail";

        User tempUser = new User(username,password,email);

        assertEquals(username,tempUser.getUsername());
    }

    @Test
    void setPassword(){
        String username = "testUsername";
        String password = "testPassword";
        String email = "testEmail";

        User tempUser = new User(username,password,email);

        assertEquals(password,tempUser.getPassword());
    }

    @Test
    void setEmail(){
        String username = "testUsername";
        String password = "testPassword";
        String email = "testEmail";


        User tempUser = new User(username,password,email);

        assertEquals(username,tempUser.getEmail());
    }

    @Test
    void setProjects(){
        String username = "testUsername";
        String password = "testPassword";
        String email = "testEmail";

        User tempUser = new User(username,password,email);
        List<Project> projectList = ProjectLists();
        assertEquals(projectList,tempUser.getProjects());
    }



}
