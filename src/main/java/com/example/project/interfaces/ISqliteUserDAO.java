package com.example.project.interfaces;
import com.example.project.model.User;

public interface ISqliteUserDAO {
    /**
     * Gets user based on the primary key username
     * @param username the primary key
     * @return A User object
     */
    public User getUser(String username);


    /**
     * Gets user based on the secondary key email
     * @param email the primary key
     * @return A User object
     */
    public User getEmail(String email);

    /**
     * Adds user to database
     * @param user User object
     * @return True if upload was successful False if otherwise
     */
    public boolean addUser(User user);
}
