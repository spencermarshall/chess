package service;

import dataAccess.DataAccess;
import dataAccess.GameDAO;
import model.AuthData;
import model.UserData;
import dataAccess.MemoryGameDAO;

public class GameService {
   // private DataAccess dataAccessGame;
    private GameDAO dataAccessGame;
    public GameService()
    {
        this.dataAccessGame = new MemoryGameDAO();
    }

    public void clearAllGame()
    {
        this.dataAccessGame = new MemoryGameDAO();
    }



}
