package controllers;

import java.io.IOException;

import gifty.Client;
import gifty.dto.Item;
import gifty.dto.Wish;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

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
    void initialize() {

        searchAction(null);
        VBox wishes = new VBox();

        Platform.runLater(() -> {

            try {

                for (Wish wish : Client.currentUser.getWishList()) {

                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource("../fxmls/wishListWish.fxml"));
                    wishListWish controller = new wishListWish();
                    loader.setController(controller);

                    wishes.getChildren().add(loader.load());

                    controller.setWish(wish);
                }

                wishesPane.setContent(wishes);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    @FXML
    void searchAction(KeyEvent event) {
        VBox items = new VBox();

        Platform.runLater(() -> {

            try {

                for (Item item : Client.market) {

                    if (item.getItem_name().toLowerCase()
                            .matches(".*" + searchField.getText().toLowerCase() + ".*")) {

                        FXMLLoader loader = new FXMLLoader(
                                getClass().getResource("../fxmls/wishListProuducts.fxml"));
                        wishListProuducts controller = new wishListProuducts();
                        loader.setController(controller);

                        items.getChildren().add(loader.load());

                        controller.setItem(item);
                    }
                }

                marketPane.setContent(items);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

}
