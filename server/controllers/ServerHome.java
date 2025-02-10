package controllers;

import gifty.Connection;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ServerHome {

    @FXML
    void AddBalanceAction(ActionEvent event) {

    }

    @FXML
    void AddItemAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxmls/ServerItem.fxml"));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void startAction(ActionEvent event) {
        try {
            Connection con = new Connection();
            con.serverInit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void stopAction(ActionEvent event) {
        try {
            Connection con = new Connection();
            con.serverStop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
