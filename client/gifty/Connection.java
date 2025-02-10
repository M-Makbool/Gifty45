
package gifty;

import java.io.*;
import java.net.*;

public class Connection {
    private final Socket socket;
    private final ObjectOutputStream output;
    private final ObjectInputStream input;

    public Connection() throws UnknownHostException, IOException {
        socket = new Socket("localhost", 5000);
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
    }

    public ObjectOutputStream getOutput() throws IOException { return output; }

    public ObjectInputStream getInput() throws IOException { return input; }
}
