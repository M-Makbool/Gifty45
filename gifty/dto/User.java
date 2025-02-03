package gifty.dto;

import java.sql.Date;
import java.util.ArrayList;

public class User extends UserLogin {

    private String email;
    private String gender;
    private boolean isDeleted;
    private String telephone;
    private Date dateOfBirth;
    private ArrayList<UserLogin> friendList;
    private ArrayList<Wish> wishList;

    public User(UserLogin userlogin, String email, String gender, String telephone, Date dateOfBirth) {
        super(userlogin);
        this.email = email;
        this.gender = gender;
        this.telephone = telephone;
        this.dateOfBirth = dateOfBirth;
    }

 

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public String getTelephone() {
        return telephone;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public ArrayList<UserLogin> getfriendList() {
        return friendList;
    }

    public ArrayList<Wish> getWishList() {
        return wishList;
    }

}
