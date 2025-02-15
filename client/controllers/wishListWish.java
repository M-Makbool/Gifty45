package controllers;

import java.io.IOException;

import gifty.Client;
import gifty.Connection;
import gifty.dto.Contributor;
import gifty.dto.User;
import gifty.dto.Wish;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class wishListWish {

    @FXML
    private Label itemLabel;

    @FXML
    private Label categoryLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label moneyLeftLabel;

    @FXML
    private Button btnRemoveWish;

    @FXML
    private Label dateLabel;

    private Wish thisWish;

    @FXML
    void removeWishAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {

                Connection removewish = new Connection();

                removewish.getOutput().writeObject("Remove Wish");
                removewish.getOutput().writeObject(Client.currentUser);
                removewish.getOutput().writeObject(thisWish);

                String responce = (String)removewish.getInput().readObject();

                switch (responce) {

                case "User":
                    Client.currentUser = (User)removewish.getInput().readObject();
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

    public void setWish(Wish wish) {

        this.thisWish = wish;
        itemLabel.setText(wish.getItem().getItem_name());
        categoryLabel.setText(wish.getItem().getCategory());
        dateLabel.setText(wish.getDate().toString());

        Double price = wish.getItem().getPrice();
        priceLabel.setText(Double.toString(price));

        Double total_mony = 0.0;
        for (Contributor contributor : wish.getConributUsers())
            total_mony += contributor.getAmount();

        moneyLeftLabel.setText(Double.toString(price - total_mony));
    }

}
