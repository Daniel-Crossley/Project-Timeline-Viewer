package com.example.project.model;

import java.util.ArrayList;
import java.util.List;

public class Dashboard {
    User user;
    List<Project> projectList = new ArrayList<>();
    SqliteProjectDAO sqlConnection;

    public Dashboard (String username, String password, String email){
        user = new User(username, password, email);
        RetrieveProjectList();
    }

    /**
     * Retrieves the user information
     * @return the user object
     */
    public User RetrieveUser(){return this.user;}

    /**
     * Retrieves the list of projects from the database
     */
    private void RetrieveProjectList(){}

    /**
     * Retrieves the project list
     * @return The list of Project objects
     */
    public List<Project> GetProjects(){return projectList;}

    /**
     * Manually add a project to list (intended for testing purposes)
     * @param project Project to add to list
     */
    public void AddProject(Project project){projectList.add(project);}
}
