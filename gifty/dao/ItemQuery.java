package gifty.dao;

import gifty.dto.Item;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemQuery {
    public static ResultSet getItems() throws SQLException {
        ResultSet rs;
        try (PreparedStatement pst = DataBase.getConnection().prepareStatement(" SELECT * FROM items ")) {
            rs = pst.executeQuery();
        }
        return rs;
    }

    public void addItem(Item item) throws SQLException {
        try (PreparedStatement pst = DataBase.getConnection().prepareStatement(
                " INSERT INTO items (Item_id, Name, Price, Category, Status) VALUES (?, ?, ?, ?, ?) ")) {
            pst.setInt(1, item.getItem_id());
            pst.setString(2, item.getItem_name());
            pst.setDouble(3, item.getPrice());
            pst.setString(4, item.getCategory());
            pst.setString(5, item.getStatus());
            pst.executeUpdate();
            pst.close();
            DataBase.getConnection().commit();
        }
    }
}
