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
                .prepareStatement(" SELECT * FROM items WHERE Status = 'AVAILABLE' ")) {
            rs = pst.executeQuery();
        }
        ArrayList<UserLogin> users = new ArrayList<>();
        while (rs.next()) {
            users.add(new UserLogin());
        }
        return users;
    }



}
