package client;
import com.google.gson.Gson;
//import exception.ResponseException;
import model.*;


import java.io.*;
import java.net.*;

public class ServerFacade {

    private final String serverUrl;

    public ServerFacade(String url) {
        serverUrl = url;
    }

    //i made this lol
    public void login(UserData user) throws Exception {
        var path = "/login";
        this.makeRequest("GET", path, user, UserData.class);
    }
    public void logout() throws Exception {
        var path = "/logout";
        UserData user = new UserData(); //todo this is WRONG, i just have it so it won't give errors
        this.makeRequest("DELETE", path, user, UserData.class);
    }
    public void createGame(GameData game) throws Exception {
        var path = "/game";
        this.makeRequest("POST", path, game, GameData.class);
    }








    public UserData addUser(UserData user) throws Exception {
        var path = "/add";
        return this.makeRequest("POST", path, user, UserData.class);
    }

    public void deletePet(int id) throws Exception {
        var path = String.format("/pet/%s", id);
        this.makeRequest("DELETE", path, null, null);
    }

    public void deleteAllPets() throws Exception {
        var path = "/pet";
        this.makeRequest("DELETE", path, null, null);
    }

    ///todo void is temporary, it should return the games i think...?
    public void listGames() throws Exception {
        var path = "/pet";
      //  record listPetResponse(Pet[] pet) {
       // }
        var response = this.makeRequest("GET", path, null, ChessClient.class); ///todo idk if this last paramter is correct
        //return response.user();
    }

    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass) throws Exception {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

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