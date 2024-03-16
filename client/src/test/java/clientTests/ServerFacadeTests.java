package clientTests;

import client.ServerFacade;
import org.junit.jupiter.api.*;
import server.Server;



public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;
    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


   // @Test
//    public void sampleTest() {
  //      Assertions.assertTrue(true);
  //  }
    @Test
    @Order(1)
    @DisplayName("Login positive works")
    public void loginPositive() throws Exception {
        var authData = facade.register
    }

}


/*
public void login(UserData user) throws Exception {
        var path = "/login";
        this.makeRequest("GET", path, user, UserData.class);
    }
    public void logout() throws Exception {
        var path = "/logout";
        UserData user = new UserData();
        this.makeRequest("DELETE", path, user, UserData.class);
    }
    public void createGame(GameData game) throws Exception {
        var path = "/game/create";
        this.makeRequest("POST", path, game, GameData.class);
        }

    public GameData[] listGames() throws Exception {
        var path = "/pet";
        //  record listPetResponse(Pet[] pet) {
        // }
        var response = this.makeRequest("GET", path, null, ChessClient.class);
        //return response.user();
        return null; //temp
    }


    //pretty sure this function below is incorrect tbh
    public void joinGame(int gameID, String color) throws Exception {
        try {
            //if color is "" then it's observer
            var joinPath=String.format("/game/join/%d/%s", gameID, color);
            var observePath=String.format("game/watch/%d", gameID);
            GameData selectedGame=listGames()[gameID - 1];
            if (color.isEmpty()) {
                this.makeRequest("POST", observePath, selectedGame, GameData.class);
            } else {
                this.makeRequest("POST", joinPath, selectedGame, GameData.class);
            }
        } catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
    }


    public UserData addUser(UserData user) throws Exception {
        var path = "/add";
        return this.makeRequest("POST", path, user, UserData.class);
    }

 */