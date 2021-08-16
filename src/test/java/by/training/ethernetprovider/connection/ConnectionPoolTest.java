package by.training.ethernetprovider.connection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConnectionPoolTest {
    @Test
    public void getConnectionTest(){
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        System.out.println(connectionPool.getPoolSize());
        Assertions.assertEquals(4, connectionPool.getPoolSize());
    }
}
