package controllers;

import gifty.Client;
import gifty.Connection;
import gifty.dto.User;
import gifty.dto.UserLogin;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    private Label msgLabel;

    @FXML
    private TextField passUser;

    @FXML
    private TextField txtUser;

    @FXML
    public void initialize() {
        if (Client.currentLogin != null) {
            txtUser.setText(Client.currentLogin.getLogin());
            passUser.setText(Client.currentLogin.getPassword());
        }
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

    @FXML
    void loginAction(ActionEvent event) {

        msgLabel.setStyle("-fx-text-fill: red;");

        if (txtUser.getText().equals("")) {
            msgLabel.setText("Enter username to login!");
            return;
        }

        if (passUser.getText().equals("")) {
            msgLabel.setText("Enter password to login!");
            return;
        }

        Client.currentLogin = new UserLogin("", txtUser.getText());
        Client.currentLogin.setPassword(hash(passUser.getText()));

        Platform.runLater(() -> {
            try {

                Connection login = new Connection();

                login.getOutput().writeObject("Login");
                login.getOutput().writeObject(Client.currentLogin);

                String responce = (String)login.getInput().readObject();

                switch (responce) {

                case "User":
                    Client.currentUser = (User)login.getInput().readObject();
                    new Client().switchScene("Home", (Stage)passUser.getScene().getWindow());
                    break;

                case "Not Found":
                    msgLabel.setStyle("-fx-text-fill: red;");
                    msgLabel.setText("User Not Found!");
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

        msgLabel.setStyle("-fx-text-fill: red;");

        if (txtUser.getText().equals("")) {
            msgLabel.setText("Enter username to register!");
            return;
        }

        if (passUser.getText().equals("")) {
            msgLabel.setText("Enter password to register!");
            return;
        }

        Client.currentLogin = new UserLogin("", txtUser.getText());
        Client.currentLogin.setPassword(hash(passUser.getText()));

        new Client().switchScene("Register", (Stage)passUser.getScene().getWindow());

    }

}
