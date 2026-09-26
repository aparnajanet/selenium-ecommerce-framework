package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;

    // A static block runs exactly once when the class is loaded into memory.
    // This prevents the framework from reading the file from the hard drive during every single test.
    static {
        try {
            // Path to the properties file
            String filePath = "src/test/resources/config.properties";
            FileInputStream fileInputStream = new FileInputStream(filePath);

            properties = new Properties();
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            // "Fail fast" - if the file is missing, stop the entire test suite immediately
            throw new RuntimeException("Failed to load config.properties file! " + e.getMessage());
        }
    }

    /**
     * Fetches the value for a given key from the properties file.
     * @param key the property key (e.g., "baseUrl")
     * @return the property value
     */
    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value != null) {
            return value;
        } else {
            throw new RuntimeException("Property '" + key + "' is not specified in the config.properties file.");
        }
    }
}