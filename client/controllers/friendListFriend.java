package controllers;

import java.io.IOException;

import gifty.Client;
import gifty.Connection;
import gifty.dto.Contributor;
import gifty.dto.Friend;
import gifty.dto.User;
import gifty.dto.UserLogin;
import gifty.dto.Wish;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

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
        UserLogin friend = new UserLogin();
        friend.setLogin(userLoginLabel.getText());
        friend.setName(fullnameLabel.getText());

        Platform.runLater(() -> {
            try {

                Connection removeFriend = new Connection();

                removeFriend.getOutput().writeObject("Remove Friend");
                removeFriend.getOutput().writeObject(Client.currentUser);
                removeFriend.getOutput().writeObject(friend);

                String responce = (String)removeFriend.getInput().readObject();

                switch (responce) {

                case "User":
                    Client.currentUser = (User)removeFriend.getInput().readObject();
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
    void showWishesAction(ActionEvent event) {

        VBox wishes = new VBox();

        Platform.runLater(() -> {

            try {

                for (Friend friend : Client.currentUser.getfriendList()) {

                    if (friend.getLogin().equals(userLoginLabel.getText())) {

                        for (Wish wish : friend.getWishList()) {

                            FXMLLoader loader = new FXMLLoader(
                                    getClass().getResource("../fxmls/friendListWish.fxml"));
                            friendListWish controller = new friendListWish();
                            loader.setController(controller);

                            wishes.getChildren().add(loader.load());

                            controller.setItemLabel(wish.getItem().getItem_name());
                            controller.setCategoryLabel(wish.getItem().getCategory());

                            Double price = wish.getItem().getPrice();
                            controller.setPriceLabel(Double.toString(price));

                            Double total_mony = 0.0;
                            for (Contributor contributor : wish.getConributUsers())
                                total_mony += contributor.getAmount();
                            controller.setMoneyLeftLabel(Double.toString(price - total_mony));
                        }
                    }
                }

                ScrollPane parent = (ScrollPane)btnWishs.getScene().lookup("#wishlistPane");
                parent.setContent(wishes);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

}
