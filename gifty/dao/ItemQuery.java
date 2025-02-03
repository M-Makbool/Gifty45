package gifty.dao;

import gifty.dto.Item;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemQuery {
    public static ArrayList<Item> getItems() throws SQLException {
        ResultSet rs;
        try (PreparedStatement pst = DataBase.getConnection()
                .prepareStatement(" SELECT * FROM items WHERE Status = 'AVAILABLE' ")) {
            rs = pst.executeQuery();
        }
        ArrayList<Item> items = new ArrayList<>();
        while (rs.next()) {
            items.add(new Item(
                    rs.getInt("Item_id"),
                    rs.getString("Name"),
                    rs.getString("Category"),
                    rs.getDouble("Price"),
                    rs.getString("Status")));
        }
        return items;
    }

    public int addItem(Item item) throws SQLException {
        try (PreparedStatement pst = DataBase.getConnection().prepareStatement(
                " INSERT INTO items ( Name, Price, Category, Status) VALUES (?, ?, ?, 'AVAILABLE') ")) {       
            pst.setString(1, item.getItem_name());
            pst.setDouble(2, item.getPrice());
            pst.setString(3, item.getCategory());
            int rows = pst.executeUpdate();
            pst.close();
            DataBase.getConnection().commit();
            return rows;
        }
    }

    public int deleteItem(Item item) throws SQLException {
        try (PreparedStatement pst = DataBase.getConnection().prepareStatement(
                "Update items set Status ='NOTAVAILABLE' where item_id = ?")) {
            pst.setInt(1, item.getItem_id());
            int rows = pst.executeUpdate();
            pst.close();
            DataBase.getConnection().commit();
            return rows;
        }
    }

    public Item getItem(int Itemid) throws SQLException {
        ResultSet rs;
        Item item = null ;
        try (PreparedStatement pst = DataBase.getConnection()
                .prepareStatement("Select * from items where item_id = ? ")) {
            pst.setInt(1, Itemid);
            rs = pst.executeQuery();
            pst.close();
            if (rs.next()) {
                item = new Item(
                        rs.getInt("item_id"),
                        rs.getString("Name"),
                        rs.getString("Category"),
                        rs.getDouble("Price"),
                        rs.getString("Status"));
            }
        }
        return item;
    }
}
