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
    public GameData[] listOfGames;

    public GameService()
    {
        this.dataAccessGame = new MemoryGameDAO();
        this.listOfGames = new GameData[64];
    }
    public void updateListOfGames(GameData[] updated)
    {
        this.listOfGames = updated;
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
    public Vector<GameData> returnAllGames(AuthData auth)
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
