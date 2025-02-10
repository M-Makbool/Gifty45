package gifty.dao;

import gifty.dto.Contributor;
import gifty.dto.Item;
import gifty.dto.User;
import gifty.dto.UserLogin;
import gifty.dto.Wish;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserQuery {

    public static ArrayList<UserLogin> getUsers() throws SQLException {

        String query = "SELECT User_login, user_name FROM users WHERE is_deleted = false";

        try (PreparedStatement pst = DataBase.getConnection().prepareStatement(query);
                ResultSet rs = pst.executeQuery()) {

            ArrayList<UserLogin> users = new ArrayList<>();
            while (rs.next()) {
                String userName = rs.getString("user_name");
                String userLogin = rs.getString("User_login");
                users.add(new UserLogin(userName, userLogin));
            }

            return users;
        }
    }

    public static int addUser(User user) throws SQLException {

        String query = "INSERT INTO users (user_login, user_email, user_name, telephone, is_deleted, gender, DOB, Password) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pst = DataBase.getConnection().prepareStatement(query)) {
            pst.setString(1, user.getLogin());
            pst.setString(2, user.getEmail());
            pst.setString(3, user.getName());
            pst.setString(4, user.getTelephone());
            pst.setBoolean(5, false);
            pst.setString(6, user.getGender());
            pst.setDate(7, user.getDateOfBirth());
            pst.setString(8, user.getPassword());

            return pst.executeUpdate();
        }
    }

    public static User getUser(UserLogin userlogin) throws SQLException {

        try (PreparedStatement pst = DataBase.getConnection().prepareStatement(
                " select * from users where user_login = ? and Is_Deleted = false ")) {
            pst.setString(1, userlogin.getLogin());
            User user;
            try (ResultSet rs = pst.executeQuery()) {
                rs.next();
                user = new User(new UserLogin(rs.getString("user_name"), userlogin.getLogin()),
                        rs.getString("user_email"), rs.getString("Gender"),
                        rs.getString("telephone"), rs.getDate("DOB"), rs.getDouble("balance"));
            }
            user.setFriendList(new ArrayList<>(getFriends(user)));
            user.setWishList(new ArrayList<>(getWishList(user)));
            return user;
        }
    }

    public static int deleteUser(UserLogin user) throws SQLException {

        try (PreparedStatement pst = DataBase.getConnection().prepareStatement(
                " Update users set is_deleted = true WHERE user_login = ? AND Password = ? ")) {

            pst.setString(1, user.getLogin());
            pst.setString(2, user.getPassword());

            return pst.executeUpdate();
        }
    }

    public static int addFriend(UserLogin user, UserLogin friend) throws SQLException {

        try (PreparedStatement pst = DataBase.getConnection().prepareStatement(
                " INSERT INTO friends (user_login, friend_login, status) VALUES (?, ?, 'REQUESTED') ")) {

            pst.setString(1, user.getLogin());
            pst.setString(2, friend.getLogin());

            return pst.executeUpdate();
        }
    }

    public static int acceptFriend(UserLogin user, UserLogin friend) throws SQLException {

        String query = "UPDATE friends SET status = 'ACCEPTED' "
                + "WHERE user_login = ? AND friend_login = ? AND status = 'REQUESTED'";

        try (PreparedStatement pst = DataBase.getConnection().prepareStatement(query)) {
            pst.setString(1, friend.getLogin());
            pst.setString(2, user.getLogin());

            return pst.executeUpdate();
        }
    }

    public int removeFriend(UserLogin user, UserLogin friend) throws SQLException {

        String query = "UPDATE friends SET status = 'Removed' "
                + "WHERE (user_login = ? AND friend_login = ?) "
                + "OR (friend_login = ? AND user_login = ?)";

        try (PreparedStatement pst = DataBase.getConnection().prepareStatement(query)) {
            pst.setString(1, user.getLogin());
            pst.setString(2, friend.getLogin());
            pst.setString(3, user.getLogin());
            pst.setString(4, friend.getLogin());

            return pst.executeUpdate();
        }
    }

    public static ArrayList<UserLogin> getFriends(UserLogin userLogin) throws SQLException {

        String query = "SELECT u.User_login, u.user_name " + "FROM users u "
                + "JOIN friends f ON u.User_login = f.Friend_login OR u.User_login = f.User_login "
                + "WHERE (f.User_login = ? OR f.Friend_login = ?) AND f.Status = 'ACCEPTED'";

        try (PreparedStatement pst = DataBase.getConnection().prepareStatement(query)) {

            pst.setString(1, userLogin.getLogin());
            pst.setString(2, userLogin.getLogin());

            ArrayList<UserLogin> friends = new ArrayList<>();

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    friends.add(
                            new UserLogin(rs.getNString("User_name"), rs.getString("User_login")));
                }
            }
            return friends;
        }
    }

    public static int addWish(UserLogin userlogin, Item item) throws SQLException {

        try (PreparedStatement pst = DataBase.getConnection().prepareStatement(
                " INSERT INTO Wishing_list ( Item_id, User_login, Wish_Date, Status ) VALUES ( ?, ?, ?, 'ACTIVE' ) ")) {

            pst.setInt(1, item.getItem_id());
            pst.setString(2, userlogin.getLogin());
            pst.setDate(3, new Date(System.currentTimeMillis()));

            return pst.executeUpdate();
        }
    }

    public static int deleteWish(UserLogin userlogin, Wish wish) throws SQLException {

        try (PreparedStatement pst = DataBase.getConnection().prepareStatement(
                " UPDATE Wishing_list SET status = 'DELETED' WHERE user_login = ? AND Item_id = ? ")) {

            pst.setString(1, userlogin.getLogin());
            pst.setInt(2, wish.getItem().getItem_id());

            return pst.executeUpdate();
        }
    }

    public static ArrayList<Wish> getWishList(UserLogin userLogin) throws SQLException {

        String query = "select * From wishing_list where User_login = ? ";

        try (PreparedStatement pst = DataBase.getConnection().prepareStatement(query)) {

            pst.setString(1, userLogin.getLogin());

            Wish wish;
            ArrayList<Wish> wishList = new ArrayList<>();

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    wish = new Wish(ItemQuery.getItem(rs.getInt("Item_id")),
                            rs.getDate("Wish_Date"), rs.getString("status"));
                    wish.setConributUsers(getContributors(userLogin, wish));
                    wishList.add(wish);
                }
            }
            return wishList;
        }
    }

    public static int contribute(UserLogin friend, UserLogin contributor, Wish wish, double amount)
            throws SQLException {

        String query = "INSERT INTO Contributers ( user_login, item_id, wish_date, Contributee_login, Contribution_date, contribution_amount ) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pst = DataBase.getConnection().prepareStatement(query)) {
            pst.setString(1, friend.getLogin());
            pst.setInt(2, wish.getItem().getItem_id());
            pst.setDate(3, wish.getDate());
            pst.setString(4, contributor.getLogin());
            pst.setDate(5, new Date(System.currentTimeMillis()));
            pst.setDouble(6, amount);

            return pst.executeUpdate();
        }
    }

    public static ArrayList<Contributor> getContributors(UserLogin userLogin, Wish wish)
            throws SQLException {

        String query = "SELECT Contributee_login, SUM(contribution_amount) AS total_contribution "
                + "FROM Contributers WHERE item_id = ? AND Wish_Date = ? AND user_login = ? GROUP BY Contributee_login";

        try (PreparedStatement pst = DataBase.getConnection().prepareStatement(query)) {

            pst.setInt(1, wish.getItem().getItem_id());
            pst.setDate(2, wish.getDate());
            pst.setString(3, userLogin.getLogin());

            ArrayList<Contributor> contributors = new ArrayList<>();

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {

                    contributors.add(
                            new Contributor(new UserLogin("", rs.getString("Contributee_login")),
                                    rs.getDouble("total_contribution")));
                }
            }
            return contributors;
        }

    }

    public static UserLogin addBalance(UserLogin user, double balance) throws SQLException {

        try (PreparedStatement pst = DataBase.getConnection().prepareStatement(
                " update users set balance = balance + ? where user_login = ? ")) {

            pst.setDouble(1, balance);
            pst.setString(2, user.getLogin());

            pst.executeUpdate();
            return getUser(user);
        }
    }
}
