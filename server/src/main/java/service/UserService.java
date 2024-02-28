package service;

import dataAccess.*;
import model.AuthData;
import model.UserData;
public class UserService {

    private UserDAO dataAccessUser;
    public UserService()
    {
        this.dataAccessUser = new MemoryUserDAO();
    }
    public boolean isEmpty()
    {
        return this.dataAccessUser.isEmpty();
    }
    public int getSize()
    {
        return dataAccessUser.getSize();
    }
    public void isValid(UserData user) throws DataAccessException {
        try {
            this.dataAccessUser.isValid(user);
        } catch (DataAccessException message) {
            throw new DataAccessException(message.getMessage());
        }
    }
    public AuthData validLogin(UserData user) throws DataAccessException {
        AuthData ret = new AuthData(user.getUsername());
        boolean exists = false;
        try {
            exists = this.dataAccessUser.testLogin(user);
            if (!exists) {
                throw new DataAccessException("401");
            }
        } catch (DataAccessException message) {
            throw new DataAccessException(message.getMessage());
        }
        return ret;
    }

    public AuthData register(UserData user) throws DataAccessException{
        //check if username is already used
        try {
            this.dataAccessUser.validUsername(user.getUsername());
        } catch (DataAccessException message) {
            throw new DataAccessException(message.getMessage());
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
       this.dataAccessUser.logout(user);
    }
}
