package server;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import dataAccess.MemoryUserDAO;
import service.AuthService;
import service.GameService;
import service.UserService;
import spark.*;
import model.GameData;
import model.AuthData;
import model.UserData;



import com.google.gson.Gson;

public class Server {
    private UserService userService;
    private GameService gameService;
    private AuthService authService;
    public Server()
    {
        userService = new UserService();
        gameService = new GameService();
        authService = new AuthService();
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

        Spark.awaitInitialization();
        return Spark.port();
    }
    private Object registerNewUser(Request req, Response res) throws DataAccessException {
        var user = new Gson().fromJson(req.body(), UserData.class);
        AuthData myAuth = userService.register(user);
        //todo not sure how/when it's a bad request but i think i need more here
        boolean isBadRequest = false;
        if (isBadRequest)
        {
            res.status(400);
            JsonObject badRequest = new JsonObject();
            badRequest.addProperty("message","Error: bad request");
        }
        if (myAuth == null)
        {
            res.status(403);
            JsonObject alreadyTakenError = new JsonObject();
            alreadyTakenError.addProperty("message","Error: already taken");
        }
        if (user == null)
        {
            res.status(500);
            JsonObject genericError = new JsonObject();
            genericError.addProperty("message","Error: description");
        }
        res.status(200); //200 is success
        JsonObject finalRet = new JsonObject();
        finalRet.addProperty("username",user.getUsername());
        finalRet.addProperty("authToken",myAuth.getAuthString());
        //finalRet is JSON
        return finalRet;
    }
    private Object login(Request req, Response res)
    {
        return "";
    }

    public void stop()
    {
        Spark.stop();
        Spark.awaitStop();
    }
    public int port()
    {
        return Spark.port();
    }
    private Object deleteAllGames(Request req, Response res) {
        this.authService.clearAllAuth();
        this.gameService.clearAllGame();
        this.userService.clearAllUsers();
        res.status(200);
        return "{}"; // maybe return '', or maybe return null
    }
}
