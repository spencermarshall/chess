package dataAccess;
import model.AuthData;
import java.util.*;

public class MemoryAuthDAO implements AuthDAO {
    private Vector<AuthData> allAuthInfo;
    public MemoryAuthDAO()
    {
        this.allAuthInfo = new Vector<AuthData>();
    }
    public void addAuth(AuthData authToken)
    {
        this.allAuthInfo.add(authToken);
    }
    public void clearAllAuth()
    {
        allAuthInfo.clear();
    }
}
