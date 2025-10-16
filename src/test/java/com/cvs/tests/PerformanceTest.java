package com.cvs.tests;

import com.cvs.dao.*;
import com.cvs.models.*;
import com.cvs.service.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;

public class PerformanceTest {
    
    private UserDAO userDAO;
    private CoffeeMenuDAO coffeeMenuDAO;
    private OrderDAO orderDAO;
    private ReportService reportService;
    
    @BeforeEach
    public void setUp() {
        userDAO = new UserDAO();
        coffeeMenuDAO = new CoffeeMenuDAO();
        orderDAO = new OrderDAO();
        reportService = new ReportService();
    }
    
    @Test
    @DisplayName("Performance Test: Database Query Response Time")
    public void testDatabaseQueryPerformance() {
        assertDoesNotThrow(() -> {
            // Test user query performance
            Instant start = Instant.now();
            userDAO.getAllUsers();
            Instant end = Instant.now();
            Duration userQueryTime = Duration.between(start, end);
            
            // Test menu query performance
            start = Instant.now();
            coffeeMenuDAO.getAllCoffeeItems();
            end = Instant.now();
            Duration menuQueryTime = Duration.between(start, end);
            
            // Test order query performance
            start = Instant.now();
            orderDAO.getAllOrders();
            end = Instant.now();
            Duration orderQueryTime = Duration.between(start, end);
            
            // Performance assertions (< 500ms requirement)
            assertTrue(userQueryTime.toMillis() < 500, 
                String.format("User query took %dms, should be < 500ms", userQueryTime.toMillis()));
            assertTrue(menuQueryTime.toMillis() < 500, 
                String.format("Menu query took %dms, should be < 500ms", menuQueryTime.toMillis()));
            assertTrue(orderQueryTime.toMillis() < 500, 
                String.format("Order query took %dms, should be < 500ms", orderQueryTime.toMillis()));
            
            System.out.printf("✅ Performance Test Results:%n");
            System.out.printf("   User Query: %dms%n", userQueryTime.toMillis());
            System.out.printf("   Menu Query: %dms%n", menuQueryTime.toMillis());
            System.out.printf("   Order Query: %dms%n", orderQueryTime.toMillis());
        });
    }
    
    @Test
    @DisplayName("Performance Test: Report Generation Time")
    public void testReportGenerationPerformance() {
        assertDoesNotThrow(() -> {
            // Test daily report generation time
            Instant start = Instant.now();
            String dailyReport = reportService.generateDailyReport();
            Instant end = Instant.now();
            Duration dailyReportTime = Duration.between(start, end);
            
            // Test monthly report generation time
            start = Instant.now();
            String monthlyReport = reportService.generateMonthlyReport();
            end = Instant.now();
            Duration monthlyReportTime = Duration.between(start, end);
            
            // Performance assertions (< 5 seconds for reports)
            assertTrue(dailyReportTime.toMillis() < 5000, 
                String.format("Daily report took %dms, should be < 5000ms", dailyReportTime.toMillis()));
            assertTrue(monthlyReportTime.toMillis() < 5000, 
                String.format("Monthly report took %dms, should be < 5000ms", monthlyReportTime.toMillis()));
            
            System.out.printf("✅ Report Performance Results:%n");
            System.out.printf("   Daily Report: %dms%n", dailyReportTime.toMillis());
            System.out.printf("   Monthly Report: %dms%n", monthlyReportTime.toMillis());
        });
    }
    
    @Test
    @DisplayName("Performance Test: Memory Usage")
    public void testMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        
        // Get initial memory usage
        long initialMemory = runtime.totalMemory() - runtime.freeMemory();
        
        // Perform memory-intensive operations
        assertDoesNotThrow(() -> {
            for (int i = 0; i < 100; i++) {
                userDAO.getAllUsers();
                coffeeMenuDAO.getAllCoffeeItems();
                orderDAO.getAllOrders();
            }
        });
        
        // Force garbage collection
        System.gc();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Get final memory usage
        long finalMemory = runtime.totalMemory() - runtime.freeMemory();
        long memoryIncrease = finalMemory - initialMemory;
        
        // Memory should not increase significantly (< 50MB)
        assertTrue(memoryIncrease < 50 * 1024 * 1024, 
            String.format("Memory increase: %d bytes, should be < 50MB", memoryIncrease));
        
        System.out.printf("✅ Memory Usage Test:%n");
        System.out.printf("   Initial: %d KB%n", initialMemory / 1024);
        System.out.printf("   Final: %d KB%n", finalMemory / 1024);
        System.out.printf("   Increase: %d KB%n", memoryIncrease / 1024);
    }
    
    @Test
    @DisplayName("Performance Test: Concurrent Operations")
    public void testConcurrentOperations() {
        assertDoesNotThrow(() -> {
            // Test multiple simultaneous database operations
            Thread[] threads = new Thread[5];
            
            for (int i = 0; i < 5; i++) {
                final int threadId = i;
                threads[i] = new Thread(() -> {
                    try {
                        // Simulate concurrent user operations
                        userDAO.getAllUsers();
                        coffeeMenuDAO.getAvailableCoffeeItems();
                        orderDAO.getAllOrders();
                        
                        System.out.printf("Thread %d completed operations%n", threadId);
                    } catch (Exception e) {
                        System.err.printf("Thread %d failed: %s%n", threadId, e.getMessage());
                    }
                });
            }
            
            // Start all threads
            Instant start = Instant.now();
            for (Thread thread : threads) {
                thread.start();
            }
            
            // Wait for all threads to complete
            for (Thread thread : threads) {
                try {
                    thread.join(2000); // 2 second timeout
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            Instant end = Instant.now();
            
            Duration totalTime = Duration.between(start, end);
            assertTrue(totalTime.toMillis() < 3000, 
                String.format("Concurrent operations took %dms, should be < 3000ms", totalTime.toMillis()));
            
            System.out.printf("✅ Concurrent Operations: %dms%n", totalTime.toMillis());
        });
    }
}