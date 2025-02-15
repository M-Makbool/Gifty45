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
import javafx.scene.control.ProgressBar;
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
    private Label contributeLabel;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Button btnContribute;

    @FXML
    private TextField contributeAmountTxt;

    private Friend thisFriend;

    public void setFriend(Friend friend) { this.thisFriend = friend; }

    private Wish thisWish;

    private Double total_mony = 0.0;

    @FXML
    void contributeAction(ActionEvent event) {

        String amountText = contributeAmountTxt.getText();

        if (amountText.matches("\\d+(\\.\\d{1,2})?")) {

            Double amount = Double.parseDouble(amountText);

            if (amount <= Double.parseDouble(moneyLeftLabel.getText()))

                Platform.runLater(() -> {

                    total_mony += amount;
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

                    progressBar.setProgress((total_mony) / thisWish.getItem().getPrice());
                    moneyLeftLabel
                            .setText(String.valueOf(thisWish.getItem().getPrice() - total_mony));

                    if (total_mony >= thisWish.getItem().getPrice()) {
                        btnContribute.setDisable(true);
                        contributeAmountTxt.setDisable(true);
                        contributeLabel.setStyle("-fx-text-fill: green;");
                        contributeLabel.setText("The Wish is Granted :)");
                        btnContribute.setStyle("-fx-text-fill: green;");
                        progressBar.setStyle("-fx-accent: green;");
                    }

                });
            else
                contributeLabel.setText("Too much money!");

        } else
            contributeLabel.setText("Not valid Num!");

    }

    public void setWish(Wish wish) {

        this.thisWish = wish;
        itemLabel.setText(wish.getItem().getItem_name());
        categoryLabel.setText(wish.getItem().getCategory());

        Double price = wish.getItem().getPrice();
        priceLabel.setText(Double.toString(price));

        for (Contributor contributor : wish.getConributUsers())
            total_mony += contributor.getAmount();

        moneyLeftLabel.setText(Double.toString(price - total_mony));

        progressBar.setProgress(total_mony / price);

        if (total_mony >= price) {
            btnContribute.setDisable(true);
            contributeAmountTxt.setDisable(true);
            contributeLabel.setStyle("-fx-text-fill: green;");
            contributeLabel.setText("The Wish is Granted :)");
            btnContribute.setStyle("-fx-text-fill: green;");
            progressBar.setStyle("-fx-accent: green;");
        }

    }

}
