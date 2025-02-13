package controllers;

import gifty.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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

    public void initialize() {

        fullnameLabel.setText(Client.currentUser.getName());
        usernameLabel.setText("@" + Client.currentUser.getLogin());
        balanceLabel.setText(Double.toString(Client.currentUser.getBalance()) + " L.E.");
    }

    @FXML
    void friendlistAction(ActionEvent event) {}

    @FXML
    void wishlistAction(ActionEvent event) {}

    @FXML
    void logoutAction(ActionEvent event) {

        new Client().switchScene("Login", (Stage)usernameLabel.getScene().getWindow());

        Client.currentLogin = null;
        Client.currentUser = null;
    }

}
