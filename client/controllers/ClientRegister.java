package controllers;

import gifty.dto.User;
import java.io.IOException;
import java.sql.Date;

import gifty.Client;
import gifty.Connection;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ClientRegister {

    @FXML
    private Label dobLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label fullnameLabel;

    @FXML
    private Label genderLabel;

    @FXML
    private Label telephoneLabel;

    @FXML
    private Label registerLabel;

    @FXML
    private TextField emailField, usernameField, telephoneField;

    @FXML
    private ChoiceBox<String> genderChoiceBox;

    @FXML
    private DatePicker dobPicker;

    @FXML
    public void initialize() {
        genderChoiceBox.getItems().addAll("Male", "Female");
        genderChoiceBox.setValue("Male");
    }

    public void handleSubmit() {

        if (emailField.getText().equals("")) {
            emailLabel.setText("Enter your email!");
            return;
        }

        if (usernameField.getText().equals("")) {
            fullnameLabel.setText("Enter your Full Name!");
            return;
        }

        if (telephoneField.getText().equals("")) {
            telephoneLabel.setText("Enter your telephone!");
            return;
        }

        if (genderChoiceBox.getValue() == null) {
            genderLabel.setText("Select your gender!");
            return;
        }

        if (dobPicker.getValue() == null) {
            dobLabel.setText("Select your date of birth!");
            return;
        }

        String email = emailField.getText();
        String fullname = usernameField.getText();
        String telephone = telephoneField.getText();
        String gender = genderChoiceBox.getValue();
        Date dob = Date.valueOf(dobPicker.getValue());

        Client.currentLogin.setName(fullname);
        Client.currentUser = new User(Client.currentLogin, email, gender, telephone, dob, 0);
        Client.currentUser.setPassword(Client.currentLogin.getPassword());

        Platform.runLater(() -> {
            try {

                Connection register = new Connection();

                register.getOutput().writeObject("Register");
                register.getOutput().writeObject(Client.currentUser);

                String responce = (String)register.getInput().readObject();

                switch (responce) {

                case "User":
                    Client.currentUser = (User)register.getInput().readObject();
                    new Client().switchScene("Login", (Stage)emailField.getScene().getWindow());
                    break;

                case "Error":
                    registerLabel.setText("Register Error");
                    break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        });

    }

    public void handleBackToLogin() {

        new Client().switchScene("Login", (Stage)emailField.getScene().getWindow());

    }
}
