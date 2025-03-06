package com.helper;

public class FileReaderManager {

    // Bill Pugh Singleton - Thread-safe, efficient, and lazy-loaded
    private static class Holder {
        private static final FileReaderManager INSTANCE = new FileReaderManager();
        private static final ConfigurationReader CONFIGURATION_READER = new ConfigurationReader();
    }

    // Private Constructor to prevent external instantiation
    private FileReaderManager() {
        // No action needed
    }

    // Static Method to get the Singleton instance of FileReaderManager
    public static FileReaderManager getInstance() {
        return Holder.INSTANCE;
    }

    // Thread-safe and efficient ConfigurationReader instance
    public ConfigurationReader getConfigurationReader() {
        return Holder.CONFIGURATION_READER;
    }
}