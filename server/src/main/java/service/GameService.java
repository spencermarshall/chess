package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import model.AuthData;
import model.GameData;
import model.UserData;
import dataAccess.MemoryGameDAO;
import java.util.Vector;

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
    public void addGame(GameData game, AuthData auth)
    {
        if (auth == null)
        {
            return;
        }
        dataAccessGame.addGame(game);
    }
    public Vector<GameData> returnAllGames()
    {
        return this.dataAccessGame.returnAllGames();
    }
    public GameData getGame(int id) throws DataAccessException
    {
        return this.dataAccessGame.getGame(id);
    }
    public void setColor(int id, String color, String username)
    {
        this.dataAccessGame.setColor(id, color, username);
    }




}
