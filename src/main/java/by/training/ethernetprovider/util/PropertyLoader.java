package by.training.ethernetprovider.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

public final class PropertyLoader {
    private static final PropertyLoader INSTANCE = new PropertyLoader();

    public static PropertyLoader getInstance(){
        return INSTANCE;
    }

    public Properties load(String path){
        Properties properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemResourceAsStream(path));
        } catch (IOException e) {
        }
        return properties;
    }

}
