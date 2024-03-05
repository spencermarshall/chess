package service;

import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.MySQLGameDAO;
import model.AuthData;
import model.GameData;
import dataAccess.MemoryGameDAO;
import java.util.Vector;

public class GameService {

    private GameDAO dataAccessGame;
    public GameData[] listOfGames;

    public GameService() {
        //this.dataAccessGame = new MemoryGameDAO();
        this.dataAccessGame = new MySQLGameDAO();
        this.listOfGames = new GameData[64];
    }

    public void clearAllGame() {
        this.dataAccessGame = new MemoryGameDAO();
        this.listOfGames = new GameData[64];
    }
    public boolean isEmpty()
    {
        return dataAccessGame.isEmpty();
    }
    public void addGame(GameData game, AuthData auth) {
        if (auth == null) {
            return;
        }
        dataAccessGame.addGame(game);
    }
    public Vector<GameData> returnAllGames(AuthData auth) {
        if (auth != null) {
            return this.dataAccessGame.returnAllGames();
        }
        return new Vector<GameData>();
    }
    public GameData getGame(int id) throws DataAccessException {
        return this.dataAccessGame.getGame(id);
    }
    public void setColor(int id, String color, String username)
    {
        this.dataAccessGame.setColor(id, color, username);
    }




}
