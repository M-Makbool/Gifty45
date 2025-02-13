package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class ClientFriendlist extends ClientHome {

    @FXML
    private Label userName;

    @FXML
    public void initialize() {
        super.initialize();
        try {

            Node friendlist = FXMLLoader
                    .load(getClass().getResource("../fxmls/ClientFriendList.fxml"));
            mainPane.getChildren().setAll(friendlist);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    @Override
    public void wishlistAction(ActionEvent event) {

    }

}
