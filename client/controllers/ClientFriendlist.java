package controllers;

import java.io.IOException;

import gifty.Client;
import gifty.Connection;
import gifty.dto.Friend;
import gifty.dto.UserLogin;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
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
    private Label friendlistLabel;

    @FXML
    private ScrollPane friendlistPane;

    @FXML
    private ScrollPane wishlistPane;

    @FXML
    public Button btnRequest;

    public static int rquests;

    @FXML
    void initialize() {
        friendlistAction(new ActionEvent());
        rquests = 0;
        for (Friend friend : Client.currentUser.getfriendList())
            if (friend.getStatus().equals("REQUESTED"))
                rquests++;
        if (rquests > 0) {
            btnRequest.setText("Friend Requestes: " + rquests);
            btnRequest.setStyle("-fx-text-fill: white; -fx-background-color: #55705c;");
        }
    }

    @FXML
    void addfriendAction(ActionEvent event) {

        Platform.runLater(() -> {
            try {
                UserLogin friend = new UserLogin();
                friend.setLogin(addfriendField.getText());

                Connection addfriend = new Connection();

                addfriend.getOutput().writeObject("Add Friend");
                addfriend.getOutput().writeObject(Client.currentUser);
                addfriend.getOutput().writeObject(friend);

                String responce = (String)addfriend.getInput().readObject();

                switch (responce) {

                case "User":
                    friend = (UserLogin)addfriend.getInput().readObject();
                    addfriendLabel.setStyle("-fx-text-fill: green;");
                    addfriendLabel.setText("Friend Request Sended to " + friend.getName());
                    break;

                case "REQUESTED":
                    friend = (UserLogin)addfriend.getInput().readObject();
                    addfriendLabel.setStyle("-fx-text-fill: red;");
                    addfriendLabel.setText("Request already sent to " + friend.getName() + "!");
                    break;

                case "ACCEPTED":
                    friend = (UserLogin)addfriend.getInput().readObject();
                    addfriendLabel.setStyle("-fx-text-fill: red;");
                    addfriendLabel.setText("" + friend.getName() + " already is a friend!");
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

        Platform.runLater(() -> {

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

                friendlistLabel.setStyle("-fx-font-weight: bold;");
                friendlistLabel.setText("--- Your Friend List ---");
                friendlistPane.setContent(frindes);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    @FXML
    void friendrequestAction(ActionEvent event) {
        VBox frindes = new VBox();

        Platform.runLater(() -> {

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

                friendlistLabel.setStyle("-fx-font-weight: bold;");
                friendlistLabel.setText("--- Your Friend Request ---");
                friendlistPane.setContent(frindes);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

}
