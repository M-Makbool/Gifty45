package controllers;

import java.io.IOException;

import gifty.Client;
import gifty.Connection;
import gifty.dto.User;
import gifty.dto.UserLogin;
import javafx.application.Platform;
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

    public void setFullnameLabel(String fullname) { this.fullnameLabel.setText(fullname); }

    public void setUserLoginLabel(String userLogin) { this.userLoginLabel.setText(userLogin); }

    public void setDobLabel1(String dob) { this.dobLabel1.setText(dob); }

    @FXML
    void acceptFriendAction(ActionEvent event) {

        UserLogin friend = new UserLogin();
        friend.setLogin(userLoginLabel.getText());
        friend.setName(fullnameLabel.getText());

        Platform.runLater(() -> {
            try {

                Connection acceptFriend = new Connection();

                acceptFriend.getOutput().writeObject("Accept Friend");
                acceptFriend.getOutput().writeObject(friend);

                String responce = (String)acceptFriend.getInput().readObject();

                switch (responce) {

                case "User":
                    Client.currentUser = (User)acceptFriend.getInput().readObject();
                    btnAccept.setDisable(true);
                    btnRemove.setDisable(true);
                    break;

                case "Not Found":
                    // loginLabel.setText("Wrong username or password!");
                    break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        });
    }

    @FXML
    void removeFriiendAction(ActionEvent event) {
        UserLogin friend = new UserLogin();
        friend.setLogin(userLoginLabel.getText());
        friend.setName(fullnameLabel.getText());

        Platform.runLater(() -> {
            try {

                Connection removeFriend = new Connection();

                removeFriend.getOutput().writeObject("Remove Friend");
                removeFriend.getOutput().writeObject(friend);

                String responce = (String)removeFriend.getInput().readObject();

                switch (responce) {

                case "User":
                    Client.currentUser = (User)removeFriend.getInput().readObject();
                    btnAccept.setDisable(true);
                    btnRemove.setDisable(true);
                    break;

                case "Not Found":
                    // loginLabel.setText("Wrong username or password!");
                    break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        });
    }

}
