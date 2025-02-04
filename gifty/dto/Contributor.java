package gifty.dto;

public class Contributor extends UserLogin {
    private final int amount;

    public Contributor(String userlogin, double amount) {
        super(userlogin);
        this.amount = amount;

    }

    public int getAmount() {
        return amount;
    }

}
