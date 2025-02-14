package controllers;

import java.io.IOException;

import gifty.Client;
import gifty.Connection;
import gifty.dto.Friend;
import gifty.dto.User;
import gifty.dto.UserLogin;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ClientFriendlist {

    @FXML
    private TextField addfriendField;

    @FXML
    private Label addfriendLabel;

    @FXML
    private ScrollPane friendlistPane;

    @FXML
    void initialize() {

    }

    @FXML
    void addfriendAction(ActionEvent event) {

        Platform.runLater(() -> {
            try {
                UserLogin friend = new UserLogin();
                friend.setLogin(addfriendField.getText());

                Connection addfriend = new Connection();

                addfriend.getOutput().writeObject("Add Friend");
                System.out.println(friend.getLogin());
                addfriend.getOutput().writeObject(friend);

                String responce = (String)addfriend.getInput().readObject();

                switch (responce) {

                case "User":
                    Client.currentUser = (User)addfriend.getInput().readObject();
                    addfriendLabel.setStyle("-fx-text-fill: green;");
                    for (UserLogin friends : Client.currentUser.getfriendList())
                        if (friends.getLogin().equals(friend.getLogin())) {
                            addfriendLabel.setText("Friend Request Sended to " + friends.getName());
                            break;
                        }
                    break;

                case "Not Found":
                    addfriendLabel.setText("Wrong username of Friend");
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
    void friendlistAction(ActionEvent event) {

        VBox frindes = new VBox();

        try {

            for (Friend friend : Client.currentUser.getfriendList()) {

                if (friend.getStatus().equals("ACCEPTED")) {

                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource("../fxmls/friendListFriend.fxml"));
                    friendListFriend controller = new friendListFriend();
                    loader.setController(controller);

                    frindes.getChildren().add(loader.load());

                    controller.setFullnameLabel(friend.getName());
                    controller.setUserLoginLabel(friend.getLogin());
                    controller.setDobLabel1(friend.getDateOfBirth().toString());
                }
            }

            friendlistPane.setContent(frindes);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void friendrequestAction(ActionEvent event) {
        VBox frindes = new VBox();

        try {

            for (Friend friend : Client.currentUser.getfriendList()) {

                if (friend.getStatus().equals("REQUESTED")) {

                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource("../fxmls/friendListRequest.fxml"));
                    friendListReuest controller = new friendListReuest();
                    loader.setController(controller);

                    frindes.getChildren().add(loader.load());

                    controller.setFullnameLabel(friend.getName());
                    controller.setUserLoginLabel(friend.getLogin());
                    controller.setDobLabel1(friend.getDateOfBirth().toString());
                }
            }

            friendlistPane.setContent(frindes);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void friendlist(Object frinObject) {

    }

}
