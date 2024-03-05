package dataAccess;

import model.GameData;

import java.util.Vector;

public class MySQLGameDAO implements GameDAO{
    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void addGame(GameData game) {

    }

    @Override
    public Vector<GameData> returnAllGames() {
        return null;
    }

    @Override
    public GameData getGame(int id) throws DataAccessException {
        return null;
    }

    @Override
    public void setColor(int id, String color, String username) {

    }
}
