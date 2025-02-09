package gifty.dto;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

public class Wish implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Item item;
    private final Date date;
    private final String status;
    private ArrayList<Contributor> conributUsers;

    public Wish(Item item, Date date, String status) {
        this.item = item;
        this.date = date;
        this.status = status;
    }

    public void setConributUsers(ArrayList<Contributor> conributUsers) {
        this.conributUsers = conributUsers;
    }

    public Item getItem() { return item; }

    public Date getDate() { return date; }

    public String getStatus() { return status; }

    public ArrayList<Contributor> getConributUsers() { return conributUsers; }
}