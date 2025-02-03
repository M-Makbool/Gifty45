package gifty.dto;

import java.sql.Date;
import java.util.ArrayList;

public class Wish {
    private Item item;
    private Date date;
    private String status;
    private ArrayList<Conributer> conributUsers;

    public Wish(Item item, Date date, String status) {
        this.item = item;
        this.date = date;
        this.status = status;
    }

    public Item getItem() {
        return item;
    }

    public Date getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<Conributer> getConributUsers() {
        return conributUsers;
    }
}