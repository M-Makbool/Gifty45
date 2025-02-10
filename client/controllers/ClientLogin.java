package controllers;

import gifty.Connection;
import gifty.dto.User;
import gifty.dto.UserLogin;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ClientLogin {
    public static User currentUser;

    @FXML
    private Button btnLogin;

    @FXML
    private TextField passUser;

    @FXML
    private TextField txtUser;

    private String hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void loginAction(ActionEvent event) {
        try {

            Connection login = new Connection();

            UserLogin user = new UserLogin("", txtUser.getText());
            user.setPassword(hash(passUser.getText()));

            login.getOutput().writeObject("Login");
            login.getOutput().writeObject(user);

            currentUser = (User)login.getInput().readObject();
            txtUser.setText(currentUser.getName());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // if server accept and respond with user data switch to home scene
    }

    @FXML
    void registerAction(ActionEvent event) {

    }

}
