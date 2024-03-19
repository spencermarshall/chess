package clientTests;

import client.ServerFacade;
import dataAccess.DataAccessException;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import server.Server;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;
    private String username = "username";
    private String password = "password";
    private String email = "email@gmail.com";

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:8080");
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @BeforeEach //this function is copied from the starterCode on github
    public void setup() throws Exception {
        facade.clear();



        //one user already logged in
        //TestModels.TestLoginRegisterResult regResult = server.register(registerRequest);
        // existingAuth = regResult.authToken;
        return;
    }

   // @Test
//    public void sampleTest() {
  //      Assertions.assertTrue(true);
  //  }

    @Test
    @Order(1)
    @DisplayName("register positive works")
    public void registerPositive() throws Exception {
        UserData user = new UserData();
        user.register(username, password, email);

        var authData = facade.register("username","password","email@gmail.com");
        assertNotNull(authData);
        //maybe assertThrows idk yet
    }
    @Test //ok
    @Order(2)
    @DisplayName("duplicate register won't work")
    public void registerNegative() throws Exception {
        UserData user = new UserData();
        user.register(username, password, email);
        var authData = facade.register("username","password","email@gmail.com");
        try {
            assertThrows(java.lang.Exception.class,(Executable) facade.register(username, password, email));
        } catch(Exception em) {
            assertTrue(true);
        }
        //tries to register same user twice, shouldn't work
       // assertNull(authData2); //expected is null because register won't be valid, because username already exists
    }
    @Test
    @Order(3)
    @DisplayName("Login positive works")
    public void loginPositive() throws Exception {
        UserData user = new UserData();
        user.register(username, password, email);
        var authData = facade.register("username","password","email@gmail.com");
        facade.login(user);
        assertTrue(facade.isLoggedIn); //todo this facade.isLogged in will need to change and it's the same in every test lol

    }
    @Test
    @Order(4)
    @DisplayName("Login negative, incorrect password won't log you in")
    public void loginNegative() throws Exception {
        UserData user = new UserData();
        user.register(username, password, email);
        var authData = facade.register("username","notrealPass","email@gmail.com");
        try {
            facade.login(user);
        } catch(Exception em) {
            assertTrue(true);
        }
    }

    @Test
    @Order(5)
    @DisplayName("Logout positive, will log you out")
    public void logoutPositive() throws Exception {
        UserData user = new UserData();
        user.register(username, password, email);
        var authData = facade.register("username","password","email@gmail.com");
        facade.login(user);
        facade.logout(username);
        assertFalse(facade.isLoggedIn);


    }

    @Test
    @Order(6)
    @DisplayName("Logout negative,...")
    public void logoutNegative() throws Exception {
        UserData user = new UserData();
        UserData fakeUser = new UserData();
        user.register(username, password, email);
        var authData = facade.register("username","notrealPass","email@gmail.com");
        try {
            facade.login(fakeUser);
        } catch(Exception em) {
            assertTrue(true);
        }
    }

    @Test
    @Order(7)
    @DisplayName("Create Game Positive")
    public void createGamePositive() throws Exception {
        UserData user = new UserData();
        UserData fakeUser = new UserData();
        user.register(username, password, email);
        var authData = facade.register(username,password,email);
        facade.login(user);
        GameData game = new GameData();
        facade.createGame(game);
        assertTrue(facade.listGames().length > 0);
    }

    @Test
    @Order(8)
    @DisplayName("Create Game Negative")
    public void createGameNegative() throws Exception {
        GameData game = new GameData();
        facade.createGame(game);
        try {
            facade.createGame(game); //create duplicate game
        } catch(Exception em) {
            assertTrue(true);
        }

    }
    @Test
    @Order(9)
    @DisplayName("List Games Positive")
    public void listGamesPositive() throws Exception {
        UserData user = new UserData();
        UserData fakeUser = new UserData();
        user.register(username, password, email);
        var authData = facade.register(username, password,email);
        GameData game = new GameData();
        facade.createGame(game);
        GameData[] list = facade.listGames();
        assertEquals(1, list.length);
    }
    @Test
    @Order(10)
    @DisplayName("List Games Negative")
    public void listGamesNegative() throws Exception {
        UserData user = new UserData();
        UserData fakeUser = new UserData();
        user.register(username, password, email);
        var authData = facade.register(username, password,email);
        GameData game = new GameData();
        //facade.createGame(game);
        GameData[] list = facade.listGames();
        assertEquals(0, list.length);
    }
    @Test
    @Order(11)
    @DisplayName("Join Game Positive")
    public void joinGamePositive() throws Exception {
        UserData user = new UserData();
        UserData fakeUser = new UserData();
        user.register(username, password, email);
        var authData = facade.register("username","notrealPass","email@gmail.com");
        GameData game = new GameData();
        facade.createGame(game);
        GameData[] list = facade.listGames();
        facade.joinGame(1,"black");
        //todo some other function call idk
        fail();
    }
    @Test
    @Order(12)
    @DisplayName("Join Game Negative")
    public void joinGameNegative() throws Exception {
        UserData user = new UserData();
        UserData fakeUser = new UserData();
        user.register(username, password, email);
        var authData = facade.register("username","notrealPass","email@gmail.com");
        GameData game = new GameData();
        facade.createGame(game);
        GameData[] list = facade.listGames();
        facade.joinGame(4,"black"); //invalid id, game does not exist
        //todo maybe assert throw, idk
        fail();
    }
    @Test
    @Order(13)
    @DisplayName("Observe Game Positive") //observe
    public void observeGamePositive() throws Exception {
        UserData user = new UserData();
        UserData fakeUser = new UserData();
        user.register(username, password, email);
        var authData = facade.register("username","notrealPass","email@gmail.com");
        GameData game = new GameData();
        facade.createGame(game);
        GameData[] list = facade.listGames();
        facade.joinGame(1,""); //observe first game ID, empty string indicates no color so we are observe but idk how to check
        //todo idk how to check this, maybe write a new function
        fail();
    }
    //todo, there might be more tests for observing or something else?
}
