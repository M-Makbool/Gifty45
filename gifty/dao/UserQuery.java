package gifty.dao;

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
        while (rs.next()){
            users.add(new UserLogin(
                rs.getString("user_name"),
                rs.getString("User_login")
                ));
        }
        return users;
    }



}
