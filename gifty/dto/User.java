package gifty.dto;

import java.sql.Date;
import java.util.ArrayList;

public class User extends UserLogin {

    private String email;
    private String gender;
    private boolean isDeleted;
    private String telephone;
    private Date dateOfBirth;
    private ArrayList<UserLogin> frindeList;
    private ArrayList<Wish> wishList;

    public User() {
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

    public ArrayList<UserLogin> getFrindeList() {
        return frindeList;
    }

    public ArrayList<Wish> getWishList() {
        return wishList;
    }

}
