package controllers;

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
    private Label categoryLabel1;

    public void setItemLabel(String text) { this.itemLabel.setText(text); }

    public void setCategoryLabel(String text) { this.categoryLabel.setText(text); }

    public void setPriceLabel(String text) { this.priceLabel.setText(text); }

    public void setMoneyLeftLabel(String text) { this.moneyLeftLabel.setText(text); }

    public void setCategoryLabel1(String text) { this.categoryLabel1.setText(text); }

    @FXML
    void removeWishAction(ActionEvent event) {

    }

}
