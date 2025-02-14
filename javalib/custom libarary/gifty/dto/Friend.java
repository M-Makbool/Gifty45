package gifty.dto;

import java.sql.Date;
import java.util.ArrayList;

public class Friend extends UserLogin {

    private final String email;
    private final String gender;
    private final String telephone;
    private final Date dateOfBirth;
    private final String status;
    private ArrayList<Wish> wishList;

    public Friend(UserLogin userlogin, String email, String gender, String telephone,
            Date dateOfBirth, String status) {
        super(userlogin);
        this.email = email.trim().replaceAll("\\s+", " ");
        this.gender = gender;
        this.telephone = telephone.trim().replaceAll("\\s+", " ");
        this.dateOfBirth = dateOfBirth;
        this.status = status;
    }

    public String getEmail() { return email; }

    public String getGender() { return gender; }

    public String getTelephone() { return telephone; }

    public Date getDateOfBirth() { return dateOfBirth; }
    
    public String getStatus() { return status; }

    public void setWishList(ArrayList<Wish> wishList) { this.wishList = new ArrayList<>(wishList); }

    public ArrayList<Wish> getWishList() { return wishList; }

}
