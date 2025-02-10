package gifty;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection extends Thread {
    private static ServerSocket serverSocket;
    private static Boolean running;

    @SuppressWarnings("CallToPrintStackTrace")
    public void serverInit() throws IOException {

        serverSocket = new ServerSocket(5000);
        System.out.println("Server started on port 5000...");
        running = true;
        start();
    }

    public void serverStop() throws IOException {
        if (serverSocket != null && !serverSocket.isClosed()) {
            running = false;
            serverSocket.close();
        }
    }

    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public void run() {
        while (running) {
            try {
                Socket socket = serverSocket.accept();
                new ClientHandler(socket).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        interrupt();
        System.out.println("Server stopped.");
    }
}
