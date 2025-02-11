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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ClientRegister {

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
            System.out.println("Enter email!");
            return;
        }

        if (usernameField.getText().equals("")) {
            System.out.println("Enter username!");
            return;
        }

        if (telephoneField.getText().equals("")) {
            System.out.println("Enter telephone!");
            return;
        }

        if (genderChoiceBox.getValue() == null) {
            System.out.println("Select gender!");
            return;
        }

        if (dobPicker.getValue() == null) {
            System.out.println("Select date of birth!");
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
                    // msgLabel.setStyle("-fx-text-fill: red;");
                    // msgLabel.setText("User Not Found!");
                    System.out.println("Not Found");
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
