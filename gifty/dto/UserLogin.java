package gifty.dto;

public class UserLogin {
    private String name;
    private String login;

    public UserLogin(UserLogin userlogin) {
        this.name = userlogin.name;
        this.login = userlogin.login;
    }

    public UserLogin(String name, String login) {
        this.name = name.trim().replaceAll("\\s+", " ");
        this.login = login.trim().replaceAll("\\s+", " ");
    }

    public String getName() { return name; }

    public String getLogin() { return login; }
}
