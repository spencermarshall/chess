package client;
import com.google.gson.Gson;
//import exception.ResponseException;
import model.*;
import java.net.URL;
import java.net.HttpURLConnection;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class ServerFacade {

    public Map<String, AuthData> usernameAuth;
    private final String serverUrl;
    public boolean isLoggedIn; //todo this shouldn't be a variable, right?
    private AuthData thisAuth;

    public ServerFacade(String url) {
        serverUrl = url;
        this.usernameAuth = new HashMap<String, AuthData>();
    }
    public ServerFacade() {
        serverUrl = "http://localhost:8080";
        this.usernameAuth = new HashMap<String, AuthData>();

    }


    //i made this lol
    public void login(UserData user) throws Exception {
        var path = "/session";
        var auth = this.makeRequest("GET", path, user, UserData.class);
        this.isLoggedIn = true;
    }
    public void logout(String auth) throws Exception {
        var path = "/session";
        this.makeRequest("DELETE", path, auth,String.class);
        this.isLoggedIn = false;
    }
    public boolean isLoggedIn(UserData user) {
        return false; //todo something here idk,
    }
    public void createGame(GameData game) throws Exception {
        var path = "/game";
        this.makeRequest("POST", path, game, GameData.class);
    }
    ///todo void is temporary, it should return the games i think...?
    public GameData[] listGames() throws Exception {
        var path = "/pet";

        var response = this.makeRequest("GET", path, null, ChessClient.class); ///todo idk if this last parameter is correct
        //return response.user();
        return null; //temp
    }
    public AuthData register(String username, String password, String email) throws Exception {
        var path="/user"; //todo is this function ever called idk cuz add user does the register
        UserData newUser=new UserData();
        newUser.register(username, password, email);
        AuthData auth = new AuthData(username);
        usernameAuth.put(username, auth); //adds auth token string to our map of all usernames/auth token
        var response = this.makeRequest("POST", path, newUser, UserData.class);

        return auth;
    }
    public void clear() throws Exception {
        var path = "/db";
        this.makeRequest("DELETE",path,null,null);
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
        var path = "/user";
        String username = user.getUsername();
        AuthData auth = new AuthData(username);
        usernameAuth.put(username, auth.getAuthString()); //adds auth token string to our map of all usernames/auth token

        return this.makeRequest("POST", path, user, UserData.class);
    }





    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass) throws Exception {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            writeBody(request, http);
            http.setAuthenticator(this.);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            throw new Exception("500");
        }
    }


    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);

            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());

            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws Exception {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            throw new Exception("failure" + status);

           // throw new ResponseException(status, "failure: " + status);
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }


    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}