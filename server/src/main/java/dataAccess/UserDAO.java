package dataAccess;

import model.UserData;

public interface UserDAO {

    void clearAllUsers();

    void registerUser(UserData user) throws DataAccessException;

    void isValid(UserData user) throws DataAccessException;

    boolean testLogin(UserData user) throws DataAccessException;

    boolean isEmpty();

    int getSize();

    void validUsername(String username) throws DataAccessException;
    void logout(UserData user);
}
