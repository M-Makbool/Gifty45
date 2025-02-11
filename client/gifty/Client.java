package gifty;

import gifty.dto.User;
import gifty.dto.UserLogin;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Client {

    public static User currentUser;
    public static UserLogin currentLogin;

    public void switchScene(String scene, Stage primaryStage) {
        try {
            Parent root = FXMLLoader
                    .load(getClass().getResource("../fxmls/Client" + scene + ".fxml"));
            primaryStage.setTitle(scene);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
