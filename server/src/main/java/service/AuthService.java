package service;

import dataAccess.*;
import model.AuthData;

public class AuthService {

    private AuthDAO dataAccessAuth;
    public AuthService() {
       // this.dataAccessAuth = new MemoryAuthDAO();
        this.dataAccessAuth = new MySQLAuthDAO();
    }

    public boolean verifyAuth(String auth) throws DataAccessException {
        return dataAccessAuth.hasUsername(auth);
    }
    public String getUsername(String auth) {
        return this.dataAccessAuth.getUsername(auth);
    }
    public void removeAuth(String auth) throws DataAccessException {
        dataAccessAuth.removeAuth(auth);
    }
    public void addAuth(AuthData auth) throws DataAccessException {
        dataAccessAuth.addAuth(auth);
    }
    public boolean isEmpty() {
        return dataAccessAuth.isEmpty();
    }

    public void clearAllAuth()
    {
        this.dataAccessAuth = new MemoryAuthDAO();
    }
}
