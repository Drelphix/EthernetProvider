package by.training.ethernetprovider.connection;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


class ConnectionFactory {
    private static final Logger LOGGER = LogManager.getLogger();
    private static String URL;
    private static final String RESOURCE = "properties/database.properties";
    private static final Properties databaseProperties = new Properties();

    private static class ConnectionFactoryHandler{
        private static final ConnectionFactory instance = new ConnectionFactory();
    }

    protected static ConnectionFactory getInstance() {
        return ConnectionFactoryHandler.instance;
    }

    private ConnectionFactory ()  {
        String driver = null;
        try(InputStream inputStream = ConnectionFactory.class.getClassLoader().getResourceAsStream(RESOURCE)){
            databaseProperties.load(inputStream);
            driver = databaseProperties.getProperty("driver");
            Class.forName(driver);
            URL = databaseProperties.getProperty("url");
        } catch (IOException e){
            LOGGER.fatal("Can't find database properties file at: " + RESOURCE);
            throw new RuntimeException("Can't find database properties file at: "+RESOURCE, e);
        } catch (ClassNotFoundException e){
            LOGGER.fatal("Can't load database driver class: " + driver);
            throw new RuntimeException("Can't load database driver class. ", e);
        }
    }

    protected Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL,databaseProperties);
    }
}
