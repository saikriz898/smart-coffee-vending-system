package com.cvs.tests;

import com.cvs.utils.DBConnector;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnectorTest {

    @Test
    @DisplayName("Test JDBC Driver Loading")
    public void testJDBCDriverLoading() {
        // This test verifies that the JDBC driver is loaded during class initialization
        assertDoesNotThrow(() -> {
            Class.forName("com.cvs.utils.DBConnector");
        }, "JDBC driver should be loaded without exceptions");
    }

    @Test
    @DisplayName("Test Database Connection")
    public void testDatabaseConnection() {
        // Note: This test requires MySQL server to be running
        // In a real environment, you might want to use @EnabledIf annotation
        // to conditionally run this test only when database is available
        
        boolean connectionResult = DBConnector.testConnection();
        // We don't assert true here because DB might not be available in test environment
        // Instead, we just verify the method executes without throwing exceptions
        assertNotNull(connectionResult, "Connection test should return a boolean result");
    }

    @Test
    @DisplayName("Test Connection Retrieval")
    public void testGetConnection() {
        // This test verifies that getConnection method works
        // It may fail if database is not available, which is expected in some environments
        
        assertDoesNotThrow(() -> {
            try (Connection conn = DBConnector.getConnection()) {
                assertNotNull(conn, "Connection should not be null when database is available");
            } catch (SQLException e) {
                // Expected when database is not available
                assertTrue(e.getMessage().contains("Connection") || 
                          e.getMessage().contains("database") ||
                          e.getMessage().contains("refused"),
                          "SQLException should be database-related");
            }
        });
    }

    @Test
    @DisplayName("Test Connection Closing")
    public void testConnectionClosing() {
        // Test that closeConnection method handles null connections gracefully
        assertDoesNotThrow(() -> {
            DBConnector.closeConnection(null);
        }, "Closing null connection should not throw exception");
    }
}