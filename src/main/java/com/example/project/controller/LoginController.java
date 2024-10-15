package com.example.project.controller;

import com.example.project.ApplicationStart;
import com.example.project.model.SqliteUserDAO;
import com.example.project.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import com.example.project.model.User.*;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

/**
 * Controller for handling login logic, creates user object
 */
public class LoginController {
    @FXML
    private TextField userNameTextField;
    @FXML
    private PasswordField passworPasswordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button GuestLogin;
    @FXML
    private Label InvalidPass;
    @FXML
    private Button registerButton;

    //SQL user instance
    private SqliteUserDAO userDAO;

    public void initialize() {
        userDAO = new SqliteUserDAO(); // Initialize the DAO here
    }

    /**
     * go to separate registration page
     * @throws IOException
     */
    @FXML
    protected void onRegisterButtonClick() throws IOException {
        Stage stage = (Stage) registerButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("register-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), ApplicationStart.WIDTH, ApplicationStart.HEIGHT);
        stage.setScene(scene);
    }

    /**
     * Login without username and password using guest
     * @throws IOException Issues for login
     */
    @FXML
    protected void onGuestLoginClick() throws IOException {
        Stage stage = (Stage) GuestLogin.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("Owner-Dashboard.fxml"));
        Parent root = fxmlLoader.load();
        DashboardController dashboardController = fxmlLoader.getController();
        User guestUser = new User("", "", "");
        dashboardController.setUserInformation(true, guestUser);
        Scene scene = new Scene(root, ApplicationStart.WIDTH, ApplicationStart.HEIGHT);
        stage.setScene(scene);
    }

    /**
     * Hashes password using Sha-256
     * @param password password to hash
     * @return encrypted password
     */
    private String hashing(String password){
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

    /**
     * This method will be automatically called after the FXML elements are initialized.
     * Initializes the SqliteUserDAO object.
     * @throws IOException Issues with login process
     */
    @FXML
    protected void loginProcess() throws IOException {
        String username = userNameTextField.getText();
        String password = passworPasswordField.getText();
        String HassedPass = hashing(password);

        User user = userDAO.getUser(username);
        if (user != null){
            if (user.getPassword().equals(HassedPass)){
                Stage stage = (Stage) loginButton.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("Owner-Dashboard.fxml"));
                Parent root = fxmlLoader.load();
                DashboardController dashboardController = fxmlLoader.getController();

                dashboardController.setUserInformation(false, user);
                Scene scene = new Scene(root, ApplicationStart.WIDTH, ApplicationStart.HEIGHT);
                stage.setScene(scene);

            }else {
                System.out.println("Invalid username");
                InvalidPass.setVisible(true);
            }
        }
        else {
            System.out.println("Invalid username");
            InvalidPass.setVisible(true);
        }
    }
}
