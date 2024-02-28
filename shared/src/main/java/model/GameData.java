package model;

import chess.ChessGame;
import chess.ChessPiece;

import java.util.Objects;

public class GameData {
  private int gameID;
  private String whiteUsername;
  private String blackUsername;
  private String gameName;
  private ChessGame myGame;
  public GameData()
  {
    //default so we know if the user never sets one
    whiteUsername = null;
    blackUsername = null;
    gameID = 1;
  }
  public String getWhiteUsername()
  {
    return this.whiteUsername;
  }
  public String getBlackUsername()
  {
    return this.blackUsername;
  }
  public void addUser(String color, String username)
  {
    if (Objects.equals(color, "WHITE"))
    {
      this.whiteUsername = username;
    }
    else if (Objects.equals(color, "BLACK"))
    {
      this.blackUsername = username;
    }
  }

  public void setGameID(int id)
  {
    this.gameID = id;
  }
  public int getGameID()
  {
    return this.gameID;
  }
  public String getGameName()
  {
    return this.gameName;
  }

  public void clearAll()
  {
    gameID = -1;
    whiteUsername = null;
    blackUsername = null;
    gameName = "";
    //myGame = new ChessGame();

    return;
  }
}
