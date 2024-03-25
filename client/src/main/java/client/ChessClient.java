package client;
import model.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static java.lang.Integer.parseInt;
//import com.sun.nio.sctp.NotificationHandler;

//import server.ServerFacade;
public class ChessClient {
   private AuthData authToken;
   private String username;
    private String visitorName = null;
    private final ServerFacade server;
    private final String serverUrl;
   // private final NotificationHandler notificationHandler;
   // private WebSocketFacade ws;
  //  private State state = State.SIGNEDOUT;
    public boolean alreadyLoggedIn;

   public ChessClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
        this.alreadyLoggedIn = false;
        this.username = "";

    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            char ws = 's';
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            String output = "";
            boolean quit = false;
            return switch (cmd) {
                case "quit" -> quit();
                case "login" -> login(params);
                case "register" -> register(params);
                case "logout" -> logOut();
                case "list" -> list();
                case "create" -> createGame(params);
                case "join" -> joinGame(params);
                case "observe" -> joinGame(params); //this is joinGame() again cuz color is empty; function can handle both
                default -> help();


            };

        } catch (Exception exception) {
            return exception.getMessage();
        }
    }

    public String list() throws Exception {
       assert this.alreadyLoggedIn;
       var allGames = server.listGames();
       if (allGames == "") {
           return "There are no games to display :)";
       }
       StringBuilder output = new StringBuilder();
       Map<String, ArrayList> test =(Map<String, ArrayList>) allGames;
       ArrayList<Map> listGames = test.get("games");
       for (int i = 0; i < listGames.size(); ++i) {
           output.append("Game ID: ");
           double gameID =(double) listGames.get(i).get("gameID");
           output.append((int)gameID);
           output.append(" Game Name: ");
           output.append(listGames.get(i).get("gameName"));
           //todo add white username and black
           output.append(" White Username: ");
           output.append("[WHITE USERNAME]");
           output.append(" Black Username: ");
           output.append("[BLACK USERNAME]\n");
       }
       return output.toString();
    }

    public String quit() throws Exception {
       return "quit";
       //return String.format("%s left the shop", visitorName);
    }

    private String assertSignedIn() throws Exception {
        if (!this.alreadyLoggedIn) { //this means we are not login so we throw exception
            throw new Exception("You are not even signed in bro");
        }
        return "true";
    }

    public String login(String... params) throws Exception {
        if (params.length == 2) {
            //username and password is what the user typed in
            String username = params[0];
            String password = params[1];
          //  UserData loginUser = server.usernameToUserData.get(username);
            UserData loginUser = new UserData();
            loginUser.register(username, password, "");


            this.authToken = server.login(loginUser);
            this.username = username;
            this.alreadyLoggedIn = true;


            visitorName = String.join("-", params);
          //  this.authToken = server.usernameAuth.get(loginUser);

            return String.format("You signed in as %s.\n You may know play Chess games :)\n These are the available commands \n"+help(), visitorName);
        }
        throw new Exception("Expected: your username and password lol");
    }

    public String register(String... params) throws Exception {
        if (params.length == 3) {
            //username and password and email is what the user typed in
            String username = params[0];
            String password = params[1];
            String email = params[2];
            UserData user = new UserData();
            user.register(username, password, email);
            server.addUser(user);


            return String.format("You are registered as %s. You may now login if desired. :)", username);
        }
        throw new Exception("Expected: register <username> <password> <email>");
    }

    public String logOut() throws Exception {
        assert this.alreadyLoggedIn; //we need to be logged in to log out lol
        // todo auth token?
        server.logout();
        this.alreadyLoggedIn = false; //now we are logged out
        this.username = "";
        this.authToken = null;
        return String.format("%s left the arena", visitorName);

    }

    public String createGame(String... params) throws Exception {
       if (params.length == 1) {
           String gameName = params[0];
           GameData game = new GameData();
           game.setGameName(gameName);
           server.createGame(game);
           return "game successfully created";
       }
       throw new Exception("Expected: create <NAME>");
    }


    public String joinGame(String... params) throws Exception {
       if (params.length == 0 || params.length > 2) {
           //if just "join" or more parameters than we need throw error
           throw new Exception("ERROR Expected: join <GAME ID> [WHITE | BLACK | <empty>]");
       }
      //determines if user is observing (true) or joining (False)
       boolean observe = (params.length == 1);
       String color = "";
       int gameID = parseInt(params[0]);
       if (params.length == 2) {
           color = params[1];
       }


        if (observe) {
            color="";
        }
        ArrayList joinedGame = (ArrayList) server.joinGame(gameID,color);
        for (int r = 0; r < joinedGame.size(); ++r) {
            ArrayList row =(ArrayList) joinedGame.get(r);
            for (int c = 0; c < row.size(); ++c) {
                Object item = row.get(c);
                if (item == null) {
                    continue;
                }
                int h = 3;
            }
        }

        return joinedGame.toString();


    }


    public String help() {
        if (!this.alreadyLoggedIn) { //if user is not logged in show this
            return """ 
                    - help -> displays command options
                    - quit -> quits the entire chess program
                    - Login <USERNAME> <PASSWORD> -> to login to existing account
                    - Register <USERNAME> <PASSWORD> <EMAIL> -> to create a new account
                    """;
        }
        else { // if user is logged in show this
            return """
                - help -> displays command options
                - logout -> logs you out
                - create <GAME NAME> -> Creates a new game, does not join it though
                - list -> lists all the current games that are craeted
                - join <GAME ID> [WHITE | BLACK | <empty>] -> joins the specified game, if it exists
                - observe <GAME ID> -> allows you to watch a game given the game ID, not as a player, you will just watch
                """;
        }
    }
}