package gifty.dto;

import java.io.Serializable;

public class UserLogin implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String login;
    private String password;

    public UserLogin() {}

    public UserLogin(UserLogin userlogin) {
        this.name = userlogin.name;
        this.login = userlogin.login;
    }

    public UserLogin(String name, String login) {
        this.name = name.trim().replaceAll("\\s+", " ");
        this.login = login.trim().replaceAll("\\s+", " ");
    }

    public void setName(String name) { this.name = name.trim().replaceAll("\\s+", " "); }

    public void setLogin(String login) { this.login = login.trim().replaceAll("\\s+", " "); }

    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }

    public String getLogin() { return login; }

    public String getPassword() { return password; }
}
