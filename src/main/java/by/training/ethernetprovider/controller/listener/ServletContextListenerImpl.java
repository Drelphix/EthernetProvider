package by.training.ethernetprovider.controller.listener;

import by.training.ethernetprovider.model.connection.ConnectionPool;
import jakarta.servlet.ServletContextEvent;

public class ServletContextListenerImpl implements jakarta.servlet.ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConnectionPool.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce){
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        connectionPool.destroyConnectionPool();
    }
}
