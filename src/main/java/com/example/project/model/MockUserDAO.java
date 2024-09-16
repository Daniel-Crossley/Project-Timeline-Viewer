package com.example.project.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MockUserDAO {
    public static final ArrayList<User> users = new ArrayList<>();

    public MockUserDAO() {
        // Add some initial contacts to the mock database
        addUser(new User("user1", "email1", "password1"));
        addUser(new User("user2", "email2", "password2"));
        addUser(new User("user3", "email3", "password3"));
    }

    public void addUser(User user) {
        users.add(user);
    }

    public String getUsername(String username) {
        for (User user : users) {
            if (Objects.equals(user.getUsername(), username)) {
                return username;
            }
        }
        return null;
    }

    public String getEmail(String email) {
        for (User user : users) {
            if (Objects.equals(user.getEmail(), email)) {
                return email;
            }
        }
        return null;
    }

    public List<User> getAllContacts() {
        return new ArrayList<>(users);
    }
}
