package dataAccess;

import model.AuthData;

public interface AuthDAO {

    void clearAllAuth();

    void addAuth(AuthData authToken);

}
