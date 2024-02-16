package model;

import chess.ChessGame;

public class GameData {
  private int gameID;
  private String whiteUsername;
  private String blackUsername;
  private String gameName;
  private ChessGame myGame;
  public GameData()
  {

  }

  public void clearAll()
  {
    gameID = -1;
    whiteUsername = "";
    blackUsername = "";
    gameName = "";
    myGame = new ChessGame();
    return;
  }
}
