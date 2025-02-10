package gifty;

import gifty.dao.UserQuery;
import gifty.dto.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientHandler extends Thread {
    private final Socket socket;
    private final ObjectOutputStream output;
    private final ObjectInputStream input;
    private User currentUser;

    public ClientHandler(Socket soc) throws IOException {
        this.socket = soc;
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public void run() {
        try {
            String request = (String)input.readObject(); // Read request
            System.out.println("New client connected: " + request); // fortesting

            switch (request) {

            case "Login":
                login((UserLogin)input.readObject());
                break;

            case "Register": {
                User user = (User)input.readObject();
                User registeredUser = register(user);
                output.writeObject(registeredUser);
                break;
            }
            case "Add Wish": {
                Item wish = (Item)input.readObject();
                ArrayList<Wish> wishes = addWish(wish);
                output.writeObject(wishes);
                break;
            }
            case "Remove Wish": {
                Wish wish = (Wish)input.readObject();
                ArrayList<Wish> wishes = removeWish(wish);
                output.writeObject(wishes);
                break;
            }
            case "Contribute": {
                UserLogin friend = (UserLogin)input.readObject();
                Wish wish = (Wish)input.readObject();
                Wish contributedWish = contribute(friend, wish);
                output.writeObject(contributedWish);
                break;
            }
            case "Get Friend": {
                UserLogin friend = (UserLogin)input.readObject();
                User user = getFriend(friend);
                output.writeObject(user);
                break;
            }
            case "Add Friend": {
                UserLogin friend = (UserLogin)input.readObject();
                UserLogin addedFriend = addFriend(friend);
                output.writeObject(addedFriend);
                break;
            }
            case "Accept Friend": {
                UserLogin friend = (UserLogin)input.readObject();
                ArrayList<UserLogin> friends = acceptFriend(friend);
                output.writeObject(friends);
                break;
            }
            case "Remove Friend": {
                UserLogin friend = (UserLogin)input.readObject();
                ArrayList<UserLogin> friends = removeFriend(friend);
                output.writeObject(friends);
                break;
            }
            case "Refresh": {
                User user = refresh();
                output.writeObject(user);
                break;
            }
            default:
                throw new AssertionError("Unknown request: " + request);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void login(UserLogin user) {

        try {

            currentUser = UserQuery.logUser(user);

            if (currentUser != null) {
                output.writeObject("User");
                output.writeObject(user);
            } else {
                output.writeObject("Not Found");
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public User register(User user) {
        // Implement register logic here
        return null;
    }

    public ArrayList<Wish> addWish(Item wish) {
        // Implement add wish logic here
        return null;
    }

    public ArrayList<Wish> removeWish(Wish wish) {
        // Implement remove wish logic here
        return null;
    }

    public Wish contribute(UserLogin friend, Wish wish) {
        // Implement contribute logic here
        return null;
    }

    public User getFriend(UserLogin friend) {
        // Implement add friend logic here
        return null;
    }

    public UserLogin addFriend(UserLogin friend) {
        // Implement add friend logic here
        return null;
    }

    public ArrayList<UserLogin> acceptFriend(UserLogin friend) {
        // Implement accept friend logic here
        return null;
    }

    public ArrayList<UserLogin> removeFriend(UserLogin friend) {
        // Implement remove friend logic here
        return null;
    }

    public User refresh() {
        // Implement refresh logic here
        return null;
    }
}
