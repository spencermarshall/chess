package dataAccess;

import model.AuthData;

public class MySQLAuthDAO implements AuthDAO{
    @Override
    public void clearAllAuth() {

    }

    @Override
    public void addAuth(AuthData authToken) {

    }

    @Override
    public boolean hasUsername(String auth) throws DataAccessException {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void removeAuth(String auth) {

    }

    @Override
    public String getUsername(String auth) {
        return null;
    }
}
