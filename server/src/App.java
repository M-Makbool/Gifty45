package src;

import gifty.dao.*;
import gifty.dto.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

        @Override
        public void start(Stage primaryStage) throws IOException {

                Parent root = FXMLLoader.load(getClass().getResource("Login-Hania.fxml"));
                Scene scene = new Scene(root, 1080, 720);

                primaryStage.setTitle("Hello World!");
                primaryStage.setScene(scene);
                primaryStage.show();
        }

        @SuppressWarnings("CallToPrintStackTrace")
        public static void main(String[] args) {

                launch(args);

                try {
                        ItemQuery.addItem(new Item(0, "laptop", "devices", 50000, "AVAILABLE"));
                        ItemQuery.addItem(new Item(0, "Iphone_11", "devices", 20000, "AVAILABLE"));
                        UserQuery.addUser(new User(new UserLogin("hamada  hamdy  ", "hamada"),
                                        "hamada@example.com", "Male", "123456789",
                                        Date.valueOf(java.time.LocalDate.of(2000, 1, 1))));
                        UserQuery.addUser(new User(
                                        new UserLogin("  mahmoud abd   elsameea", " mahmoud"),
                                        "mahmoud@example.com", "Female", "987654321",
                                        Date.valueOf(java.time.LocalDate.of(2001, 2, 2))));
                        UserQuery.addWish(UserQuery.getUsers().get(0), ItemQuery.getItems().get(0));
                        UserQuery.addFriend(UserQuery.getUsers().get(0),
                                        UserQuery.getUsers().get(1));
                        UserQuery.acceptFriend(UserQuery.getUsers().get(1),
                                        UserQuery.getUsers().get(0));
                        UserQuery.contribute(
                                        UserQuery.getFriends(UserQuery.getUsers().get(1)).get(0),
                                        UserQuery.getUsers().get(1),
                                        UserQuery.getWishList(UserQuery
                                                        .getFriends(UserQuery.getUsers().get(1))
                                                        .get(0)).get(0),
                                        90);
                        System.out.println(UserQuery.getContributors(
                                        UserQuery.getFriends(UserQuery.getUsers().get(1)).get(0),
                                        UserQuery.getWishList(UserQuery
                                                        .getFriends(UserQuery.getUsers().get(1))
                                                        .get(0)).get(0))
                                        .get(0).getLogin());
                } catch (SQLException e) {
                        e.printStackTrace();
                }

        }

}
