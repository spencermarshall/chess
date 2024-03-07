package serviceTests;

import chess.ChessGame;
import dataAccess.DataAccessException;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.*;
import passoffTests.testClasses.TestException;
import passoffTests.testClasses.TestModels;
import server.Server;
import service.AuthService;
import service.GameService;
import service.UserService;

import java.net.HttpURLConnection;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class AllTests {


    private static Server server;

    private String existingAuth;
    private UserService userService = new UserService();
    private GameService gameService = new GameService();
    private AuthService authService = new AuthService();
    public UserData existingUser = new UserData();
    public UserData newUser = new UserData();
    String existingUserUsername = "ExistingUser";
    String existingUserPassword = "existingUserPassword";
    String existingUserEmail = "e@mail.com";


    String newUserUsername = "NewUser";
    String newUserPassword = "newUserPassword";
    String newUserEmail = "new@mail.com";

    public AllTests() throws DataAccessException {
    }


    @BeforeAll //this function is copied from the starterCode on github
    public static void init() throws DataAccessException {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);



    }
    @AfterAll  //this function is copied from the starterCode on github
    public static void stop()
    {
        server.stop();
    }
    @BeforeEach //this function is copied from the starterCode on github
    public void setup() throws TestException, DataAccessException {

        this.userService.clearAllUsers();
        this.gameService.clearAllGame();
        this.authService.clearAllAuth();
        this.existingUser.register(existingUserUsername, existingUserPassword, existingUserEmail);
        this.userService.register(existingUser);


        //one user already logged in
        //TestModels.TestLoginRegisterResult regResult = server.register(registerRequest);
       // existingAuth = regResult.authToken;
    }
    @Test
    @Order(1)
    @DisplayName("Clear All")
    public void clearWorksTest() throws Exception {
        //user is already added
        userService.clearAllUsers();
        authService.clearAllAuth();
        gameService.clearAllGame();

        assertTrue(userService.isEmpty());

    }
    @Test
    @Order(2)
    @DisplayName("Register Positive (will add)")
    public void registerWorksTest() throws Exception {
        newUser.register(newUserUsername,newUserPassword,newUserEmail);
        this.userService.register(newUser);
        assertEquals(2, userService.getSize());

    }
    @Test
    @Order(3)
    @DisplayName("Register negative (won't add a duplicate)")
    public void registerWorksDuplicateUserTest() throws Exception {
        //this.userService.register(existingUser);
       // assertEquals(1, userService.getSize());
        assertThrows(DataAccessException.class, () -> {
            this.userService.register(existingUser);
        });

    }
    @Test
    @Order(4)
    @DisplayName("Login Positive (returns authToken)")
    public void loginWorksTest() throws Exception {
        AuthData valid = this.userService.validLogin(existingUser);
        //it will be null if it's not a valid login, or throw an error depending
        assertNotNull(valid);
    }
    @Test
    @Order(5)
    @DisplayName("Login negative (incorrect username or password)")
    public void loginNegativeIncorrectPasswordTest() throws DataAccessException {
       // AuthData valid = this.userService.login(newUser);
       // assertNull(valid);
        assertThrows(DataAccessException.class, () -> {
            this.userService.login(newUser);
        });
        //will be null if it couldn't login, meaning incorrect username or password
    }
    @Test
    @Order(6)
    @DisplayName("Logout Positive (successful logged out)")
    public void logoutPositiveTest() throws Exception {
        this.userService.logout(existingUser);
        //will yell if not empty
        assertTrue(this.userService.isEmpty());
    }
    @Test
    @Order(7)
    @DisplayName("Logout negative (incorrect password)")
    public void logoutNegativeIncorrectPasswordTest() throws Exception {
        this.userService.logout(newUser);
        assertFalse(this.userService.isEmpty());

    }
    @Test
    @Order(8)
    @DisplayName("List Games Positive (works)")
    public void listGamesPositiveTest() throws Exception {
        AuthData auth = new AuthData(existingAuth);
        GameData newGame = new GameData();
        newGame.addUser("BLACK", "blackTom");
        newGame.addUser("WHITE", "whiteJack");
        this.gameService.addGame(newGame, auth);
        Collection<GameData> allGames = this.gameService.returnAllGames(auth);
        assertEquals(1, allGames.size());
    }

    @Test
    @Order(9)
    @DisplayName("List Games negative (unauthorized)")
    public void listGameNegativeUnauthorizedTest() throws Exception {
        AuthData auth = new AuthData(existingAuth);
        GameData newGame = new GameData();
        this.gameService.addGame(newGame, auth);
        AuthData notRealAuth = null;
        Collection<GameData> allGames =this.gameService.returnAllGames(notRealAuth);
        //this means it's not listing anything because we are unauthorized
        assertEquals(0,allGames.size());
    }
    @Test
    @Order(10)
    @DisplayName("Create Game Positive (gives gameID)")
    public void createGamePositiveTest() throws Exception {
        AuthData auth = new AuthData(existingUserUsername);
        GameData game = new GameData();
        this.gameService.addGame(game, auth);
        assertFalse(this.gameService.isEmpty());


    }
    @Test
    @Order(11)
    @DisplayName("Create Game Negative (unauthorized)")
    public void createGameNegativeUnauthorizedTest() throws Exception {
        AuthData auth = null;
        GameData game = new GameData();
        this.gameService.addGame(game, auth);
        assertTrue(this.gameService.isEmpty());
    }
    @Test
    @Order(12)
    @DisplayName("Join Game Positive works")
    public void joinGamePositiveTest() throws Exception {
        GameData myGame = new GameData();
        AuthData userAuth = new AuthData(existingUserUsername);
        AuthData gameAuth = new AuthData(userAuth.getAuthString(), true);
        this.gameService.addGame(myGame, gameAuth);
        //this joins it
        this.gameService.setColor(1,"WHITE","me");
        assertNotNull(this.gameService.getGame(1).getWhiteUsername());

        return;
    }

    @Test
    @Order(13)
    @DisplayName("Join Game Negative Already Taken")
    public void joinGameNegativeAlreadyTakenTest() throws Exception {
        GameData myGame = new GameData();
        AuthData userAuth = new AuthData(existingUserUsername);
        AuthData gameAuth = new AuthData(userAuth.getAuthString(), true);
        this.gameService.addGame(myGame, gameAuth);
        //this joins it
        this.gameService.setColor(1,"WHITE","me");
        //this tries to have another user join
        this.gameService.setColor(1,"WHITE","next");
        assertEquals("me",this.gameService.getGame(1).getWhiteUsername());
    }


    @Test
    @Order(12)
    @DisplayName("Add Auth Positive")
    public void addAuthWorks() throws DataAccessException {
        AuthData auth = new AuthData(existingUserUsername);
        this.authService.addAuth(auth);
        assertFalse(this.authService.isEmpty());
    }
    /*@Test
    @Order(12)
    @DisplayName("Add Auth Negative")
    public void addAuthNull() throws DataAccessException {
        AuthData auth = new AuthData("not exist");
        this.authService.addAuth(auth);
        assertFalse(this.authService.isEmpty());
    }*/

    @Test
    @Order(13)
    @DisplayName("Auth is empty positive")
    public void authIsEmpty() throws DataAccessException {
        AuthData myAuth = new AuthData(existingUserUsername);
        this.authService.addAuth(myAuth);
        this.authService.removeAuth(myAuth.getAuthString());
        assertTrue(this.authService.isEmpty());
    }

    @Test
    @Order(14)
    @DisplayName("Auth is empty negative")
    public void authIsNotEmpty() throws DataAccessException {
        AuthData myAuth = new AuthData(existingUserUsername);
        this.authService.addAuth(myAuth);
        assertFalse(this.authService.isEmpty());
    }

    @Test
    @Order(15)
    @DisplayName("remove Auth works")
    public void removeAuthPositive() throws DataAccessException {
        AuthData myAuth = new AuthData(existingUserUsername);
        this.authService.addAuth(myAuth);
        this.authService.removeAuth(myAuth.getAuthString());
        assertTrue(this.authService.isEmpty());
    }

    @Test
    @Order(16)
    @DisplayName("remove auth negative")
    public void removeAuthNegative() throws DataAccessException {
        AuthData myAuth = new AuthData(existingUserUsername);
        this.authService.addAuth(myAuth);
        this.authService.removeAuth("not a real auth");
        assertFalse(this.authService.isEmpty());
    }


   /* @Test
    @Order(18)
    @DisplayName("remove auth negative")
    public void verifyAuthNegative() throws DataAccessException {
        AuthData myAuth = new AuthData(existingUserUsername);
        this.authService.addAuth(myAuth);
        assertFalse(this.authService.verifyAuth("not a real auth"));
    }*/
   @Test
   @Order(18)
   @DisplayName("remove auth positive")
   public void verifyAuthPositive() throws DataAccessException {
       AuthData myAuth = new AuthData(existingUserUsername);
       this.authService.addAuth(myAuth);
       assertTrue(this.authService.verifyAuth(myAuth.getAuthString()));
   }

    @Test
    @Order(19)
    @DisplayName("clear auth positive")
    public void clearAuthPositive() throws DataAccessException {
        AuthData myAuth = new AuthData(existingUserUsername);
        this.authService.clearAllAuth();
        assertTrue(this.authService.isEmpty());
    }



  /*
    public String getUsername(String auth) throws DataAccessException {
        return this.dataAccessAuth.getUsername(auth);
    }





    public void clearAllAuth() throws DataAccessException {
        this.dataAccessAuth.clearAllAuth();
    }*/
}
