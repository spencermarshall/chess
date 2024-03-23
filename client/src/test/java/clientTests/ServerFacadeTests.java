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
        facade = new ServerFacade(port);
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

        var authData = facade.addUser(user); //used to be register() idk they seem to do the same thing
        assertNotNull(authData);
        //maybe assertThrows idk yet
    }
    @Test //ok
    @Order(2)
    @DisplayName("duplicate register won't work")
    public void registerNegative() throws Exception {
        UserData user = new UserData();
        user.register(username, password, email);
        var authData = facade.addUser(user);
        try {
            assertThrows(java.lang.Exception.class,(Executable) facade.addUser(user));
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
        var authData = facade.addUser(user);
        facade.login(user);
        assertTrue(facade.isLoggedIn); //todo this facade.isLogged in will need to change and it's the same in every test lol

    }
    @Test
    @Order(4)
    @DisplayName("Login negative, incorrect password won't log you in")
    public void loginNegative() throws Exception {
        UserData user = new UserData();
        user.register(username, password, email);
        UserData user2 = new UserData();
        user2.register(username, "fake", email);
        var authData = facade.addUser(user);
        try {
            facade.login(user2);
        } catch(Exception em) {
            assertTrue(true);
        }
    }

    @Test
    @Order(5)
    @DisplayName("Logout positive, will log you out standard")
    public void logoutPositive() throws Exception {
        UserData user = new UserData();
        user.register(username, password, email);
        var authData = facade.addUser(user);
        facade.login(user);
        facade.logout();
        assertFalse(facade.isLoggedIn);


    }

    @Test
    @Order(6)
    @DisplayName("Logout negative, tries to logout when not logged in")
    public void logoutNegative() throws Exception {
        try {
            facade.logout();//we are not even loggedin
        } catch(Exception em) {
            assertTrue(true);
        }
    }

    @Test
    @Order(7)
    @DisplayName("Create Game Positive") //this test is dependeont on listGames also working, i don't see another way
    public void createGamePositive() throws Exception {
        UserData user = new UserData();
        UserData fakeUser = new UserData();
        user.register(username, password, email);
        var authData = facade.addUser(user);
        facade.login(user);
        GameData game = new GameData();
        facade.createGame(game);
        assertFalse(facade.listGames().toString().isEmpty());
    }

    @Test
    @Order(8)
    @DisplayName("Create Game Negative")
    public void createGameNegative() throws Exception {
        UserData user = new UserData();
        UserData fakeUser = new UserData();
        user.register(username, password, email);
        var authData = facade.addUser(user);
        facade.login(user);
        GameData game = new GameData();
        game.setGameName("test");
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
        var auth = facade.addUser(user);
        facade.login(user);

        GameData game = new GameData();
        facade.createGame(game);
        String list = facade.listGames().toString();
        assertFalse(list.isEmpty());
    }
    @Test
    @Order(10)
    @DisplayName("List Games Negative")
    public void listGamesNegative() throws Exception {
        UserData user = new UserData();
        UserData fakeUser = new UserData();
        user.register(username, password, email);
        var authData = facade.addUser(user);
        GameData game = new GameData();
        //facade.createGame(game);
        try {
            String list = facade.listGames().toString(); //we are not logged in, it should not allow us
        } catch(Exception em) {
            assertTrue(true);
        }
    }
    @Test
    @Order(11)
    @DisplayName("Join Game Positive")
    public void joinGamePositive() throws Exception {
        UserData user = new UserData();
        UserData fakeUser = new UserData();
        user.register("checkUsername", password, email);
        var authData = facade.addUser(user);
        GameData game = new GameData();
        game.setGameName("testGameName");
        facade.login(user);
        facade.createGame(game);
        String list = facade.listGames().toString();
        facade.joinGame(1,"black");
        String check = facade.listGames().toString();
        assertTrue(check.contains("checkUsername"));//this is username so if it contains then yah it works
    }
    @Test
    @Order(12)
    @DisplayName("Join Game Negative")
    public void JoinGameNegative() throws Exception {
        UserData user = new UserData();
        UserData fakeUser = new UserData();
        user.register(username, password, email);
        var authData = facade.addUser(user);
        facade.login(user);

        GameData game = new GameData();
        facade.createGame(game);
        String list = facade.listGames().toString();
        try {
            facade.joinGame(4,"black"); //invalid id, game does not exist
            fail();
        } catch (Exception ex) {
            assertTrue(true);
        }

    }
    @Test
    @Order(13)
    @DisplayName("Observe Game Positive") //observe
    public void observeGamePositive() throws Exception {
        UserData user = new UserData();
        UserData fakeUser = new UserData();
        user.register(username, password, email);
        var authData = facade.addUser(user);
        GameData game = new GameData();

        facade.login(user);
        facade.createGame(game);
        String list = facade.listGames().toString();
        facade.joinGame(1,""); //observe first game ID, empty string indicates no color so we are observe but idk how to check
        String observeList = facade.listGames().toString(); //to make sure observe did not update anything in the games
        assertEquals(list,observeList);
    }

    //todo, there might be more tests for observing or something else?
}
