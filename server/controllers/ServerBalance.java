package controllers;

import gifty.dao.UserQuery;
import gifty.dto.User;
import gifty.dto.UserLogin;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ServerBalance {

    @FXML
    private TextField balanceField;

    @FXML
    private Label labelNotify;

    @FXML
    private TextField userLoginField;

    @FXML
    void addBalanceAction(ActionEvent event) {

        boolean userFound = false;

        try {

            if (!balanceField.getText().matches("\\d+(\\.\\d{1,2})?")) {
                labelNotify.setStyle("-fx-text-fill: red;");
                labelNotify.setText("Invalid Balance format. Please enter a valid number.");
                return;
            }

            for (UserLogin user : UserQuery.getUsers())
                if (user.getLogin().equals(userLoginField.getText())) {
                    userFound = true;
                    break;
                }

            if (!userFound) {
                labelNotify.setStyle("-fx-text-fill: red;");
                labelNotify.setText("Invalid username. Please enter an existing user.");
                return;
            }

            User user = (User)UserQuery.addBalance(
                    new UserLogin("balance", userLoginField.getText()),
                    Double.parseDouble(balanceField.getText()));
            user = UserQuery.getUser(user);

            if (user.getBalance() >= 0) {
                labelNotify.setStyle("-fx-text-fill: green;");
                labelNotify.setText("Balance Added Successfully. Total Balance is: "
                        + user.getBalance() + " L.E. for user: " + user.getName());
            } else {
                labelNotify.setStyle("-fx-text-fill: red;");
                labelNotify.setText("Balance Faield");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goHomeAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxmls/ServerHome.fxml"));
            Stage stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
