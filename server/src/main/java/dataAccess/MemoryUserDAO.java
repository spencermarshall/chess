package dataAccess;
import model.UserData;
import org.eclipse.jetty.server.Authentication;

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
        for (int i = 0; i < allUsers.size(); ++i)
        {
            if (Objects.equals(allUsers.get(i).getUsername(), user.getUsername()))
            {
                throw new DataAccessException ("403");
            }
        }
        if (user.getUsername() == null || user.getEmail() == null || user.getPassword() == null)
        {
            throw new DataAccessException("400");
        }
    }
    public void validUsername(String username) throws DataAccessException {
        for (int i = 0; i < this.allUsers.size(); ++i)
        {
            if (Objects.equals(allUsers.get(i).getUsername(), username))
            {
                throw new DataAccessException("403");
            }
        }
    }
    public boolean isEmpty()
    {
        return allUsers.isEmpty();
    }
    public int getSize()
    {
        return allUsers.size();
    }
    public void logout(UserData user)
    {
        this.allUsers.remove(user);
    }
    public boolean testLogin(UserData user) throws DataAccessException
    {
        boolean valid = false;
        for (int i = 0; i < allUsers.size(); ++i)
        {
            if (Objects.equals(allUsers.get(i).getUsername(), user.getUsername()))
            {
                if (Objects.equals(allUsers.get(i).getPassword(), user.getPassword()))
                {
                    //username and password is correct
                    return true;
                }
                else
                {
                    //username is correct but password is incorrect
                    //unauthorized 500
                    throw new DataAccessException("401");
                }
            }
        }
        return valid;
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
