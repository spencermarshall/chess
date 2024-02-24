package model;

import chess.ChessGame;
import chess.ChessPiece;

public class GameData {
  private int gameID;
  private String whiteUsername;
  private String blackUsername;
  private String gameName;
  private ChessGame myGame;
  public GameData()
  {
    //default so we know if the user never sets one
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
    whiteUsername = "";
    blackUsername = "";
    gameName = "";
   // myGame = new ChessGame();
    return;
  }
}
