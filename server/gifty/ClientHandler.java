package gifty;

import gifty.dao.ItemQuery;
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
    private static User currentUser;

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
                register(user);
                break;
            }
            case "Add Wish": {
                Item wish = (Item)input.readObject();
                addWish(wish);
                break;
            }
            case "Remove Wish": {
                Wish wish = (Wish)input.readObject();
                removeWish(wish);
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
                getFriend(friend);
                break;
            }
            case "Add Friend": {
                UserLogin friend = (UserLogin)input.readObject();
                addFriend(friend);
                break;
            }
            case "Accept Friend": {
                UserLogin friend = (UserLogin)input.readObject();
                acceptFriend(friend);
                break;
            }
            case "Remove Friend": {
                UserLogin friend = (UserLogin)input.readObject();
                removeFriend(friend);
                break;
            }
            case "Refresh": {
                refresh();
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
                output.writeObject(currentUser);
                output.writeObject(ItemQuery.getItems());
            } else {
                output.writeObject("Not Found");
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void register(User user) {
        try {
            int result = UserQuery.addUser(user);

            if (result > 0) {
                output.writeObject("User");
                User registeredUser = UserQuery.getUser(user);
                output.writeObject(registeredUser);
            } else {

                output.writeObject("Error");
            }
        } catch (SQLException | IOException e) {

            e.printStackTrace();

        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void addWish(Item wish) {
        try {
            int result = UserQuery.addWish(currentUser, wish);

            if (result > 0) {
                output.writeObject("User");
                currentUser = UserQuery.getUser(currentUser);
                output.writeObject(currentUser);
            } else {
                output.writeObject(" Wish could not be added to the database");
            }
        } catch (SQLException | IOException e) {

            e.printStackTrace();

        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void removeWish(Wish wish) {
        try {
            int result = UserQuery.deleteWish(currentUser, wish);

            if (result > 0) {
                ArrayList<Wish> updatedWishList = UserQuery.getWishList(currentUser);
                output.writeObject(" removed wish");
                output.writeObject(updatedWishList);
            } else {

                output.writeObject(" Wish could not be removed from the database.");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();

        }

    }

    public Wish contribute(UserLogin friend, Wish wish) {
        // Implement contribute logic here
        return null;
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void getFriend(UserLogin friend) {
        try {
            User friendUser = UserQuery.getUser(friend);

            if (friendUser != null) {
                output.writeObject("friend");
                output.writeObject(friendUser);
            } else {
                output.writeObject("Get Friend Failed: Friend could not be found in the database.");
            }
        } catch (SQLException | IOException e) {

            e.printStackTrace();

        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void addFriend(UserLogin friend) {
        try {

            int result = UserQuery.addFriend(currentUser, friend);

            if (result > 0) {
                output.writeObject("User");
                friend = UserQuery.getUser(friend);
                output.writeObject(friend);
            } else {
                output.writeObject("Not Found");
            }
        } catch (SQLException | IOException e) {

            e.printStackTrace();

        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void acceptFriend(UserLogin friend) {
        try {
            int result = UserQuery.acceptFriend(currentUser, friend);

            if (result > 0) {
                output.writeObject("User");
                currentUser = UserQuery.getUser(currentUser);
                output.writeObject(currentUser);
            } else {
                output.writeObject("Not Found");
            }
        } catch (SQLException | IOException e) {

            e.printStackTrace();
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void removeFriend(UserLogin friend) {
        try {

            int result = UserQuery.removeFriend(currentUser, friend);

            if (result > 0) {
                currentUser = UserQuery.getUser(currentUser);

                output.writeObject("User");
                output.writeObject(currentUser);
            } else {

                output.writeObject("Not Found");
            }
        } catch (SQLException | IOException e) {

            e.printStackTrace();

        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void refresh() {
        try {
            User updatedUser = UserQuery.getUser(currentUser);

            if (updatedUser != null) {
                output.writeObject("updatedUser");
                output.writeObject(updatedUser);
            } else {

                output.writeObject("Refresh Failed: User data could not be fetched.");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            try {
                output.writeObject("Refresh Error: " + e.getMessage());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}