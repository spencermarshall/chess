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

    public void clearAllAuth()
    {
        this.dataAccessAuth = new MemoryAuthDAO();
    }



}
