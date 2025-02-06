package gifty.dao;

import gifty.dto.Item;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemQuery {

    public static ArrayList<Item> getItems() throws SQLException {
        ArrayList<Item> items;
        try (PreparedStatement pst = DataBase.getConnection()
                .prepareStatement(" SELECT * FROM items WHERE Status = 'AVAILABLE' ");
                ResultSet rs = pst.executeQuery()) {
            items = new ArrayList<>();
            while (rs.next()) {
                items.add(new Item(rs.getInt("Item_id"), rs.getString("Name"),
                        rs.getString("Category"), rs.getDouble("Price"), rs.getString("Status")));
            }
        }
        return items;
    }

    public static int addItem(Item item) throws SQLException {
        try (PreparedStatement pst = DataBase.getConnection().prepareStatement(
                " INSERT INTO items ( Name, Price, Category, Status) VALUES (?, ?, ?, 'AVAILABLE') ")) {
            pst.setString(1, item.getItem_name());
            pst.setDouble(2, item.getPrice());
            pst.setString(3, item.getCategory());
            if (pst.executeUpdate() == 1) {
                try (PreparedStatement pstr = DataBase.getConnection().prepareStatement(
                        " SELECT ITEM_ID FROM items WHERE Name = ? AND Price = ? AND Category = ? AND Status = 'AVAILABLE' ")) {
                    pstr.setString(1, item.getItem_name());
                    pstr.setDouble(2, item.getPrice());
                    pstr.setString(3, item.getCategory());
                    try (ResultSet rs = pstr.executeQuery()) {
                        if (rs.next())
                            item.setItem_id(rs.getInt("Item_id"));
                    }
                }
            }
        }
        return item.getItem_id();
    }

    public static int deleteItem(Item item) throws SQLException {
        try (PreparedStatement pst = DataBase.getConnection()
                .prepareStatement("Update items set Status ='NOTAVAILABLE' where item_id = ?")) {
            pst.setInt(1, item.getItem_id());
            int rows = pst.executeUpdate();
            return rows;
        }
    }

    public static Item getItem(int Itemid) throws SQLException {
        Item item = null;
        try (PreparedStatement pst = DataBase.getConnection()
                .prepareStatement("Select * from items where item_id = ? ")) {
            pst.setInt(1, Itemid);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    item = new Item(rs.getInt("item_id"), rs.getString("Name"),
                            rs.getString("Category"), rs.getDouble("Price"),
                            rs.getString("Status"));
                }
            }
        }
        return item;
    }
}
