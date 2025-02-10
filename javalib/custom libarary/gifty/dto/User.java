package gifty.dto;

import java.sql.Date;
import java.util.ArrayList;

public class User extends UserLogin {

    private final String email;
    private final String gender;
    private final String telephone;
    private final Date dateOfBirth;
    private final double balance ;
    private ArrayList<UserLogin> friendList;
    private ArrayList<Wish> wishList;

    public User(UserLogin userlogin, String email, String gender, String telephone,
            Date dateOfBirth, double balance) {
        super(userlogin);
        this.email = email.trim().replaceAll("\\s+", " ");
        this.gender = gender;
        this.telephone = telephone.trim().replaceAll("\\s+", " ");
        this.dateOfBirth = dateOfBirth;
        this.balance = balance;
    }

    public String getEmail() { return email; }

    public String getGender() { return gender; }

    public String getTelephone() { return telephone; }

    public Date getDateOfBirth() { return dateOfBirth; }
    
    public double getBalance() { return balance; }

    public void setFriendList(ArrayList<UserLogin> friendList) {
        this.friendList = new ArrayList<>(friendList);
    }

    public void setWishList(ArrayList<Wish> wishList) { this.wishList = new ArrayList<>(wishList); }

    public ArrayList<UserLogin> getfriendList() { return friendList; }

    public ArrayList<Wish> getWishList() { return wishList; }

}
