package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class ClientWishlist {

    @FXML
    private ScrollPane wishesPane;

    @FXML
    private ScrollPane marketPane;

    @FXML
    private TextField searchField;

    @FXML
    private Label userName;

    @FXML
    void searchAction(KeyEvent event) {}

}
