package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
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
    private void onAdd() {
        // Default values for a new contact
        String USERNAME = usernameField.getText();
        String EMAIL = emailField.getText();
        String PASSWORD = passwordField.getText();
        String CONFIRM_PASSWORD = confirmPasswordField.getText();

        if (USERNAME.isEmpty() || EMAIL.isEmpty() || PASSWORD.isEmpty() || CONFIRM_PASSWORD.isEmpty()) {
            registerErrorText.setText("Please fill in all fields");
            return;
        }

        if (!PASSWORD.equals(CONFIRM_PASSWORD)) {;
            registerErrorText.setText("Passwords do not match");
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
