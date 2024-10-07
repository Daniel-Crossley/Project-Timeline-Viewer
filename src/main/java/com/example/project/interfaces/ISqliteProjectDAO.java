package com.example.project.interfaces;

import com.example.project.model.Project;
import com.example.project.model.User;

public interface ISqliteProjectDAO {
    /**
     * @param id primary key of project
     * @return returns project object with given id
     */
    public Project getProject(int id);


    /**
     * Adds projects to user objects for every
     * project they have created
     * @param user User object to add projects to
     */
    public void getProjects(User user);

    /**
     * @param project the project to be added
     * @param user the use to add the foreign key
     */
    public void addProject(Project project, User user);
}
