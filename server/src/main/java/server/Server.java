package server;
import spark.*;
import model.GameData;
import model.AuthData;
import model.UserData;

public class Server {
    public Server()
    {

    }

    public int run(int desiredPort)
    {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/games",this::deleteAllGames);
        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop()
    {
        Spark.stop();
        Spark.awaitStop();
    }

    private Object deleteAllGames(Request req, Response res)
    {
        res.status(400);
        return ' ';
    }
}
