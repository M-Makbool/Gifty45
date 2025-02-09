package controllers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ServerLogin {

    @FXML
    private TextField txtPass;

    @FXML
    private TextField txtUser;

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

    public void loginAction() {

        String hashedPassword = hash(txtPass.getText());
        String hashedUser = hash(txtUser.getText());
        if (hashedUser.equals("21232f297a57a5a743894a0e4a801fc3")
                && hashedPassword.equals("21232f297a57a5a743894a0e4a801fc3")) {
            System.out.println("hello server");
        }
    }

}
