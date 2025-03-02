package controllers;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ServerLogin {

    @FXML
    private TextField txtPass;

    @FXML
    private TextField txtUser;

    @FXML
    private Button btnLogin;

    @FXML
    public void initialize() { btnLogin.setDefaultButton(true); }

    private String hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void loginAction() {

        String hashedPassword = hash(txtPass.getText());
        String hashedUser = hash(txtUser.getText());
        if (hashedUser.equals("21232f297a57a5a743894a0e4a801fc3")
                && hashedPassword.equals("21232f297a57a5a743894a0e4a801fc3")) {
            System.out.println("hello server");
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../fxmls/ServerHome.fxml"));
                Stage stage = (Stage)txtPass.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
