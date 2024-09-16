package com.example.project.controller;

import com.example.project.model.IUserDAO;
import com.example.project.model.MockUserDAO;
import com.example.project.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class RegisterController {
    @FXML
    private ListView<User> contactsListView;
    private IUserDAO userDAO;

    @FXML
    private Label registerErrorText;

    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button onAdd;

    @FXML
    private String hashing(String password) {
        try {
            // 256 algo
            MessageDigest digest=MessageDigest.getInstance("Sha-256");

            //hashing password to bytes
            byte[] hashByte = digest.digest(password.getBytes());

            //back to hex
            StringBuilder hexString = new StringBuilder();
            for (byte b: hashByte){
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    private void onAdd() {
        // Default values for a new contact
        String USERNAME = usernameField.getText();
        String EMAIL = emailField.getText();
        String PASSWORD = passwordField.getText();
        String CONFIRM_PASSWORD = confirmPasswordField.getText();
        String HASHED_PASSWORD = hashing(PASSWORD);

        if (USERNAME.isEmpty() || EMAIL.isEmpty() || PASSWORD.isEmpty() || CONFIRM_PASSWORD.isEmpty()) {
            registerErrorText.setText("Please fill in all fields");
            return;
        }

        if (!PASSWORD.equals(CONFIRM_PASSWORD)) {;
            registerErrorText.setText("Passwords do not match");
            return;
        }

        System.out.println(userDAO.getUsername(USERNAME));

        if (userDAO.getUsername(USERNAME) != null) {
            registerErrorText.setText("User Already exists");
            return;
        }

        if (userDAO.getEmail(EMAIL) != null) {
            registerErrorText.setText("Email is already in use");
            return;
        }



        User newUser = new User(USERNAME, EMAIL, PASSWORD);
        // Add the new contact to the database
        userDAO.addUser(newUser);

        List<User> contacts = userDAO.getAllContacts();
        for (User contact : contacts) {
            System.out.println(contact.getUser());
        }
    }

    //@FXML
    //protected void onHelloButtonClick() {
        //registerErrorText.setText("");
    //}

    public RegisterController() {
        userDAO = new MockUserDAO();
    }
}
