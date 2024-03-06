package dataAccess;

import model.GameData;
import org.eclipse.jetty.server.Authentication;

import java.util.Collection;
import java.util.Vector;

public interface GameDAO
{
  boolean isEmpty() throws DataAccessException;
  void addGame(GameData game) throws DataAccessException;
  Collection<GameData> returnAllGames() throws DataAccessException;
  GameData getGame(int id) throws DataAccessException;
  void setColor(int id, String color, String username) throws DataAccessException;


  void clearAllGames();
}
