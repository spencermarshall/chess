package server;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dataAccess.DataAccessException;
import service.AuthService;
import service.GameService;
import service.UserService;
import spark.*;
import model.GameData;
import model.AuthData;
import model.UserData;

import java.util.*;


import com.google.gson.Gson;

public class Server {
    private UserService userService;
    private GameService gameService;
    private AuthService authService;
    private GameData[] games;
    public Server() throws DataAccessException {
        this.userService = new UserService();
        this.gameService = new GameService();
        this.authService = new AuthService();
        //this.games = new GameData;
    }

    public int run(int desiredPort)
    {

        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/games",this::deleteAllGames);
        Spark.delete("/db",this::deleteAllGames);
        Spark.post("/user",this::registerNewUser);
        Spark.post("/session", this::login);
        Spark.delete("/session", this::logout);
        Spark.get("/game",this::listGames);
        Spark.post("/game",this::createGame);
        Spark.put("/game",this::joinGame);


        Spark.awaitInitialization();
        return Spark.port();
    }
    private Object registerNewUser(Request req, Response res) throws DataAccessException {
        var user = new Gson().fromJson(req.body(), UserData.class);


        //todo not sure how/when it's a bad request but i think i need more here
        boolean incorrectPass = false;
        String errorCode = "200";
        try
        {
            this.userService.isValid(user);

        } catch (DataAccessException errorMessage)
        {
            errorCode = errorMessage.getMessage();
        }

        if (Objects.equals(errorCode, "400") || user.getUsername() == null || user.getPassword() == null)
        {
            return error400(res);

        }
        else if (Objects.equals(errorCode, "403"))
        {
            return error403(res);

        }
        else if (Objects.equals(errorCode, "500"))
        {
            res.status(500);
            JsonObject genericError = new JsonObject();
            genericError.addProperty("message","Error: description");
            //finalRet is JSON
            return genericError;
        }
        else
        {
            AuthData myAuth = userService.register(user);
            res.status(200); //200 is success
            JsonObject finalRet = new JsonObject();
            finalRet.addProperty("username",user.getUsername());
            finalRet.addProperty("authToken",myAuth.getAuthString());
            authService.addAuth(myAuth);

            //finalRet is JSON
            return finalRet;
        }


    }
    private Object login(Request req, Response res) throws DataAccessException
    {
        var user = new Gson().fromJson(req.body(), UserData.class);
        AuthData loginAuth = null;

        String errorCode = "200";
        try
        {
            loginAuth = this.userService.validLogin(user);
        } catch (DataAccessException errorMessage)
        {
            errorCode = errorMessage.getMessage();
        }
        if (Objects.equals(errorCode, "401"))
        {
            return error401(res);
        }
        else if (Objects.equals(errorCode, "500"))
        {
            return error500(res);
        }
        else
        {
            res.status(200); //200 is success
            JsonObject finalRet = new JsonObject();
            finalRet.addProperty("username",user.getUsername());
            finalRet.addProperty("authToken",loginAuth.getAuthString());
            //add auth to authService
            authService.addAuth(loginAuth);
            //finalRet is JSON
            return finalRet;
        }

    }
    private Object logout(Request req, Response res) throws DataAccessException {
        String header = req.headers("Authorization");
        boolean validAuthToken = false;
        try
        {
            validAuthToken = this.authService.verifyAuth(header);
        } catch (DataAccessException errorMessage)
        {
            return error401(res);
        }

        if (validAuthToken)
        {
            res.status(200); //200 is success
            JsonObject success = new JsonObject();
            authService.removeAuth(header);
            success.addProperty("message","");
            //finalRet is JSON
            return success;

        }
        return "{something wrong}";

    }
    private Object listGames(Request req, Response res) throws DataAccessException {
        String header = req.headers("Authorization");
        JsonObject success = new JsonObject();


        AuthData myAuth = new AuthData(header, true);
        Collection<GameData> allGames =this.gameService.returnAllGames(myAuth);


        boolean validAuthToken = false;
        try
        {
            validAuthToken = this.authService.verifyAuth(header);
        } catch (DataAccessException errorMessage)
        {
            //success.addProperty("games:", "[]");
            //return success;
            return error401(res);
        }
        res.status(200); //200 is success
        res.type("application/json");


        if (allGames == null || allGames.isEmpty())
        {
            JsonObject nothing = new JsonObject();
            return new Gson().toJson(Map.of("games",allGames));


        }



        Collection<GameData> allGamesVector =this.gameService.returnAllGames(myAuth);

        this.games = new GameData[allGamesVector.size()];
        for (int j = 0; j < allGamesVector.size(); ++j) {
            this.games[j] = (GameData)allGamesVector.toArray()[j];
        }
        JsonObject me = new JsonObject();
        String listGame = new Gson().toJson(this.games);
        if (this.games.length == 0) {
            return new Gson().toJson(Map.of("games",""));
        }
        return new Gson().toJson(Map.of("games",this.games));
    }
    private Object createGame(Request req, Response res) throws DataAccessException {
        String header = req.headers("Authorization");
        boolean validAuthToken = false;
        try {
            validAuthToken = this.authService.verifyAuth(header);
        } catch (DataAccessException errorMessage) {
            return error401(res);
        }
        //username found from auth string
        String username = this.authService.getUsername(header);


        //create game
        var game = new Gson().fromJson(req.body(), GameData.class);
       // if (Objects.equals(game.getGameName(), null)) {
        //    return error400(res);
       // }
        //add it to games thru GameService
        AuthData myAuth = new AuthData(header);
        gameService.addGame(game, myAuth);
        res.status(200); //200 is success
        JsonObject success = new JsonObject();
        this.authService.addAuth(myAuth);
        success.addProperty("gameID",game.getGameID());
        //finalRet is JSON
        return success;
    }
    private Object joinGame(Request req, Response res) throws DataAccessException {
        res.status(200); //200 is success
        String header = req.headers("Authorization");
        String body = req.body();
        String color = "EMPTY";
        if (body.contains("WHITE")) {
            color = "WHITE";
        }
        else if (body.contains("BLACK")) {
            color = "BLACK";
        }
        boolean validAuthToken = false;
        try {
            validAuthToken = this.authService.verifyAuth(header);
        } catch (DataAccessException errorMessage) {
            return error401(res);
        }
        //verify gameID exists
        String gameID = body.substring(body.indexOf("gameID")+8);
        gameID = gameID.substring(0,gameID.length()-1);
        String username = this.authService.getUsername(header);
        int intGameID = Integer.parseInt(gameID);
        GameData myGame = null;
        try {
            myGame = this.gameService.getGame(intGameID);

        } catch (DataAccessException error) {
            //game id does not exist bruh
            return error400(res);
        }
        //watch or play
        if (color.equals("EMPTY")) {
            //add as spectator; not needed for this phase
            res.status(200);
            return "{}";
        }
        if (color.equals("BLACK")) {
            if (!Objects.equals(myGame.getBlackUsername(), null)) {
                //black is already taken
                return error403(res);
            }
            //else add them
            this.gameService.setColor(intGameID,color, username);
        }
        else if (color.equals("WHITE")) {
            if (!Objects.equals(myGame.getWhiteUsername(), null)) {
                //white is already taken
                return error403(res);
            }
            //else add them
            this.gameService.setColor(intGameID,color, username);
        }
        //else we chose a color and it is available, so join
        return "{}";
    }
    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
    private Object deleteAllGames(Request req, Response res) throws DataAccessException {
        this.authService.clearAllAuth();
        this.gameService.clearAllGame();
        this.userService.clearAllUsers();
        if (authService.isEmpty() && gameService.isEmpty() && userService.isEmpty()) {
            res.status(200);

        }
        else {
            res.status(401);
        }
        return "{}"; // maybe return '', or maybe return null
    }
    //error or success for code decomposition, 200 is not included because of different display messages
    private JsonObject error400(Response res) {
        res.status(400);
        JsonObject badRequest = new JsonObject();
        badRequest.addProperty("message","Error: bad request");
        return badRequest;
    }
    private JsonObject error401(Response res) {
        res.status(401); //unauthorized
        JsonObject genericError = new JsonObject();
        genericError.addProperty("message","Error: unauthorized");
        //finalRet is JSON
        return genericError;
    }
    private JsonObject error403(Response res) {
        res.status(403);
        JsonObject alreadyTakenError = new JsonObject();
        alreadyTakenError.addProperty("message","Error: already taken");
        //finalRet is JSON
        return alreadyTakenError;
    }
    private JsonObject error500(Response res) {
        res.status(500);
        JsonObject genericError = new JsonObject();
        genericError.addProperty("message","Error: description");
        //finalRet is JSON
        return genericError;
    }
}