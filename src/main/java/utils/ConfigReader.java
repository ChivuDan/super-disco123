package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties = new Properties();

    static {
        // Load the properties file from the classpath using the class loader.
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("Failed to load config.properties file. File not found in classpath.");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties file.", e);
        }
    }

    /**
     * Returns the property value for the given key.
     *
     * @param key the property key
     * @return the property value, or null if not found
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
