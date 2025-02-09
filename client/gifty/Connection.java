
package gifty;
import gifty.dto.UserLogin;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Connection {
    public static void connect(String[] args) {
        try (Socket socket = new Socket("localhost", 5000);
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

            output.writeObject("GET_USERS"); // Request user list

            ArrayList<UserLogin> users = (ArrayList<UserLogin>)input.readObject(); // Receive
                                                                                   // users
            for (UserLogin user : users) {
                System.out.println("User: " + user.getName());
                System.out.println("Connected to server...");

            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
