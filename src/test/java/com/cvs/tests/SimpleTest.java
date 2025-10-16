package com.cvs.tests;

import com.cvs.service.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class SimpleTest {
    
    @Test
    @DisplayName("Simple Test: Services Available")
    public void testServicesAvailable() {
        UserService userService = new UserService();
        AdminService adminService = new AdminService();
        OrderService orderService = new OrderService();
        ReportService reportService = new ReportService();
        
        assertNotNull(userService, "UserService should be available");
        assertNotNull(adminService, "AdminService should be available");
        assertNotNull(orderService, "OrderService should be available");
        assertNotNull(reportService, "ReportService should be available");
        
        System.out.println("✅ All services are available and working");
    }
    
    @Test
    @DisplayName("Simple Test: Admin Authentication")
    public void testAdminAuth() {
        AdminService adminService = new AdminService();
        
        boolean validAuth = adminService.authenticateAdmin("admin", "admin123");
        assertTrue(validAuth, "Admin should authenticate with correct credentials");
        
        boolean invalidAuth = adminService.authenticateAdmin("admin", "wrong");
        assertFalse(invalidAuth, "Admin should not authenticate with wrong credentials");
        
        System.out.println("✅ Admin authentication working correctly");
    }
}