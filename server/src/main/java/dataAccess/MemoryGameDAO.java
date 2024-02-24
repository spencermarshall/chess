package dataAccess;
import model.GameData;
import java.util.*;
import java.util.Vector;
public class MemoryGameDAO implements GameDAO
{

  private Vector<GameData> allGames;
  public MemoryGameDAO()
  {
      this.allGames = new Vector<GameData>();
  }
  public void clearAllGames()
  {
      this.allGames.clear();
  }
  public boolean isEmpty()
  {
      return allGames.isEmpty();
  }
  public void addGame(GameData game)
  {
      allGames.add(game);
  }
  public Vector<GameData> returnAllGames()
  {
      return this.allGames;
  }


}
