package chess;

public class JoinRegister {
    private String playerColor;
    private int gameID;
    private String username;

    public JoinRegister(){
        playerColor = "";
        gameID = -1;
        username = "";
    }
    public JoinRegister(String color, int id) {
        this.playerColor = color;
        this.gameID = id;
    }
    public int getGameID() {
        return this.gameID;
    }
    public void setGameID (int id){
        this.gameID = id;
    }

    public String getPlayerColor() {
        return playerColor;
    }

    public String getUsername() {
        return this.username;
    }
    public void setPlayerColor(String playerColor) {
        this.playerColor = playerColor;
    }
}
