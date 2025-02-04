package gifty.dao;

import gifty.dto.Contributor;
import gifty.dto.Item;
import gifty.dto.User;
import gifty.dto.UserLogin;
import gifty.dto.Wish;
import java.sql.Connection;
import java.sql.Date;
import oracle.sql.DATE;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserQuery {
    public static ArrayList<UserLogin> getUsers() throws SQLException {
        ResultSet rs;
        try (PreparedStatement pst = DataBase.getConnection()
                .prepareStatement(" SELECT User_login, user_name FROM users WHERE is_deleted = false; ")) {
            rs = pst.executeQuery();
        }
        ArrayList<UserLogin> users = new ArrayList<>();
        while (rs.next()) {
            users.add(new UserLogin(
                    rs.getString("user_name"),
                    rs.getString("User_login")));
        }
        return users;
    }

    public int addUser(User user) throws SQLException {
        try (PreparedStatement pst = DataBase.getConnection().prepareStatement(
                " INSERT INTO users (user_login, user_email, user_name, telephone, is_deleted, gender, password, dob) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)")) {

            pst.setString(1, user.getLogin());
            pst.setString(2, user.getEmail());
            pst.setString(3, user.getName());
            pst.setString(4, user.getTelephone());
            pst.setBoolean(5, false);
            pst.setString(6, user.getGender());
            // pst.setInt(7, user.); // we need to handle password
            pst.setDate(8, user.getDateOfBirth());
            int rows = pst.executeUpdate();
            pst.close();
            DataBase.getConnection().commit();

            return rows;
        }
    }

    public int deleteUser(UserLogin user) throws SQLException {
        try (PreparedStatement pst = DataBase.getConnection().prepareStatement(
                " Update users set is_deleted = true WHERE user_login = ? ")) {
            pst.setString(1, user.getLogin());
            int rows = pst.executeUpdate();
            pst.close();
            DataBase.getConnection().commit();

            return rows;
        }
    }

    public int addFriend(UserLogin user, UserLogin friend) throws SQLException {
        try (PreparedStatement pst = DataBase.getConnection().prepareStatement(
                " INSERT INTO friends (user_login, friend_login, status) VALUES (?, ?, 'REQUESTED') ")) {
            pst.setString(1, user.getLogin());
            pst.setString(2, friend.getLogin());

            int rows = pst.executeUpdate();
            pst.close();
            DataBase.getConnection().commit();

            return rows;
        }
    }

    public int removeFriend(UserLogin user, UserLogin friend) throws SQLException {
        try (PreparedStatement pst = DataBase.getConnection().prepareStatement(
                " update friends set status = 'Removed' WHERE (user_login = ? AND friend_login = ?) OR (friend_login = ? and user_login = ?) ")) {
            pst.setString(1, user.getLogin());
            pst.setString(2, friend.getLogin());
            pst.setString(3, user.getLogin());
            pst.setString(4, friend.getLogin());
            int rows = pst.executeUpdate();
            pst.close();
            DataBase.getConnection().commit();

            return rows;
        }
    }

    public static User getUser(UserLogin userlogin) throws SQLException {
        ResultSet rs;
        PreparedStatement pst = DataBase.getConnection().prepareStatement(
                " select * from users where user_login = ? and status = 'AVAILABLE' ");
        pst.setString(1, userlogin.getLogin());
        rs = pst.executeQuery();
        rs.next();
        return new User(
                userlogin,
                rs.getString("user_email"),
                rs.getString("Gender"),
                rs.getString("telephone"),
                rs.getDate("DOB")

        );
    }

    public static int deleteWish(UserLogin userlogin, Wish wish) throws SQLException {
        PreparedStatement pst = DataBase.getConnection().prepareStatement(
                " UPDATE Wishing_list SET status = 'DELETED' WHERE user_login = ? AND Item_id = ? ");
        pst.setString(1, userlogin.getLogin());
        pst.setInt(2, wish.getItem().getItem_id());
        return pst.executeUpdate();
    }

    public static int addWish(UserLogin userlogin, Item item) throws SQLException {
        PreparedStatement pst = DataBase.getConnection().prepareStatement(
                " INSERT INTO Wishing_list (Item_id,User_login,Wish_Date ,Status) VALUES (?, ? ,? , 'ACTIVE') ");
        pst.setInt(1, item.getItem_id());
        pst.setString(2, userlogin.getLogin());
        pst.setDate(3, DATE.getCurrentDate().dateValue());
        return pst.executeUpdate();

    }

    public static ArrayList<UserLogin> getFriends(UserLogin userLogin) throws SQLException {
        ArrayList<UserLogin> friends = new ArrayList<>();
        String query = "select u.User_login , u.user_name from users u join Friends f on u.User_login = f.Friend_login or u.User_login = f.User_login where f.User_login = ? or f.Friend_login = ? and f.Status = 'ACCEPTED'";
        try (Connection conn = DataBase.getConnection();
                PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, userLogin.getLogin());
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    friends.add(new UserLogin(rs.getNString("User_name"), rs.getString("User_login")));
                }
            }
        }
        return friends;
    }

    public static ArrayList<Wish> getWishList(UserLogin userLogin) throws SQLException {
        ArrayList<Wish> wishList = new ArrayList<>();
        String query = "select * From wishing_list where User_login = ? ";
         
    
        try (Connection conn = DataBase.getConnection();
                PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, userLogin.getLogin());
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                       
                    wishList.add(new Wish(
                        rs.getInt("Item_id"),
                        rs.getDate("Date"),
                        rs.getString("status"),
                         ));
                }
            }
        }
        return wishList;
    }

    public static ArrayList<Contributor> getContributors(Wish wish) throws SQLException {
        ArrayList<Contributor> contributors = new ArrayList<>();

        String query = "SELECT user_login, SUM(contribution_amount) AS total_contribution FROM contributions WHERE item_id = ? AND wishing_date = ?  GROUP BY user_login";

        try (Connection conn = DataBase.getConnection();
                PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setInt(1, wish.getItem().getItem_id());
            pst.setDate(2, wish.getDate());

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {

                    contributors.add(new Contributor(
                            rs.getString("user_login"),
                            rs.getDouble("total_contribution")));
                }
            }
        }

        return contributors;
    }

}
