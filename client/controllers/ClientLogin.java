package controllers;

import gifty.Client;
import gifty.Connection;
import gifty.dto.Item;
import gifty.dto.User;
import gifty.dto.UserLogin;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ClientLogin {

    @FXML
    private Button btnLogin;

    @FXML
    private Label loginLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField passUser;

    @FXML
    private TextField txtUser;

    @FXML
    public void initialize() {

        btnLogin.setDefaultButton(true);

        if (Client.currentLogin != null)
            txtUser.setText(Client.currentLogin.getLogin());
    }

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

    @SuppressWarnings("unchecked")
    @FXML
    void loginAction(ActionEvent event) {

        usernameLabel.setText("");
        passwordLabel.setText("");

        boolean missing = false;

        if (txtUser.getText().equals("")) {
            usernameLabel.setText("Enter username to login!");
            missing = true;
        }

        if (passUser.getText().equals("")) {
            passwordLabel.setText("Enter password to login!");
            missing = true;
        }

        if (missing)
            return;

        Client.currentLogin = new UserLogin("", txtUser.getText());
        Client.currentLogin.setPassword(hash(passUser.getText()));

        Platform.runLater(() -> {
            try {

                Connection login = new Connection();

                login.getOutput().writeObject("Login");
                login.getOutput().writeObject(Client.currentUser);
                login.getOutput().writeObject(Client.currentLogin);

                String responce = (String)login.getInput().readObject();

                switch (responce) {

                case "User":
                    Client.currentUser = (User)login.getInput().readObject();
                    Client.market = (ArrayList<Item>)login.getInput().readObject();
                    new Client().switchScene("Home", (Stage)passUser.getScene().getWindow());
                    break;

                case "Not Found":
                    loginLabel.setText("Wrong username or password!");
                    break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        });
    }

    @FXML
    void registerAction(ActionEvent event) {

        if (txtUser.getText().equals("")) {
            usernameLabel.setText("Enter username to register!");
            return;
        }

        if (passUser.getText().equals("")) {
            passwordLabel.setText("Enter password to register!");
            return;
        }

        Client.currentLogin = new UserLogin("", txtUser.getText());
        Client.currentLogin.setPassword(hash(passUser.getText()));

        new Client().switchScene("Register", (Stage)passUser.getScene().getWindow());

    }

}
