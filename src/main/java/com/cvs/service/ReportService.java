package com.cvs.service;

import com.cvs.dao.OrderDAO;
import com.cvs.dao.UserDAO;
import com.cvs.models.Order;
import com.cvs.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportService {
    private static final Logger logger = LoggerFactory.getLogger(ReportService.class);
    private final OrderDAO orderDAO;
    private final UserDAO userDAO;

    public ReportService() {
        this.orderDAO = new OrderDAO();
        this.userDAO = new UserDAO();
    }

    public String generateDailyReport() {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime startOfDay = today.withHour(0).withMinute(0).withSecond(0);
        
        List<Order> allOrders = orderDAO.getAllOrders();
        List<Order> todayOrders = allOrders.stream()
            .filter(order -> order.getOrderTime().isAfter(startOfDay))
            .collect(Collectors.toList());

        return generateReportContent("Daily Report", todayOrders, today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    public String generateWeeklyReport() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime weekStart = now.minusDays(7);
        
        List<Order> allOrders = orderDAO.getAllOrders();
        List<Order> weekOrders = allOrders.stream()
            .filter(order -> order.getOrderTime().isAfter(weekStart))
            .collect(Collectors.toList());

        return generateReportContent("Weekly Report", weekOrders, "Last 7 days");
    }

    public String generateMonthlyReport() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime monthStart = now.minusDays(30);
        
        List<Order> allOrders = orderDAO.getAllOrders();
        List<Order> monthOrders = allOrders.stream()
            .filter(order -> order.getOrderTime().isAfter(monthStart))
            .collect(Collectors.toList());

        return generateReportContent("Monthly Report", monthOrders, "Last 30 days");
    }

    private String generateReportContent(String reportType, List<Order> orders, String period) {
        StringBuilder report = new StringBuilder();
        
        // Header
        report.append("=".repeat(50)).append("\n");
        report.append(String.format("☕ COFFEE VENDING SYSTEM - %s\n", reportType.toUpperCase()));
        report.append(String.format("Period: %s\n", period));
        report.append(String.format("Generated: %s\n", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        report.append("=".repeat(50)).append("\n\n");

        // Summary Statistics
        int totalOrders = orders.size();
        long completedOrders = orders.stream()
            .filter(order -> order.getPaymentStatus() == Order.PaymentStatus.COMPLETED)
            .count();
        
        BigDecimal totalRevenue = orders.stream()
            .filter(order -> order.getPaymentStatus() == Order.PaymentStatus.COMPLETED)
            .map(Order::getTotalAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        report.append("📊 SUMMARY STATISTICS\n");
        report.append("-".repeat(30)).append("\n");
        report.append(String.format("Total Orders: %d\n", totalOrders));
        report.append(String.format("Completed Orders: %d\n", completedOrders));
        report.append(String.format("Success Rate: %.1f%%\n", totalOrders > 0 ? (completedOrders * 100.0 / totalOrders) : 0));
        report.append(String.format("Total Revenue: $%.2f\n", totalRevenue));
        report.append(String.format("Average Order Value: $%.2f\n", completedOrders > 0 ? totalRevenue.divide(BigDecimal.valueOf(completedOrders), 2, BigDecimal.ROUND_HALF_UP) : BigDecimal.ZERO));
        report.append("\n");

        // Order Status Breakdown
        Map<Order.OrderStatus, Long> statusCounts = orders.stream()
            .collect(Collectors.groupingBy(Order::getOrderStatus, Collectors.counting()));

        report.append("📋 ORDER STATUS BREAKDOWN\n");
        report.append("-".repeat(30)).append("\n");
        for (Order.OrderStatus status : Order.OrderStatus.values()) {
            long count = statusCounts.getOrDefault(status, 0L);
            report.append(String.format("%-12s: %d\n", status, count));
        }
        report.append("\n");

        // Payment Status Breakdown
        Map<Order.PaymentStatus, Long> paymentCounts = orders.stream()
            .collect(Collectors.groupingBy(Order::getPaymentStatus, Collectors.counting()));

        report.append("💳 PAYMENT STATUS BREAKDOWN\n");
        report.append("-".repeat(30)).append("\n");
        for (Order.PaymentStatus status : Order.PaymentStatus.values()) {
            long count = paymentCounts.getOrDefault(status, 0L);
            report.append(String.format("%-12s: %d\n", status, count));
        }
        report.append("\n");

        // Recent Orders (last 10)
        report.append("🕒 RECENT ORDERS\n");
        report.append("-".repeat(30)).append("\n");
        orders.stream()
            .sorted((o1, o2) -> o2.getOrderTime().compareTo(o1.getOrderTime()))
            .limit(10)
            .forEach(order -> {
                report.append(String.format("Order #%d - $%.2f - %s - %s\n",
                    order.getOrderId(),
                    order.getTotalAmount(),
                    order.getOrderStatus(),
                    order.getOrderTime().format(DateTimeFormatter.ofPattern("MM-dd HH:mm"))));
            });

        report.append("\n");
        report.append("=".repeat(50)).append("\n");
        report.append("Report End\n");

        logger.info("Generated {} for period: {}", reportType, period);
        return report.toString();
    }

    public String generateUserStatistics() {
        List<User> users = userDAO.getAllUsers();
        List<Order> orders = orderDAO.getAllOrders();

        StringBuilder report = new StringBuilder();
        report.append("👥 USER STATISTICS\n");
        report.append("-".repeat(30)).append("\n");
        report.append(String.format("Total Users: %d\n", users.size()));
        
        BigDecimal totalUserBalance = users.stream()
            .map(User::getBalance)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        report.append(String.format("Total User Balance: $%.2f\n", totalUserBalance));
        report.append(String.format("Average Balance: $%.2f\n", 
            users.size() > 0 ? totalUserBalance.divide(BigDecimal.valueOf(users.size()), 2, BigDecimal.ROUND_HALF_UP) : BigDecimal.ZERO));

        // Top users by order count
        Map<Integer, Long> userOrderCounts = orders.stream()
            .collect(Collectors.groupingBy(Order::getUserId, Collectors.counting()));

        report.append("\n🏆 TOP CUSTOMERS\n");
        report.append("-".repeat(20)).append("\n");
        userOrderCounts.entrySet().stream()
            .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
            .limit(5)
            .forEach(entry -> {
                User user = userDAO.getUserById(entry.getKey());
                if (user != null) {
                    report.append(String.format("%-20s: %d orders\n", user.getName(), entry.getValue()));
                }
            });

        return report.toString();
    }
}