package service;

import dataAccess.DataAccess;
import dataAccess.GameDAO;
import model.AuthData;
import model.GameData;
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
    public boolean isEmpty()
    {
        return dataAccessGame.isEmpty();
    }
    public void addGame(GameData game)
    {
        dataAccessGame.addGame(game);
    }




}
