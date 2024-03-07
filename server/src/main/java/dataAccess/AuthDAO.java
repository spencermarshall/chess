package dataAccess;

import model.AuthData;

public interface AuthDAO {

    void clearAllAuth() throws DataAccessException;
    void addAuth(AuthData authToken) throws DataAccessException;
    boolean hasUsername(String auth) throws DataAccessException;
    boolean isEmpty();
    void removeAuth(String auth) throws DataAccessException;
    String getUsername(String auth) throws DataAccessException;


}
