package dataAccess;

import model.GameData;
import model.UserData;

import java.sql.SQLException;
import java.util.Vector;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class MySQLGameDAO implements GameDAO{
    public MySQLGameDAO() throws DataAccessException {
        configureDatabase();
    }
    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void addGame(GameData game) {

    }

    @Override
    public Vector<GameData> returnAllGames() {
        return null;
    }

    @Override
    public GameData getGame(int id) throws DataAccessException {
        return null;
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
    public void setColor(int id, String color, String username) {

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
              `whiteUsername` varchar(256) NOT NULL,
              `blackUsername` varchar(256) NOT NULL,
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
