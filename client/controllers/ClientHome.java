package controllers;

import java.io.IOException;

import gifty.Client;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ClientHome {

    @FXML
    private Label balanceLabel;

    @FXML
    private Label fullnameLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    protected AnchorPane mainPane;

    @FXML
    public void initialize() {

        fullnameLabel.setText(Client.currentUser.getName());
        usernameLabel.setText("@" + Client.currentUser.getLogin());
        balanceLabel.setText(Double.toString(Client.currentUser.getBalance()) + " L.E.");
    }

    @FXML
    void friendlistAction(ActionEvent event) {
        Platform.runLater(() -> {

            try {

                Node friendlist = FXMLLoader
                        .load(getClass().getResource("../fxmls/ClientFriendList.fxml"));
                mainPane.getChildren().setAll(friendlist);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    @FXML
    void wishlistAction(ActionEvent event) {
        Platform.runLater(() -> {

            try {

                Node wishlist = FXMLLoader
                        .load(getClass().getResource("../fxmls/ClientWishList.fxml"));
                mainPane.getChildren().setAll(wishlist);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    @FXML
    void logoutAction(ActionEvent event) {

        new Client().switchScene("Login", (Stage)usernameLabel.getScene().getWindow());

        Client.currentLogin = null;
        Client.currentUser = null;
    }

}
