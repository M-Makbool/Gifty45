package gifty.dto;

import java.io.Serializable;
import java.util.ArrayList;


public class Item implements Serializable {
    private static final long serialVersionUID = 1L; // Version control
    private int item_id;
    private String item_name;
    private String category;
    private double price;
    private String status;

    public Item (int item_id, String item_name, String category, double price, String status) {
        this.item_id = item_id;
        this.item_name = item_name.trim().replaceAll("\\s+", " ");
        this.category = category.trim().replaceAll("\\s+", " ");
        this.price = price;
        this.status = status;
    }

    public int getItem_id() { return item_id; }

    public void setItem_id(int item_id) { this.item_id = item_id; }

    public String getItem_name() { return item_name; }

    public void setItem_name(String item_name) { this.item_name = item_name; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public double getPrice() { return price; }

    public void setPrice(double price) { this.price = price; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public static Item selectItemById(int itemId, ArrayList<Item> items) {
        for (Item item : items)
            if (item.getItem_id() == itemId)
                return item;
        return null;
    }

}
