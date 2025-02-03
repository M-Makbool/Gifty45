package gifty.dao;

import gifty.dto.Item;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemQuery {
    public static ArrayList<Item> getItems() throws SQLException {
        ResultSet rs;
        try (PreparedStatement pst = DataBase.getConnection().prepareStatement(" SELECT * FROM items WHERE Status = 'AVAILABLE' ")) {
            rs = pst.executeQuery();
        }
        ArrayList<Item> items = new ArrayList<>();
        while(rs.next()){
            items.add( new Item( 
            rs.getInt("Item_id"), 
            rs.getString("Name"), 
            rs.getString("Category"), 
            rs.getDouble("Price"), 
            rs.getString("Status"))
            );
        }
        return items;
    }

    public int addItem(Item item) throws SQLException {
        try (PreparedStatement pst = DataBase.getConnection().prepareStatement(
                " INSERT INTO items (Item_id, Name, Price, Category, Status) VALUES (?, ?, ?, ?, ?) ")) {
            pst.setInt(1, item.getItem_id());
            pst.setString(2, item.getItem_name());
            pst.setDouble(3, item.getPrice());
            pst.setString(4, item.getCategory());
            pst.setString(5, item.getStatus());
            int rows = pst.executeUpdate();
            pst.close();
            DataBase.getConnection().commit();
            return rows;
        }
    }

    public int deleteItem(Item item){
        int rows = 0;
        return rows;
    }

}
