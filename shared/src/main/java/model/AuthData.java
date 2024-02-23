package model;

import java.util.UUID;

public class AuthData
{
  private String authToken;
  private String username;

  public AuthData(String username)
  {
      authToken = UUID.randomUUID().toString();
      this.username = username;
  }
  public void clearAll()
  {
    authToken = "";
    username = "";
  }
  public String getAuthString()
  {
    return this.authToken;
  }
}
