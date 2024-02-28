package model;

import java.util.UUID;

public class AuthData
{
  private String authToken;
  private String username;
  private boolean isGame;

  public AuthData(String username)
  {
      authToken = UUID.randomUUID().toString();
      this.username = username;
  }
  public boolean isGame()
  {
    return this.isGame;
  }
  public AuthData(String uuid, boolean isUUIDString)
  {
      if (isUUIDString)
      {
        this.authToken = uuid;
      }
  }
  public String getUsername()
  {
    return this.username;
  }
  public String getAuthString()
  {
    return this.authToken;
  }
}
