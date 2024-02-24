package dataAccess;

import model.AuthData;

public interface AuthDAO {

    void clearAllAuth();

    void addAuth(AuthData authToken);
    boolean hasUsername(String auth) throws DataAccessException;
    boolean isEmpty();
    void removeAuth(String auth);

}
