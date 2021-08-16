package by.training.ethernetprovider.connection;


import by.training.ethernetprovider.exception.DatabaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static final Logger LOGGER = LogManager.getLogger();
    private static String URL;
    private static final String RESOURCE = "properties/database.properties";
    private static final Properties databaseProperties = new Properties();
    private static ConnectionFactory instance;

    public static ConnectionFactory getInstance() throws DatabaseException {
        if(instance == null){
            instance = new ConnectionFactory();
        }
        return instance;
    }

    public ConnectionFactory () throws DatabaseException {
        String driver = null;
        try(InputStream inputStream = ConnectionFactory.class.getClassLoader().getResourceAsStream(RESOURCE)){
            databaseProperties.load(inputStream);
            driver = databaseProperties.getProperty("driver");
            Class.forName(driver);
            URL = databaseProperties.getProperty("url");
        } catch (IOException e){
            LOGGER.fatal("Can't find database properties file at: "+RESOURCE);
            throw new DatabaseException("Can't find database properties file at: "+RESOURCE, e);
        } catch (ClassNotFoundException e){
            LOGGER.fatal("Can't load database driver class: " + driver);
            throw new DatabaseException("Can't load database driver class. ", e);
        }
    }

    public  ProxyConnection getConnection() throws DatabaseException {
        try {
            Connection connection = DriverManager.getConnection(URL,databaseProperties);
            return new ProxyConnection(connection);
        } catch (SQLException e) {
            throw new DatabaseException("Can't get connection. Url: " + URL + ", properties: " + databaseProperties);
        }

    }
}
