package by.training.ethernetprovider.connection;

import by.training.ethernetprovider.exception.ConnectionPoolException;
import by.training.ethernetprovider.exception.DatabaseException;
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
    private static final int MAX_CONNECTIONS = 151;
    private BlockingQueue<ProxyConnection> connectionPool;
    private BlockingQueue<ProxyConnection> usedConnections;

    private static class ConnectionPoolHolder {
        static final ConnectionPool INSTANCE = new ConnectionPool();
    }

    private ConnectionPool() {
        connectionPool = new LinkedBlockingQueue<>(DEFAULT_POOL_SIZE);
        usedConnections = new LinkedBlockingQueue<>();
        try {
            addConnectionsToPool(DEFAULT_POOL_SIZE);
        } catch (ConnectionPoolException e) {
            LOGGER.error("Can't add new connection to pool.", e);
        }
    }

    public static ConnectionPool getInstance() {
        return ConnectionPoolHolder.INSTANCE;
    }

    public Connection getConnection()  {
        Connection connection = null;
        try {
            if (connectionPool.isEmpty()) {
                addConnectionsToPool(1);
            }
            connection = connectionPool.take();
            usedConnections.put((ProxyConnection)connection);
        } catch (InterruptedException e) {
            LOGGER.error("There is an error in thread.", e);
            Thread.currentThread().interrupt();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Can't get  connection from pool.", e);
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        try {
            if (getPoolSize() < DEFAULT_POOL_SIZE) {
                connectionPool.put((ProxyConnection)connection);
            }
            usedConnections.remove(connection);
            connection.close();
        } catch (InterruptedException e) {
            LOGGER.error("There is an error in thread.", e);
            Thread.currentThread().interrupt();
        } catch (SQLException e){
            LOGGER.error("Can't close connection", e);
        }
    }

    public void addConnectionsToPool(int count) throws ConnectionPoolException {
        try {
                if(getPoolSize() + count <= MAX_CONNECTIONS) {
                    ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
                    for (int i = 0; i < count; i++) {
                        Connection connection = connectionFactory.getConnection();
                        connectionPool.put(new ProxyConnection(connection));
                    }
                }
        } catch (DatabaseException e) {
            throw new ConnectionPoolException("Can't get new connection to database.", e);
        } catch (InterruptedException e) {
            LOGGER.error("Can't add new connection to pool", e);
            Thread.currentThread().interrupt();
        }
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

    public int getPoolSize() {
        return usedConnections.size() + connectionPool.size();
    }

    private void unregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                LOGGER.error("Can't unregister drivers ", e);
            }
        });
    }
}
