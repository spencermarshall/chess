package dataAccess;

import chess.ChessBoard;
import chess.ChessGame;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.GameData;
import model.UserData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Vector;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class MySQLGameDAO implements GameDAO{
    public MySQLGameDAO() throws DataAccessException {
        configureDatabase();
    }
    @Override
    public boolean isEmpty() throws DataAccessException {
        var result = new ArrayList<GameData>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM game";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    int count = 0;
                    while(rs.next()) {
                        count++;
                    }
                    //count will increases for every item inside, so if count = 0, there are no items inside, so it'll
                    //return true for yes it is empty since 0 items are inside
                    return count == 0;
                }
            }
        } catch (DataAccessException | SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void addGame(GameData game) throws DataAccessException {
        //need to get count of games to change gameID
        int count = 0;
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT COUNT(gameID) FROM game";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while(rs.next()) {
                        count = rs.getInt(1);
                    }
                }
            }
        } catch (DataAccessException | SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        //this is because we want gameID to start at 1
        count++;




        var statement = "INSERT INTO game (whiteUsername, blackUsername, gameName, myGame) VALUES (?, ?, ?, ?)";
        game.setGameID(count);
        var json = new Gson().toJson(game);
        var id = executeUpdate(statement,game.getWhiteUsername(), game.getBlackUsername(), game.getGameName(), json);
    }

    @Override
    public Collection<GameData> returnAllGames() throws DataAccessException {
        var result = new ArrayList<GameData>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM game";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while(rs.next()) {
                        int gameID = rs.getInt(1);
                        String whiteUsername = rs.getString(2);
                        String blackUsername = rs.getString(3);
                        String disGame = rs.getString(5);

                        ChessGame outputGame = new ChessGame();
                        GameData thisGame = new GameData();

                        //this is a chess game object we add to the GameData type
                        var chessGameObj = new Gson().fromJson(disGame, ChessGame.class);

                        thisGame.setGameID(gameID);
                        thisGame.setWhiteUsername(whiteUsername);
                        thisGame.setBlackUsername(blackUsername);
                        result.add(thisGame);
                        //make game into json and add it...?
                      //  JsonObject stringGame = new JsonObject()
                    }
                }
            }
        } catch (DataAccessException | SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        return result;
    }

    @Override
    public GameData getGame(int id) throws DataAccessException {
        GameData retGame = new GameData();
        boolean noGameFound = true;
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM game WHERE gameID = ?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1,id);
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        noGameFound = false;
                        int gameID = rs.getInt(1);
                        String whiteUsername = rs.getString(2);
                        String blackUsername = rs.getString(3);
                        String myGame = rs.getString(5);
                        ChessGame gameObj = new Gson().fromJson(myGame, ChessGame.class);
                        retGame.setMyGame(gameObj);
                        retGame.setBlackUsername(blackUsername);
                        retGame.setWhiteUsername(whiteUsername);
                        retGame.setGameID(gameID);
                    }
                }
            }
            if (noGameFound) {
                //can't return crap b/c we didn't find a game with that id
                throw new DataAccessException("400");
            }
        } catch (DataAccessException | SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        return retGame;
    }
    public void clearAllGames() {
        var statement="TRUNCATE game";
        try {
            executeUpdate(statement);
        } catch(DataAccessException ex) {
            return;
        }
    }

    @Override
    public void setColor(int id, String color, String username) throws DataAccessException {
        //if it already has a color just return; and exit;
        var statement1 = "";
        if (Objects.equals(color, "BLACK")){
            statement1 = "SELECT blackUsername FROM game WHERE gameID = ?";
        }
        else if (Objects.equals(color, "WHITE")) {
            statement1 = "SELECT whiteUsername FROM game WHERE gameID = ?";
        }
        // we want it to be empty
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement1)) {
                ps.setInt(1,id);
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String name = rs.getString(1);
                        if (!Objects.equals(name, null)) {
                            //name is not "" so it is already taken
                            return;
                        }

                    }
                }
            }
        } catch (DataAccessException | SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        // we will only get here if it's empty






        var statement = "";
        if (Objects.equals(color, "BLACK")){
            statement = "UPDATE game SET blackUsername = ? WHERE gameID = ?";
        }
        else if (Objects.equals(color, "WHITE")) {
            statement = "UPDATE game SET whiteUsername = ? WHERE gameID = ?";
        }
        else if (Objects.equals(color, "") || Objects.equals(color, "EMPTY")) {
            //we are just spectating so we don't need to update anything
            return;
        }
        executeUpdate(statement, username, id);
    }
    private int executeUpdate(String statement, Object... params) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (var i = 0; i < params.length; i++) {
                    var param=params[i];
                    if (param instanceof String p) ps.setString(i + 1, p);
                    else if (param instanceof Integer p) ps.setInt(i + 1, p);
                    else if (param instanceof UserData p) ps.setString(i + 1, p.toString());
                    else if (param == null) ps.setNull(i + 1, NULL);
                }

                ps.executeUpdate();



                var rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }

                return 0;
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS game (
              `gameID` int NOT NULL AUTO_INCREMENT,
              `whiteUsername` varchar(256),
              `blackUsername` varchar(256),
              `gameName` varchar(256),
              `myGame` blob NOT NULL,
              PRIMARY KEY (`gameID`),
              INDEX(gameID)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
            //  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
    };


    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

}
