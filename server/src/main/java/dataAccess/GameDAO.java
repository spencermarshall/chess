package dataAccess;

import model.GameData;
import org.eclipse.jetty.server.Authentication;
import java.util.Vector;

public interface GameDAO
{
  boolean isEmpty();
  void addGame(GameData game);
  Vector<GameData> returnAllGames();
  GameData getGame(int id) throws DataAccessException;
  void setColor(int id, String color, String username);


  void clearAllGames();
}
