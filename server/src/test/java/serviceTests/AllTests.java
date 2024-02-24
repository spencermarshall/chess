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
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;

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


    @BeforeAll //this function is copied from the starterCode on github
    public static void init() {
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

        this.existingUser.register(existingUserUsername,existingUserPassword,existingUserEmail);
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

        assertTrue(userService.isEmpty());

    }
    @Test
    @Order(2)
    @DisplayName("Register Positive (will add)")
    public void registerWorksTest() throws Exception {
        newUser.register(newUserUsername,newUserPassword,newUserEmail);
        this.userService.register(newUser);
        assertTrue(userService.getSize() == 2);


        return;
    }
    @Test
    @Order(3)
    @DisplayName("Register negative (won't add a duplicate)")
    public void registerWorksDuplicateUserTest() throws Exception {
        this.userService.register(existingUser); //tries to add an existing User
        assertTrue(userService.getSize() == 1);
        return;
    }
    @Test
    @Order(4)
    @DisplayName("Login Positive (returns authToken)")
    public void loginWorksTest() throws Exception {
        AuthData valid = this.userService.validLogin(existingUser);
        assertNotNull(valid);


        return;
    }
    @Test
    @Order(5)
    @DisplayName("Login negative (incorrect username or password)")
    public void loginNegativeIncorrectPasswordTest() throws DataAccessException {
        AuthData valid = this.userService.login(newUser);
        assertNull(valid);
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
        return;
    }

    @Test
    @Order(9)
    @DisplayName("List Games negative (unauthorized)")
    public void listGameNegativeUnauthorizedTest() throws Exception {
        return;
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
        assertTrue(game.getGameID() > 0);
    }
    @Test
    @Order(12)
    @DisplayName("Join Game Positive works")
    public void joinGamePositiveTest() throws Exception {
        return;
    }
    @Test
    @Order(13)
    @DisplayName("Join Game Negative Already Taken")
    public void joinGameNegativeAlreadyTakenTest() throws Exception {
        return;
    }

}
