package client;
import chess.ChessGame;
import com.google.gson.Gson;
//import exception.ResponseException;
import model.*;
import java.net.URL;
import java.net.HttpURLConnection;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ServerFacade {

    public Map<UserData, AuthData> usernameAuth;
    public Map<String, UserData> usernameToUserData;

    private final String serverUrl;
    public boolean isLoggedIn; //todo this shouldn't be a variable, right?
    private AuthData thisAuth;
    private String usernameLoggedIn;
    private UserData userLoggedIn;

    public ServerFacade(String url) {
        serverUrl = url;
        this.usernameAuth = new HashMap<UserData, AuthData>();
        this.usernameToUserData = new HashMap<String, UserData>();
    }
    public ServerFacade() {
        serverUrl = "http://localhost:8080";
        this.usernameAuth = new HashMap<UserData, AuthData>();
        this.usernameToUserData = new HashMap<String, UserData>();
    }


    //i made this lol
    public AuthData login(UserData user) throws Exception {
        var path = "/session";
        var auth = this.makeRequest("POST", path, user, AuthData.class,null);
        this.isLoggedIn = true;
        this.userLoggedIn = user; //technicaly it's missing email but username and password arew the ame
        this.thisAuth = auth;
        this.usernameLoggedIn = user.getUsername();
        return auth;
    }
    public void logout() throws Exception {
        var path = "/session";
        this.makeRequest("DELETE", path, this.userLoggedIn,UserData.class,this.thisAuth.getAuthString());
        this.isLoggedIn = false;
    }
    public void createGame(GameData game) throws Exception {
        var path = "/game";
        this.makeRequest("POST", path, game, GameData.class,this.thisAuth.getAuthString());
    }
    public Object listGames() throws Exception {
        var path = "/game";
        record listGameResponse() { }
        var response = this.makeRequest("GET", path, null, Object.class, this.thisAuth.getAuthString()); ///todo idk if this last parameter is correct
        return response;
    }

    public void clear() throws Exception {
        var path = "/db";
        this.makeRequest("DELETE",path,null,null,null);
    }


    //pretty sure this function below is incorrect tbh
    public void joinGame(int gameID, String color) throws Exception {
        try {
            //if color is "" then it's observer
            var joinPath=String.format("/game/%d/%s/%s", gameID, color,this.usernameLoggedIn);
            var observePath = String.format("game/%d", gameID);
            String selectedGame = null;
            if (color.isEmpty()) {
                this.makeRequest("PUT", observePath, null, GameData.class, this.thisAuth.getAuthString());
            } else {
                this.makeRequest("PUT", joinPath, null, GameData.class, this.thisAuth.getAuthString());
            }
        } catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
    }


    public UserData addUser(UserData user) throws Exception {
        var path = "/user";
        String username = user.getUsername();
        AuthData auth = new AuthData(username); //todo this is creating a new auth but we already make one in server
        usernameAuth.put(user, auth); //adds auth token string to our map of all usernames/auth token
        usernameToUserData.put(user.getUsername(),user);

        return this.makeRequest("POST", path, user, UserData.class,null);
    }





    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass, String authToken) throws Exception {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            if (authToken != null) {
                http.addRequestProperty("Authorization", authToken);
            }
            http.setRequestMethod(method);
            http.setDoOutput(true);
            if (Objects.equals(method, "GET")) {
                http.setDoOutput(false);
            }



            writeBody(request, http);
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