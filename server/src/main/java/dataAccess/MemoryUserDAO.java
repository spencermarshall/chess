package dataAccess;
import model.UserData;
import java.util.*;

public class MemoryUserDAO implements UserDAO {
    //private UserData[] allUsers =new UserData[0];
    private Vector<UserData> allUsers;
    public MemoryUserDAO()
    {
        this.allUsers = new Vector<UserData>();
    }

    public void clearAllUsers()
    {
        allUsers.clear();
    }
    public void registerUser(UserData user)
    {
       // user.register(user["username"],);
        this.allUsers.add(user);
    }
}
