package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class friendListReuest {

    @FXML
    private Label fullnameLabel;

    @FXML
    private Label userLoginLabel;

    @FXML
    private Label dobLabel1;

    @FXML
    private Button btnRemove;

    @FXML
    private Button btnAccept;

    public void setFullnameLabel(String fullname) {
        this.fullnameLabel.setText(fullname);
    }

    public void setUserLoginLabel(String userLogin) {
        this.userLoginLabel.setText(userLogin);
    }

    public void setDobLabel1(String dob) {
        this.dobLabel1.setText(dob);
    }

    @FXML
    void acceptFriendAction(ActionEvent event) {

    }

    @FXML
    void removeFriiendAction(ActionEvent event) {

    }

}
