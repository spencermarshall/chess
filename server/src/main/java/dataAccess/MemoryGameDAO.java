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
      this.allGames = new Vector<GameData>();
      //this.allGames.clear();
  }
  public boolean isEmpty()
  {
      return allGames.isEmpty();
  }
  public void addGame(GameData game)
  {
      allGames.add(game);
      //refactor gameID everytime one is added or removed
      for (int i = 0; i < this.allGames.size(); ++i)
      {
          this.allGames.get(i).setGameID(i+1);
      }
  }
  public Vector<GameData> returnAllGames()
  {
      if (this.allGames == null)
      {
          return new Vector<GameData>();
      }
      return this.allGames;
  }
  public void setColor(int id, String color, String username)
  {
      for (int i = 0; i < this.allGames.size(); ++i)
      {
          if (this.allGames.get(i).getGameID() == id)
          {
              if (Objects.equals(color, "BLACK") && this.allGames.get(i).getBlackUsername() != null)
              {
                  return;
              }
              else if (Objects.equals(color, "WHITE") && this.allGames.get(i).getWhiteUsername() != null)
              {
                  return;
              }
              this.allGames.get(i).addUser(color, username);
              return;
          }
      }
  }
  public GameData getGame(int id) throws DataAccessException
  {
      for (int i = 0; i < allGames.size(); ++i)
      {
          if (allGames.get(i).getGameID() == id)
          {
              return allGames.get(i);
          }
      }
      //this means gameID doesn't exist so, it's a bad request
      throw new DataAccessException("400");
  }


}
