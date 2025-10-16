package com.cvs.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBConnector {
    private static final Logger logger = LoggerFactory.getLogger(DBConnector.class);
    private static Properties properties;
    private static boolean driverLoaded = false;

    static {
        loadProperties();
        loadJDBCDriver();
    }

    private static void loadProperties() {
        properties = new Properties();
        try (InputStream input = DBConnector.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                logger.error("Unable to find config.properties");
                throw new RuntimeException("config.properties not found");
            }
            properties.load(input);
            logger.info("Database configuration loaded successfully");
        } catch (IOException e) {
            logger.error("Error loading config.properties: {}", e.getMessage());
            throw new RuntimeException("Failed to load database configuration", e);
        }
    }

    private static void loadJDBCDriver() {
        try {
            String driverClass = properties.getProperty("db.driver");
            Class.forName(driverClass);
            driverLoaded = true;
            logger.info("JDBC Driver loaded successfully: {}", driverClass);
        } catch (ClassNotFoundException e) {
            logger.error("JDBC Driver not found: {}", e.getMessage());
            throw new RuntimeException("Failed to load JDBC driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        if (!driverLoaded) {
            throw new SQLException("JDBC Driver not loaded");
        }

        String url = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            logger.info("Database connection established successfully");
            return connection;
        } catch (SQLException e) {
            logger.error("Failed to establish database connection: {}", e.getMessage());
            throw e;
        }
    }

    public static boolean testConnection() {
        try (Connection connection = getConnection()) {
            boolean isValid = connection.isValid(5);
            if (isValid) {
                logger.info("Database connection test: SUCCESS");
                System.out.println("✅ Database connection test: SUCCESS");
            } else {
                logger.warn("Database connection test: FAILED - Invalid connection");
                System.out.println("❌ Database connection test: FAILED - Invalid connection");
            }
            return isValid;
        } catch (SQLException e) {
            logger.error("Database connection test: FAILED - {}", e.getMessage());
            System.out.println("❌ Database connection test: FAILED - " + e.getMessage());
            return false;
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                logger.debug("Database connection closed");
            } catch (SQLException e) {
                logger.error("Error closing database connection: {}", e.getMessage());
            }
        }
    }
}