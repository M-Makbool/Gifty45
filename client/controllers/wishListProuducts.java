package controllers;

import java.io.IOException;

import gifty.Client;
import gifty.Connection;
import gifty.dto.Item;
import gifty.dto.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class wishListProuducts {

    @FXML
    private Label itemNameLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label categoryLabel;

    @FXML
    private Button btnWishs;

    private Item thisItem;

    @FXML
    void addWishAction(ActionEvent event) {

        Platform.runLater(() -> {
            try {

                Connection addwish = new Connection();

                addwish.getOutput().writeObject("Add Wish");
                addwish.getOutput().writeObject(thisItem);

                String responce = (String)addwish.getInput().readObject();

                switch (responce) {

                case "User":
                    Client.currentUser = (User)addwish.getInput().readObject();
                    break;

                case "Not Found":
                    break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        });

    }

    public void setItem(Item item) {
        this.thisItem = item;
        itemNameLabel.setText(item.getItem_name());
        priceLabel.setText(String.valueOf(item.getPrice()));
        categoryLabel.setText(item.getCategory());
    }

}
