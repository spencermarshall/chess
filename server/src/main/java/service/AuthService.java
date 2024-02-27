package service;

import dataAccess.*;
import model.AuthData;
import model.UserData;

import javax.xml.crypto.Data;

public class AuthService {
    //private DataAccess dataAcessAuth;
    private AuthDAO dataAccessAuth;

    public AuthService()
    {
        this.dataAccessAuth = new MemoryAuthDAO();
    }

    public boolean verifyAuth(String auth) throws DataAccessException
    {
        return dataAccessAuth.hasUsername(auth);
    }
    public String getUsername(String auth)
    {
        return this.dataAccessAuth.getUsername(auth);
    }
    public void removeAuth(String auth)
    {
        dataAccessAuth.removeAuth(auth);
    }
    public void addAuth(AuthData auth)
    {
        dataAccessAuth.addAuth(auth);
    }
    public boolean isEmpty()
    {
        return dataAccessAuth.isEmpty();
    }

    public void clearAllAuth()
    {
        this.dataAccessAuth = new MemoryAuthDAO();
    }



}
