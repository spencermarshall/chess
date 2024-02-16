package model;

public class AuthData
{
  private String authToken;
  private String username;

  public AuthData()
  {

  }
  public void clearAll()
  {
    authToken = "";
    username = "";
  }
}
