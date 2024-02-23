package dataAccess;

import model.UserData;

public interface UserDAO {

    void clearAllUsers();
    void registerUser(UserData user) throws DataAccessException;
    void isValid(UserData user) throws DataAccessException;

}
