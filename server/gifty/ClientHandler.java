package gifty;
import gifty.dao.*;
import gifty.dto.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientHandler extends Thread {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {
             
            String request = (String) input.readObject(); // Read request
            
            if ("GET_USERS".equals(request)) {
                ArrayList<UserLogin> users = UserQuery.getUsers(); // Get users from DB
                output.writeObject(users); // Send users list
                System.out.println("New client connected: " + socket.getInetAddress());
            }

        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
