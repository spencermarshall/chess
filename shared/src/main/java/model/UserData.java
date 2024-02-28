package model;

public class UserData {
    private String username;
    private String password;
    private String email;

    public UserData()
    {

    }
    public String getUsername()
    {
        return this.username;
    }
    public String getEmail()
    {
        return this.email;
    }
    public String getPassword()
    {
        return this.password;
    }
    public void register(String username, String password, String email)
    {
        this.username = username;
        this.password = password;
        this.email = email;
    }

}
