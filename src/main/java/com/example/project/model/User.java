package com.example.project.model;

import java.util.ArrayList;
import java.util.List;

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

    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public String getEmail() {return email;}
    public List<Project> getProjects() {return projects;}
    public void setUsername(String username){this.username = username;}

    public void setPassword(String password){this.password=password;}

    public void addProject(Project project){
        this.projects.add(project);
    }
}
