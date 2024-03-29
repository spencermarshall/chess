package service;

import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.MySQLGameDAO;
import model.AuthData;
import model.GameData;
import dataAccess.MemoryGameDAO;

import java.util.Collection;
import java.util.Vector;

public class GameService {

    private GameDAO dataAccessGame;
    public GameData[] listOfGames;

    public GameService() throws DataAccessException {
        //this.dataAccessGame = new MemoryGameDAO();
        this.dataAccessGame = new MySQLGameDAO();
        this.listOfGames = new GameData[64];
    }

    public void clearAllGame() {
        this.dataAccessGame.clearAllGames();
        this.listOfGames = new GameData[64];
    }
    public boolean isEmpty() throws DataAccessException {
        return dataAccessGame.isEmpty();
    }
    public void addGame(GameData game, AuthData auth) throws DataAccessException {
        if (auth == null) {
            return;
        }
        dataAccessGame.addGame(game);
    }
    public Collection<GameData> returnAllGames(AuthData auth) throws DataAccessException {
        if (auth != null) {
            return this.dataAccessGame.returnAllGames();
        }
        return new Vector<GameData>();
    }
    public GameData getGame(int id) throws DataAccessException {
        return this.dataAccessGame.getGame(id);
    }
    public void setColor(int id, String color, String username) throws DataAccessException {
        this.dataAccessGame.setColor(id, color, username);
    }




}
