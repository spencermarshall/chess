package dataAccessTests;

import dataAccess.DataAccessException;
import org.junit.jupiter.api.*;
import passoffTests.testClasses.TestException;
import server.Server;

public class DataAccessTests {


    private static Server server;



    @BeforeAll //this function is copied from the starterCode on github
    public static void init() throws DataAccessException {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);



    }
    @AfterAll  //this function is copied from the starterCode on github
    public static void stop()
    {
        server.stop();
    }
    @BeforeEach //this function is copied from the starterCode on github
    public void setup() throws TestException, DataAccessException {

    }


}
