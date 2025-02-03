package gifty.dto;

public class UserLogin {
    private int id;
    private String name;
    private String login;

    public UserLogin(int id, String name, String login ){
        this.id=id;
        this.name=name;
        this.login=login;
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
}
