package dataAccess;
import model.AuthData;
import java.util.*;

public class MemoryAuthDAO implements AuthDAO {
    private Vector<AuthData> allAuthInfo;
    public MemoryAuthDAO() {
        this.allAuthInfo =new Vector<>();
    }
    public void addAuth(AuthData authToken) {
        this.allAuthInfo.add(authToken);
    }
    public void clearAllAuth() {
        allAuthInfo.clear();
    }
    public boolean isEmpty() {
        return allAuthInfo.isEmpty();
    }
    public String getUsername(String auth) {
        for (int i = 0; i < this.allAuthInfo.size(); ++i) {
            if (Objects.equals(this.allAuthInfo.get(i).getAuthString(), auth)) {
                return this.allAuthInfo.get(i).getUsername();
            }
        }
        return null;
    }
    public void removeAuth(String auth) {
        for (int i = 0; i < allAuthInfo.size(); ++i) {
            if (Objects.equals(allAuthInfo.get(i).getAuthString(), auth)) {
                //loops thru Auth, when we are at the auth it removes it
                allAuthInfo.remove(allAuthInfo.get(i));
            }
        }
    }
    public boolean hasUsername(String auth) throws DataAccessException {
        for (AuthData authData : allAuthInfo) {
            if (Objects.equals(authData.getAuthString(), auth)) {
                return true;
            }
        }
        throw new DataAccessException("401");
    }
}
