package gifty.dao;

import gifty.dto.User;
import gifty.dto.UserLogin;
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
}
