package dataAccess;
import com.google.gson.Gson;
import model.UserData;

import javax.xml.crypto.Data;
import java.sql.*;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class MySQLUserDAO implements UserDAO {
    public MySQLUserDAO() throws DataAccessException {
        configureDatabase();
    }


    public void registerUser(UserData user) throws DataAccessException {
        var statement = "INSERT INTO user (username, password, email) VALUES (?, ?, ?)";
        var json = new Gson().toJson(user);
        var id = executeUpdate(statement, user.getUsername(), user.getPassword(), user.getEmail());
    }

    @Override
    public void isValid(UserData user) throws DataAccessException {
        //is username exists we can't allow it throw exception, this is to test if we can register
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT COUNT(username) FROM user WHERE username = ?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, user.getUsername());
                try (var rs = ps.executeQuery()) {
                    //rs.size should be 0 cuz there should be no query
                    while(rs.next()) {
                        //this means there alerady is user with that username
                        throw new DataAccessException("403");
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException("403");
        }
    }

    @Override
    public boolean testLogin(UserData user) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT id FROM user WHERE username = ? AND password = ?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1,user.getUsername());
                ps.setString(2, user.getPassword());
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        try (var conn = DatabaseManager.getConnection()){
            try (var preparedStatement = conn.prepareStatement("SELECT * FROM user"))
            {
                var rs = preparedStatement.executeQuery();
                while(rs.next()) {
                    //if we get here there is something in the query
                    return false;
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public int getSize() throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()){
            try (var preparedStatement = conn.prepareStatement("SELECT COUNT(id) FROM user"))
            {
                var rs = preparedStatement.executeQuery();
                rs.next();
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void validUsername(String username) throws DataAccessException {
        //we wanna make sure this username isn't already taking before we register it
        try (var conn = DatabaseManager.getConnection()){
            try (var preparedStatement = conn.prepareStatement("SELECT COUNT(username) FROM user WHERE username = ?;"))
            {
                preparedStatement.setString(1, username);
                var rs = preparedStatement.executeQuery();
                rs.next();
                if(rs.getInt(1) == 1)
                {
                    //user already added
                    throw new DataAccessException("403");
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void logout(UserData user) throws Exception {
        var statement = "DELETE FROM user WHERE username = ?";
        executeUpdate(statement, user.getUsername());
    }

    public void clearAllUsers() {
        var statement="TRUNCATE user";
        try {
            executeUpdate(statement);
        } catch(DataAccessException ex) {
            return;
        }
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
            CREATE TABLE IF NOT EXISTS user (
              `id` int NOT NULL AUTO_INCREMENT,
              `username` varchar(256) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` varchar(256) NOT NULL,
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
