package controllers;
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

    public void setItemLabel(String text) {
        this.itemLabel.setText(text);
    }

    public void setCategoryLabel(String text) {
        this.categoryLabel.setText(text);
    }

    public void setPriceLabel(String text) {
        this.priceLabel.setText(text);
    }

    public void setMoneyLeftLabel(String text) {
        this.moneyLeftLabel.setText(text);
    }

    @FXML
    private Label moneyLeftLabel;

    @FXML
    private Button btnContribute;

    @FXML
    private TextField contributeAmountTxt;

    @FXML
    void contributeAction(ActionEvent event) {

    }

}
