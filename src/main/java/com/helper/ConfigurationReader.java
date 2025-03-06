package com.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationReader {
    
    private static final Properties properties = new Properties();
    private static volatile ConfigurationReader instance;

    ConfigurationReader() {
        try {
            loadProperties();
        } catch (IOException e) {
            throw new RuntimeException("Configuration loading failed", e);
        }
    }

    public static ConfigurationReader getInstance() {
        if (instance == null) {
            synchronized (ConfigurationReader.class) {
                if (instance == null) {
                    instance = new ConfigurationReader();
                }
            }
        }
        return instance;
    }

    private void loadProperties() throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (inputStream == null) {
                throw new IOException("Properties file not found in classpath!");
            }
            properties.load(inputStream);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getUrl() {
        return getProperty("dev");
    }

    public String getEmail() {
        return getProperty("email");
    }

    public String getPassword() {
        return getProperty("password");
    }

    public String getBrowser() {
        return getProperty("browser");
    }

    public Boolean isHeadless() {
        String headless = getProperty("headless");
        return headless != null ? Boolean.parseBoolean(headless) : null;
    }
}
