package com.example.project.controller;

import com.example.project.ApplicationStart;
import com.example.project.model.SqliteUserDAO;
import com.example.project.model.MockUserDAO;
import com.example.project.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;


public class RegisterController {
    @FXML
    private ListView<User> contactsListView;
    private SqliteUserDAO userDAO;

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
    private Button registerButton;

    @FXML
    private Button backButton;

    @FXML
    protected void onBack() throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), ApplicationStart.WIDTH, ApplicationStart.HEIGHT);
        stage.setScene(scene);
    }

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
    private void onAdd() throws IOException {
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

        User search = userDAO.getUser(USERNAME);

        if (search != null) {
            registerErrorText.setText("User Already exists");
            return;
        }

        User newUser = new User(USERNAME, HASHED_PASSWORD, EMAIL);
        // Add the new contact to the database
        if (userDAO.addUser(newUser)) {
            Stage stage = (Stage) registerButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), ApplicationStart.WIDTH, ApplicationStart.HEIGHT);
            stage.setScene(scene);
        } else {
            registerErrorText.setText("Email has already been registered");
            return;
        }
    }

    //@FXML
    //protected void onHelloButtonClick() {
        //registerErrorText.setText("");
    //}

    public RegisterController() {
        userDAO = new SqliteUserDAO();
    }
}
