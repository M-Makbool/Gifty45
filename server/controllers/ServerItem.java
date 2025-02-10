package controllers;

import gifty.dao.ItemQuery;
import gifty.dto.Item;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ServerItem {

    @FXML
    private TextField categoryField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private TextField itemNameField;

    @FXML
    private TextField priceField;

    @FXML
    private Label labelNotify;

    @FXML
    void addItemAction(ActionEvent event) {
        try {
            if (!priceField.getText().matches("\\d+(\\.\\d{1,2})?")) {
                labelNotify.setStyle("-fx-text-fill: red;");
                labelNotify.setText("Invalid price format. Please enter a valid number.");
                return;
            }
            int id = ItemQuery.addItem(new Item(0, itemNameField.getText(), categoryField.getText(),
                    Double.parseDouble(priceField.getText()), descriptionField.getText()));
            if (id > 0) {
                labelNotify.setStyle("-fx-text-fill: green;");
                labelNotify.setText("Item Added Successfully with id: " + id);
            } else {
                labelNotify.setStyle("-fx-text-fill: red;");
                labelNotify.setText("Item Faield");
            }
        } catch (SQLException e) {
            labelNotify.setStyle("-fx-text-fill: red;");
            labelNotify.setText("Item Failed");
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
