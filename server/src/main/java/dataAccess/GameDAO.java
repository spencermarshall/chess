package dataAccess;

import model.GameData;
import org.eclipse.jetty.server.Authentication;

public interface GameDAO
{
  void clearAllGames();
  boolean isEmpty();
  void addGame(GameData game);

}
