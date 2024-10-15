package com.example.project.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Used to generate a mock database for testing purposes
 */
public class MockUserDAO {
    public static final ArrayList<User> users = new ArrayList<>();

    /**
     * Add some initial contacts to the mock database
     */
    public MockUserDAO() {
        addUser(new User("user1", "email1", "password1"));
        addUser(new User("user2", "email2", "password2"));
        addUser(new User("user3", "email3", "password3"));
    }

    /**
     * Adds given user to the mock database
     * @param user User to be added
     */
    public void addUser(User user) {
        users.add(user);
    }

    /**
     * Retrieves the username of the user to check if it matches with the given username
     * @param username The username of the username to be matched with
     * @return the matching username otherwise returns null
     */
    public String getUsername(String username) {
        for (User user : users) {
            if (Objects.equals(user.getUsername(), username)) {
                return username;
            }
        }
        return null;
    }

    /**
     * Retrieves the email of the user to check if it matches with the given email
     * @param email The username of the email to be matched with
     * @return the matching email otherwise returns null
     */
    public String getEmail(String email) {
        for (User user : users) {
            if (Objects.equals(user.getEmail(), email)) {
                return email;
            }
        }
        return null;
    }

    /**
     * Retrieves the List of users
     * @return Returns the users as an arraylist
     */
    public List<User> getAllContacts() {
        return new ArrayList<>(users);
    }
}
