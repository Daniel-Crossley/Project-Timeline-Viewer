package com.example.project.model;

import java.util.ArrayList;
import java.util.List;

/**
 * User class to store user data
 */
public class User {
    private String username;
    private String password;
    private String email;
    private List<Project> projects = new ArrayList<>();

    public User(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * Retrieves the username from the user object
     * @return Returns the username as a String
     */
    public String getUsername() {return username;}

    /**
     * Retrieves the password from the user object
     * @return Returns the password as a String
     */
    public String getPassword() {return password;}

    /**
     * Retrieves the email from the user object
     * @return Returns the email as a String
     */
    public String getEmail() {return email;}

    /**
     * Retrieves the projects from the user object
     * @return Returns the projects stored in user object
     */
    public List<Project> getProjects() {return projects;}

    /**
     * Sets the username to be used for retrieving user's information
     * @param username The username to be used
     */
    public void setUsername(String username){this.username = username;}

    /**
     * Sets the username to be used for retrieving user's information
     * @param password The password to match with the username
     */
    public void setPassword(String password){this.password=password;}

    /**
     * Adds the project to the list of projects stored in user object
     * @param project The project to be added to the user object
     */
    public void addProject(Project project){
        this.projects.add(project);
    }

    /**
     * sets list of projects
     * @param projects list of projects
     */
    public void setProjects(List<Project> projects){this.projects=projects;}
}
