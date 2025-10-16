package com.cvs;

import com.cvs.utils.DBConnector;
import com.cvs.service.*;
import com.cvs.dao.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SystemValidationTest {
    
    @Test
    public void testDatabaseConnection() {
        assertDoesNotThrow(() -> DBConnector.getConnection());
    }
    
    @Test
    public void testCoreServices() {
        assertNotNull(new UserService());
        assertNotNull(new AdminService());
        assertNotNull(new OrderService());
    }
    
    @Test
    public void testDAOLayer() {
        assertNotNull(new UserDAO());
        assertNotNull(new AdminDAO());
        assertNotNull(new OrderDAO());
        assertNotNull(new CoffeeMenuDAO());
    }
    
    @Test
    public void testSystemIntegration() {
        UserService userService = new UserService();
        OrderService orderService = new OrderService();
        
        assertNotNull(userService);
        assertNotNull(orderService);
    }
}