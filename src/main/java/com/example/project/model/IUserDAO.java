package com.example.project.model;

import java.util.List;

public interface IUserDAO {
    public void addUser(User user);


    public String getUsername(String username);

    public String getEmail(String email);

    public List<User> getAllContacts();
}
