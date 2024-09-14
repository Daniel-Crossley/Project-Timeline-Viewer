package com.example.project.controller;

import com.example.project.ApplicationStart;
import com.example.project.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import com.example.project.model.User.*;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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


    /**
     * Login without username and password using guest
     * @throws IOException Issues for login
     */
    @FXML
    protected void onGuestLoginClick() throws IOException {
        Stage stage = (Stage) GuestLogin.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("Owner-Dashboard.fxml"));

        User guestUser = new User("","","");

        DashboardController dashboardController = new DashboardController(true, guestUser);
        // Guest login, so set to `true`
        fxmlLoader.setController(dashboardController);
        Scene scene = new Scene(fxmlLoader.load(), ApplicationStart.WIDTH, ApplicationStart.HEIGHT);
        String stylesheet = ApplicationStart.class.getResource("stylesheet.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);
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
     * login for window
     * @throws IOException Issues with login process
     */
    @FXML
    protected void loginProcess() throws IOException {
        String username = userNameTextField.getText();
        String password = passworPasswordField.getText();
        String HassedPass = hashing(password);
    /*
    //User check for password
    if (userDAOS.verifyUser(username,password)){
        Stage stage = (Stage) loginButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Owner-Dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        String stylesheet = HelloApplication.class.getResource("stylesheet.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);
        stage.setScene(scene);
    }else {
        System.out.println("Invalid username");
    }
    */
        System.out.println(HassedPass);
        //if to login currently in a test mode and not connected to the DB
        if ("test".equals(username) && hashing(password).equals(hashing(password))) {
            Stage stage = (Stage) loginButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("Owner-Dashboard.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), ApplicationStart.WIDTH, ApplicationStart.HEIGHT);
            String stylesheet = ApplicationStart.class.getResource("stylesheet.css").toExternalForm();
            scene.getStylesheets().add(stylesheet);
            stage.setScene(scene);
        }else {
            System.out.println("Invalid username");
            InvalidPass.setVisible(true);
        }

    }


}
