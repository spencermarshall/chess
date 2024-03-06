package model;


import chess.ChessGame;

import java.util.Objects;

public class GameData {
  private int gameID;
  private String whiteUsername;
  private String blackUsername;
  private ChessGame myGame;
  public GameData()
  {
    //default so we know if the user never sets one
    whiteUsername = "";
    blackUsername = "";
    gameID = 1;
    myGame = new ChessGame();
  }
  public void setMyGame(ChessGame game) {
    this.myGame = game;
  }

  public String getWhiteUsername()
  {
    return this.whiteUsername;
  }
  public String getBlackUsername()
  {
    return this.blackUsername;
  }
  public void setWhiteUsername(String username) {
    this.whiteUsername = username;
  }
  public void setBlackUsername(String username) {
    this.blackUsername = username;
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
  public String getGame()
  {
    return this.myGame.toString();
  }
}
