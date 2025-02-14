package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class friendListFriend {

    @FXML
    private Label fullnameLabel;

    @FXML
    private Label userLoginLabel;

    @FXML
    private Label dobLabel1;

    @FXML
    private Button btnRemove;

    public void setFullnameLabel(String fullname) { this.fullnameLabel.setText(fullname); }

    public void setUserLoginLabel(String userLogin) { this.userLoginLabel.setText(userLogin); }

    public void setDobLabel1(String dob) { this.dobLabel1.setText(dob); }

    @FXML
    private Button btnWishs;

    @FXML
    void removeFriiendAction(ActionEvent event) {

    }

    @FXML
    void showWishesAction(ActionEvent event) {

    }

}
