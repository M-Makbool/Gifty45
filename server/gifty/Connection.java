package gifty;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection {

 
   public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server started on port 5000...");

            while (true) {
                Socket socket = serverSocket.accept();
                new ClientHandler(socket).start(); // Handle client in a new thread
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}





