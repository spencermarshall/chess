package serviceTests;

import chess.ChessGame;
import org.junit.jupiter.api.*;
import passoffTests.testClasses.TestException;
import passoffTests.testClasses.TestModels;
import server.Server;

import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class AllTests {
    private static TestModels.TestUser existingUser;

    private static TestModels.TestUser newUser;

    private static TestModels.TestCreateRequest createRequest;

    private static Server server;

    private String existingAuth;


    @BeforeAll //this function is copied from the starterCode on github
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);



        existingUser = new TestModels.TestUser();
        existingUser.username = "ExistingUser";
        existingUser.password = "existingUserPassword";
        existingUser.email = "eu@mail.com";

        newUser = new TestModels.TestUser();
        newUser.username = "NewUser";
        newUser.password = "newUserPassword";
        newUser.email = "nu@mail.com";

        createRequest = new TestModels.TestCreateRequest();
        createRequest.gameName = "testGame";
    }
    @AfterAll  //this function is copied from the starterCode on github
    public static void stop()
    {
        server.stop();
    }
    @BeforeEach //this function is copied from the starterCode on github
    public void setup() throws TestException {


        TestModels.TestRegisterRequest registerRequest = new TestModels.TestRegisterRequest();
        registerRequest.username = existingUser.username;
        registerRequest.password = existingUser.password;
        registerRequest.email = existingUser.email;

        //one user already logged in
        //TestModels.TestLoginRegisterResult regResult = server.register(registerRequest);
       // existingAuth = regResult.authToken;
    }
    @Test
    @Order(1)
    @DisplayName("Clear All")
    public void clearWorks() throws Exception {
        TestModels.TestLoginRequest loginRequest = new TestModels.TestLoginRequest();
        loginRequest.username = existingUser.username;
        loginRequest.password = existingUser.password;
       // TestModels.TestLoginRegisterResult loginResult = server.login(loginRequest);



        //todo none of this below this line is correct, idk what above really does
        //Assertions.assertEquals(server., "200",
        //        "Server response code was not 200 OK");
        //assertNotNull(htmlFromServer, "Server returned an empty file");
        //assertTrue(htmlFromServer.contains("CS 240 Chess Server Web API"));
    }
    @Test
    @Order(2)
    @DisplayName("Register Positive (will add)")
    public void clearWorksTest() throws Exception {

        return;
    }
    @Test
    @Order(3)
    @DisplayName("Register negative (won't add a duplicate)")
    public void clearWorksDuplicateUserTest() throws Exception {
        return;
    }
    @Test
    @Order(4)
    @DisplayName("Login Positive (returns authToken)")
    public void loginWorksTest() throws Exception {
        return;
    }
    @Test
    @Order(5)
    @DisplayName("Login negative (incorrect password)")
    public void loginNegativeIncorrectPasswordTest() throws Exception {
        return;
    }
    @Test
    @Order(6)
    @DisplayName("Logout Positive (successful logged out)")
    public void logoutPositiveTest() throws Exception {
        return;
    }
    @Test
    @Order(7)
    @DisplayName("Logout negative (incorrect password)")
    public void logoutNegativeIncorrectPasswordTest() throws Exception {
        return;
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
        return;
    }
    @Test
    @Order(11)
    @DisplayName("Create Game Negative (unauthorized)")
    public void createGameNegativeUnauthorizedTest() throws Exception {
        return;
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
