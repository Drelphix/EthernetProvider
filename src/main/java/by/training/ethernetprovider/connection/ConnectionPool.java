package by.training.ethernetprovider.connection;

import by.training.ethernetprovider.exception.ConnectionPoolException;
import by.training.ethernetprovider.exception.DatabaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    public ProxyConnection getConnection()  {
        ProxyConnection connection = null;
        try {
            if (connectionPool.isEmpty()) {
                addConnectionsToPool(1);
            }
            connection = connectionPool.take();
            usedConnections.put(connection);
        } catch (InterruptedException e) {
            LOGGER.error("There is an error in thread.", e);
            Thread.currentThread().interrupt();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Can't get  connection from pool.", e);
        }
        return connection;
    }

    public boolean releaseConnection(ProxyConnection connection) {
        try {
            if (getPoolSize() < DEFAULT_POOL_SIZE) {
                connectionPool.put(connection);
            }
            return usedConnections.remove(connection);
        } catch (InterruptedException e) {
            LOGGER.error("There is an error in thread.", e);
            Thread.currentThread().interrupt();
        }
        return false;
    }

    public void addConnectionsToPool(int count) throws ConnectionPoolException {
        try {
                if(getPoolSize() + count <= MAX_CONNECTIONS) {
                    ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
                    for (int i = 0; i < count; i++) {
                        connectionPool.put(connectionFactory.getConnection());
                    }
                }
        } catch (DatabaseException e) {
            throw new ConnectionPoolException("Can't get new connection to database.", e);
        } catch (InterruptedException e) {
            LOGGER.error("Can't add new connection to pool", e);
            Thread.currentThread().interrupt();
        }
    }

    public int getPoolSize() {
        return usedConnections.size() + connectionPool.size();
    }
}
