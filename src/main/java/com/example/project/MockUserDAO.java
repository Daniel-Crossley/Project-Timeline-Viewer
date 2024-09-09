package com.example.project;

import java.util.ArrayList;
import java.util.List;

public class MockUserDAO implements IUserDAO {
    public static final ArrayList<User> users = new ArrayList<>();

    public MockUserDAO() {
        // Add some initial contacts to the mock database
        addUser(new User("user1", "email1", "password1"));
        addUser(new User("user2", "email2", "password2"));
        addUser(new User("user3", "email3", "password3"));
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public String getUsername(String username) {
        for (User user : users) {
            if (user.getUser() == username) {
                return username;
            }
        }
        return null;
    }

    @Override
    public String getEmail(String email) {
        for (User user : users) {
            if (user.getEmail() == email) {
                return email;
            }
        }
        return null;
    }

    @Override
    public List<User> getAllContacts() {
        return new ArrayList<>(users);
    }
}
