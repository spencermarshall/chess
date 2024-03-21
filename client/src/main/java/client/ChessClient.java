package client;
import model.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.Map;

import static java.lang.Integer.parseInt;
//import com.sun.nio.sctp.NotificationHandler;

//import server.ServerFacade;
public class ChessClient {
   private String authToken;
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
        this.authToken = "";

    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            String output = "";
            boolean quit = false;
            return switch (cmd) {
                case "quit" -> quit();
                case "login" -> login(params);
                case "register" -> register(params);
                case "logout" -> logOut();
                case "create" -> createGame(params);
                case "join" -> joinGame(params);
                case "observe" -> joinGame(params); //this is joinGame() again cuz color is empty; function can handle both
                default -> help();
            };

        } catch (Exception exception) {
            return exception.getMessage();
        }
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
            UserData testUser = new UserData();
            testUser.register(username, password, "");
            server.login(testUser);
            this.username = username;
            this.alreadyLoggedIn = true;


            visitorName = String.join("-", params);
            authToken = server.usernameAuth.get(username);

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
        server.logout(this.authToken);
        this.alreadyLoggedIn = false; //now we are logged out
        return String.format("%s left the shop", visitorName);

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

    public String listPets() throws Exception {
        assert this.alreadyLoggedIn;

        //server.listGames() unsure what data type it returns <T>
        var games = server.listGames();
        var result = new StringBuilder();
        var gson = new Gson();
        for (var game : games) {
            result.append(gson.toJson(game)).append('\n'); //this from petshop, i might have to adjust it slightly
        }
        return result.toString();
    }

    public String joinGame(String... params) throws Exception {
       boolean observe = (params.length == 1);
       String color = "";
       int gameID = -1; //default, it shouldn't be this ever
       if (observe) {
           gameID = parseInt(params[0]);
       }
       else if (params.length == 2) {
           gameID = parseInt(params[0]);
           color = params[1];
       }

       server.joinGame(gameID,color); //todo i might have to implement more for observe
        if (observe) {
            return "successfully observing";
        }
        else {
            return "successfully joined";
        }
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