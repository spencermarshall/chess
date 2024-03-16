package client;
import model.*;

import java.util.Arrays;
//import com.sun.nio.sctp.NotificationHandler;

//import server.ServerFacade;
public class ChessClient {
    private String visitorName = null;
    private final ServerFacade server;
    private final String serverUrl;
   // private final NotificationHandler notificationHandler;
   // private WebSocketFacade ws;
  //  private State state = State.SIGNEDOUT;
    private boolean alreadyLoggedIn;

   public ChessClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
        this.alreadyLoggedIn = false;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            String output = "";
            boolean quit = false;
            return switch (cmd) {
                case "quit" -> {
                    quit();
                    quit = true;
                }
                case "login" -> login(params);
                case "register" -> register();
                case "logout" -> logOut();
                case "adopt" -> adoptPet(params);
                case "adoptall" -> adoptAllPets();
                case "quit" -> "quit";
                default -> help();
            };
            if (quit) {
                return "quit";
            }
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
        if (params.length >= 2) {
            //username and password is what the user typed in
            String username = params[0];
            String password = params[1];
            UserData testUser = new UserData();
            testUser.register(username, password, "");
            server.login(testUser);
            this.alreadyLoggedIn = true;


            visitorName = String.join("-", params);

            return String.format("You signed in as %s.\n You may know play Chess games :)\n These are the available commands \n"+help(), visitorName);
        }
        throw new Exception("Expected: your username and password lol");
    }

    public String register(String... params) throws Exception {
        if (params.length >= 3) {
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
        // todo do we pass in user from paramteter, how do we know which user to logout?
        server.logout();
        return String.format("%s left the shop", visitorName);
    }

    public void createGame(String... params) throws Exception {
       if (params.length >= 1) {
           String gameName = params[0];
           GameData game = new GameData();
           game.setGameName(gameName);
           server.createGame(game);
       }
       throw new Exception("Expected: create <NAME>");
    }

    /*
    public String listPets() throws ResponseException {
        assertSignedIn();
        var pets = server.listPets();
        var result = new StringBuilder();
        var gson = new Gson();
        for (var pet : pets) {
            result.append(gson.toJson(pet)).append('\n');
        }
        return result.toString();
    }

    public String adoptPet(String... params) throws ResponseException {
        assertSignedIn();
        if (params.length == 1) {
            try {
                var id = Integer.parseInt(params[0]);
                var pet = getPet(id);
                if (pet != null) {
                    server.deletePet(id);
                    return String.format("%s says %s", pet.name(), pet.sound());
                }
            } catch (NumberFormatException ignored) {
            }
        }
        throw new ResponseException(400, "Expected: <pet id>");
    }

    public String adoptAllPets() throws ResponseException {
        assertSignedIn();
        var buffer = new StringBuilder();
        for (var pet : server.listPets()) {
            buffer.append(String.format("%s says %s%n", pet.name(), pet.sound()));
        }

        server.deleteAllPets();
        return buffer.toString();
    }



    private Pet getPet(int id) throws ResponseException {
        for (var pet : server.listPets()) {
            if (pet.id() == id) {
                return pet;
            }
        }
        return null;
    }



    }*/
    public String help() {
        if (!this.alreadyLoggedIn) {
            return """
                    - help -> displays command options
                    - quit -> quits the entire chess program
                    - Login <USERNAME> <PASSWORD> -> to login to existing account
                    - Register <USERNAME> <PASSWORD> <EMAIL> -> to create a new account
                    """;
        }
        else {
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