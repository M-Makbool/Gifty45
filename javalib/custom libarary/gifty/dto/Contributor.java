package gifty.dto;

public class Contributor extends UserLogin {
    private final double amount;

    public Contributor(UserLogin userlogin, double amount) {
        super(userlogin);
        this.amount = amount;
    }

    public double getAmount() { return amount; }

}
