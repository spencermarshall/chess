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
    public void isValid(UserData user) throws DataAccessException
    {
        if(!this.allUsers.contains(user))
        {
            throw new DataAccessException ("403");
        }

        if (user.getUsername() == null || user.getEmail() == null || user.getPassword() == null)
        {
            throw new DataAccessException("400");
        }
    }

    public void clearAllUsers()
    {
        allUsers.clear();
    }
    public void registerUser(UserData user)
    {
        this.allUsers.add(user);
    }
}
