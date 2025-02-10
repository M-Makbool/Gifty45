
import gifty.dto.User;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    public static User currentUser;

    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("fxmls/ClientLogin.fxml"));
        Scene scene = new Scene(root, 1080, 720);

        primaryStage.setTitle("Gifty45");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        launch(args);
    }
}
