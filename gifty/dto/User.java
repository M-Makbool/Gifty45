package gifty.dto;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private String login;
    private String email;
    private String gender;
    private boolean isDeleted;
    private String telephone;
    private Date dateOfBirth;
    private ArrayList<User> frindeList;
    private ArrayList<Wish> wishList;

    public User() {
    }

    public User(ResultSet rs) throws SQLException {
        id = rs.getInt("");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
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

    public ArrayList<User> getFrindeList() {
        return frindeList;
    }

    public ArrayList<Wish> getWishList() {
        return wishList;
    }

}
