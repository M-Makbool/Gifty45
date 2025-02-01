package gifty.types;

import java.sql.Date;
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

}
