package dataAccess;

import com.google.gson.Gson;
import model.AuthData;
import model.UserData;

import java.sql.SQLException;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class MySQLAuthDAO implements AuthDAO{
    public MySQLAuthDAO() throws DataAccessException {
        configureDatabase();
    }
    @Override
    public void clearAllAuth() throws DataAccessException {
        var statement="TRUNCATE auth";
        try {
            executeUpdate(statement);
        } catch(DataAccessException ex) {
            return;
        }
    }

    @Override
    public void addAuth(AuthData authToken) throws DataAccessException {
        var statement = "INSERT INTO auth (username, authToken) VALUES (?, ?)";
        var json = new Gson().toJson(authToken);
        var id = executeUpdate(statement, authToken.getUsername(), authToken.getAuthString());
    }

    public boolean hasUsername(String auth) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()){
            try (var preparedStatement = conn.prepareStatement("SELECT username FROM auth WHERE authToken = ?"))
            {
                preparedStatement.setString(1,auth);
                try(var rs = preparedStatement.executeQuery()) {
                    rs.next();
                    String username = rs.getString(1);
                    return true;
                }


            }
        } catch (SQLException | DataAccessException e) {
            return false;
        }

    }

    @Override
    public boolean isEmpty() {
        try (var conn = DatabaseManager.getConnection()){
            try (var preparedStatement = conn.prepareStatement("SELECT * FROM auth"))
            {
                var rs = preparedStatement.executeQuery();
                while(rs.next()) {
                    return false;
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public void removeAuth(String auth) throws DataAccessException {
        var statement = "DELETE FROM auth WHERE authToken = ?";
        executeUpdate(statement, auth);

    }

    @Override
    public String getUsername(String auth) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()){
            try (var preparedStatement = conn.prepareStatement("SELECT username FROM auth WHERE authToken = ?"))
            {
                preparedStatement.setString(1,auth);
                try (var rs = preparedStatement.executeQuery()) {
                    rs.next();
                    String username = rs.getString("username");
                    return username;
                }

            }
        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException("401");
        }
    }



    private int executeUpdate(String statement, Object... params) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    if (param instanceof String p) ps.setString(i + 1, p);
                    else if (param instanceof Integer p) ps.setInt(i + 1, p);
                    ///todo this might be incorrect below
                    else if (param instanceof AuthData p) ps.setString(i + 1, p.toString());
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
            CREATE TABLE IF NOT EXISTS auth (
              `id` int NOT NULL AUTO_INCREMENT,
              `authToken` varchar(256) NOT NULL,
              `username` varchar(256) NOT NULL,
              PRIMARY KEY (`id`),
              INDEX(id)
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
