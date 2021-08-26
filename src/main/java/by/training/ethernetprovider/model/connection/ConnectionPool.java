package by.training.ethernetprovider.model.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int DEFAULT_POOL_SIZE = 4;
    private BlockingQueue<ProxyConnection> connectionPool;
    private BlockingQueue<ProxyConnection> usedConnections;

    private static class ConnectionPoolHolder {
        static final ConnectionPool INSTANCE = new ConnectionPool();
    }

    private ConnectionPool() {
        connectionPool = new LinkedBlockingQueue<>(DEFAULT_POOL_SIZE);
        usedConnections = new LinkedBlockingQueue<>();
        try {
            ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
            for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
                Connection connection = connectionFactory.getConnection();
                connectionPool.put(new ProxyConnection(connection));
            }
        } catch (InterruptedException e) {
            LOGGER.error("Can't add new connection to pool.", e);
            throw new RuntimeException("Can't add new connection to pool.", e);
        } catch (SQLException e){
            LOGGER.error("Can't get new connection", e);
            throw new RuntimeException("Can't get new connection", e);
        }
    }

    public static ConnectionPool getInstance() {
        return ConnectionPoolHolder.INSTANCE;
    }

    public Connection getConnection()  {
        ProxyConnection connection = null;
        try {
            connection = connectionPool.take();
            usedConnections.put(connection);
        } catch (InterruptedException e) {
            LOGGER.error("There is an error in thread.", e);
            Thread.currentThread().interrupt();
        }

        return connection;
    }

    public boolean releaseConnection(ProxyConnection connection) {
        try {
            connectionPool.put(connection);
            return usedConnections.remove(connection);
        } catch (InterruptedException e) {
            LOGGER.error("There is an error in thread.", e);
            Thread.currentThread().interrupt();
        }
        return false;
    }


    public void destroyConnectionPool() {
            connectionPool.parallelStream().forEach(connection -> {
                try {
                    connection.closeReally();
                } catch (SQLException e) {
                    LOGGER.error("Can't close connection from connection pool", e);
                }
            });
            usedConnections.parallelStream().forEach(connection -> {
                try {
                    connection.closeReally();
                } catch (SQLException e) {
                    LOGGER.error("Can't close connection from used connection pool", e);
                }
            });
            unregisterDrivers();
    }

    private void unregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                LOGGER.error("Can't unregister drivers", e);
            }
        });
    }
}
