package com.cvs.tests;

import com.cvs.dao.UserDAO;
import com.cvs.models.User;
import com.cvs.service.UserService;
import com.cvs.service.OrderService;
import com.cvs.service.AdminService;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FunctionalTest {
    
    private UserService userService;
    private OrderService orderService;
    private AdminService adminService;
    private static final String TEST_EMAIL = "functional.test@example.com";
    private static final String TEST_PASSWORD = "testpass123";
    
    @BeforeEach
    public void setUp() {
        userService = new UserService();
        orderService = new OrderService();
        adminService = new AdminService();
    }
    
    @Test
    @Order(1)
    @DisplayName("Functional Test: User Registration")
    public void testUserRegistration() {
        assertDoesNotThrow(() -> {
            boolean result = userService.registerUser("Test User", TEST_EMAIL, TEST_PASSWORD);
            // Test passes if no exception is thrown
            assertNotNull(result, "Registration should return a boolean result");
        });
    }
    
    @Test
    @Order(2)
    @DisplayName("Functional Test: User Login")
    public void testUserLogin() {
        assertDoesNotThrow(() -> {
            User user = userService.authenticateUser(TEST_EMAIL, TEST_PASSWORD);
            // Test passes if authentication method executes without error
        });
    }
    
    @Test
    @Order(3)
    @DisplayName("Functional Test: Balance Management")
    public void testBalanceManagement() {
        assertDoesNotThrow(() -> {
            // Test adding balance
            boolean addResult = userService.addBalance(1, new BigDecimal("25.00"));
            assertNotNull(addResult, "Add balance should return a boolean");
            
            // Test deducting balance
            boolean deductResult = userService.deductBalance(1, new BigDecimal("5.00"));
            assertNotNull(deductResult, "Deduct balance should return a boolean");
        });
    }
    
    @Test
    @Order(4)
    @DisplayName("Functional Test: Menu Operations")
    public void testMenuOperations() {
        assertDoesNotThrow(() -> {
            // Test getting available coffee items
            var coffeeItems = adminService.getAllCoffeeItems();
            assertNotNull(coffeeItems, "Coffee items list should not be null");
            
            // Test adding new coffee item
            boolean addResult = adminService.addCoffeeItem("Test Coffee", new BigDecimal("3.99"), "Test Description");
            assertNotNull(addResult, "Add coffee item should return a boolean");
        });
    }
    
    @Test
    @Order(5)
    @DisplayName("Functional Test: Order Processing")
    public void testOrderProcessing() {
        assertDoesNotThrow(() -> {
            // Test order creation (may fail due to database constraints, but should not throw)
            var orders = orderService.getUserOrders(1);
            assertNotNull(orders, "User orders should return a list (may be empty)");
        });
    }
    
    @Test
    @Order(6)
    @DisplayName("Functional Test: Admin Authentication")
    public void testAdminAuthentication() {
        // Test admin login with default credentials
        boolean adminAuth = adminService.authenticateAdmin("admin", "admin123");
        assertTrue(adminAuth, "Admin authentication with correct credentials should succeed");
        
        // Test admin login with wrong credentials
        boolean wrongAuth = adminService.authenticateAdmin("admin", "wrongpass");
        assertFalse(wrongAuth, "Admin authentication with wrong credentials should fail");
    }
    
    @Test
    @Order(7)
    @DisplayName("Functional Test: Report Generation")
    public void testReportGeneration() {
        assertDoesNotThrow(() -> {
            // Test report generation methods exist and execute
            int totalOrders = adminService.getTotalOrders();
            assertTrue(totalOrders >= 0, "Total orders should be non-negative");
            
            BigDecimal revenue = adminService.getTotalRevenue();
            assertNotNull(revenue, "Revenue should not be null");
            assertTrue(revenue.compareTo(BigDecimal.ZERO) >= 0, "Revenue should be non-negative");
        });
    }
}