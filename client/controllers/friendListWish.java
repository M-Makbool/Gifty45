package controllers;

import java.io.IOException;

import gifty.Client;
import gifty.Connection;
import gifty.dto.Contributor;
import gifty.dto.Friend;
import gifty.dto.User;
import gifty.dto.Wish;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class friendListWish {

    @FXML
    private Label itemLabel;

    @FXML
    private Label categoryLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label moneyLeftLabel;

    @FXML
    private Button btnContribute;

    @FXML
    private TextField contributeAmountTxt;

    private Friend thisFriend;

    public void setFriend(Friend friend) { this.thisFriend = friend; }

    private Wish thisWish;

    @FXML
    void contributeAction(ActionEvent event) {

        String amountText = contributeAmountTxt.getText();

        if (amountText.matches("\\d+(\\.\\d{1,2})?")) {

            Double amount = Double.parseDouble(amountText);
            Platform.runLater(() -> {

                try {

                    Connection contribute = new Connection();

                    contribute.getOutput().writeObject("Contribute");
                    contribute.getOutput().writeObject(Client.currentUser);
                    contribute.getOutput().writeObject(thisFriend);
                    contribute.getOutput().writeObject(thisWish);
                    contribute.getOutput().writeObject(amount);

                    String responce = (String)contribute.getInput().readObject();

                    switch (responce) {

                    case "User":
                        Client.currentUser = (User)contribute.getInput().readObject();
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
        } else {
            contributeAmountTxt.setText("not valid");
        }

    }

    public void setWish(Wish wish) {

        this.thisWish = wish;
        itemLabel.setText(wish.getItem().getItem_name());
        categoryLabel.setText(wish.getItem().getCategory());
        // dateLabel.setText(wish.getDate().toString());

        Double price = wish.getItem().getPrice();
        priceLabel.setText(Double.toString(price));

        Double total_mony = 0.0;
        for (Contributor contributor : wish.getConributUsers())
            total_mony += contributor.getAmount();

        moneyLeftLabel.setText(Double.toString(price - total_mony));
    }

}
