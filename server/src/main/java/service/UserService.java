package service;

import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import dataAccess.DataAccess;
import model.AuthData;
import model.UserData;
import dataAccess.MemoryUserDAO;
import org.eclipse.jetty.server.Authentication;


public class UserService {
   // private DataAccess dataAcessUser;
   // private UserDAO dataAccessUser;
    private UserDAO dataAccessUser;
    public UserService()
    {
        this.dataAccessUser = new MemoryUserDAO();
    }

    public AuthData register(UserData user) throws DataAccessException{
      if (user == null)
      {
          throw new DataAccessException("user is null bruh");
      }
      this.dataAccessUser.registerUser(user);

      //create Auth token with the users username and returns AuthData
      return new AuthData(user.getUsername());


  }

    public AuthData login(UserData user)
    {
        return null;
    }


    public void clearAllUsers()
    {
        this.dataAccessUser.clearAllUsers();
    }
    public void logout(UserData user)
    {
       return;
    }


}
