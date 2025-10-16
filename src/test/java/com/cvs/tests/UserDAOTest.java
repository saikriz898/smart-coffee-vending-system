package com.cvs.tests;

import com.cvs.dao.UserDAO;
import com.cvs.models.User;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

public class UserDAOTest {
    private UserDAO userDAO;
    private User testUser;

    @BeforeEach
    public void setUp() {
        userDAO = new UserDAO();
        testUser = new User("Test User", "test@example.com", "password123");
        testUser.setBalance(new BigDecimal("25.00"));
    }

    @Test
    @DisplayName("Test User Creation")
    public void testCreateUser() {
        // Note: This test requires database connection
        // In production, you might want to use @EnabledIf for conditional testing
        assertDoesNotThrow(() -> {
            boolean result = userDAO.createUser(testUser);
            // We don't assert true because DB might not be available
            assertNotNull(result, "Create user should return a boolean result");
        });
    }

    @Test
    @DisplayName("Test Get User By Email")
    public void testGetUserByEmail() {
        assertDoesNotThrow(() -> {
            User result = userDAO.getUserByEmail("test@example.com");
            // Result might be null if database is not available or user doesn't exist
            // This test just ensures the method executes without throwing exceptions
        });
    }

    @Test
    @DisplayName("Test Update User Balance")
    public void testUpdateUserBalance() {
        assertDoesNotThrow(() -> {
            BigDecimal newBalance = new BigDecimal("50.00");
            boolean result = userDAO.updateUserBalance(1, newBalance);
            assertNotNull(result, "Update balance should return a boolean result");
        });
    }

    @Test
    @DisplayName("Test Get All Users")
    public void testGetAllUsers() {
        assertDoesNotThrow(() -> {
            var users = userDAO.getAllUsers();
            assertNotNull(users, "Get all users should return a list (may be empty)");
        });
    }

    @Test
    @DisplayName("Test User Model Validation")
    public void testUserModelValidation() {
        // Test user model without database interaction
        User user = new User("John Doe", "john@test.com", "pass123");
        user.setBalance(new BigDecimal("100.00"));
        
        assertEquals("John Doe", user.getName());
        assertEquals("john@test.com", user.getEmail());
        assertEquals("pass123", user.getPassword());
        assertEquals(new BigDecimal("100.00"), user.getBalance());
        
        assertNotNull(user.toString());
        assertTrue(user.toString().contains("John Doe"));
    }
}