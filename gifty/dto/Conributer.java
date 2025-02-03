package gifty.dto;

import java.sql.Date;

public class Conributer extends UserLogin {
    private final int amount;
    private final Date date;

    public Conributer(UserLogin userlogin, int amount, Date date) {
        super(userlogin);
        this.amount = amount;
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }
}
