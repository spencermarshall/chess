package dataAccess;

import model.UserData;

public interface UserDAO {

    public void clearAllUsers();
    public void registerUser(UserData user) throws DataAccessException;

}
