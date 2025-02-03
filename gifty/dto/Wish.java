package gifty.dto;

import java.sql.Date;
import java.util.ArrayList;

public class Wish {
    private final Item item;
    private final Date date;
    private final String status;
    private final ArrayList<Conributer> conributUsers;

    public Wish(Item item, Date date, String status, ArrayList<Conributer> conributUsers) {
        this.item = item;
        this.date = date;
        this.status = status;
        this.conributUsers = conributUsers;
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