package com.cvs.service;

import com.cvs.dao.CoffeeMenuDAO;
import com.cvs.dao.OrderDAO;
import com.cvs.dao.UserDAO;
import com.cvs.models.CoffeeMenu;
import com.cvs.models.Order;
import com.cvs.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

public class AdminService {
    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);
    private final CoffeeMenuDAO coffeeMenuDAO;
    private final OrderDAO orderDAO;
    private final UserDAO userDAO;

    public AdminService() {
        this.coffeeMenuDAO = new CoffeeMenuDAO();
        this.orderDAO = new OrderDAO();
        this.userDAO = new UserDAO();
    }

    public boolean authenticateAdmin(String username, String password) {
        // Simple admin authentication (in production, use proper hashing)
        return ("admin".equals(username) && "admin123".equals(password)) ||
               ("manager".equals(username) && "manager123".equals(password));
    }

    public List<CoffeeMenu> getAllCoffeeItems() {
        return coffeeMenuDAO.getAllCoffeeItems();
    }

    public boolean addCoffeeItem(String name, BigDecimal price, String description) {
        try {
            CoffeeMenu coffee = new CoffeeMenu(name, price, description);
            boolean result = coffeeMenuDAO.createCoffeeItem(coffee);
            if (result) {
                logger.info("Coffee item added: {}", name);
            }
            return result;
        } catch (Exception e) {
            logger.error("Error adding coffee item: {}", e.getMessage());
            return false;
        }
    }

    public boolean updateCoffeeItem(CoffeeMenu coffee) {
        try {
            boolean result = coffeeMenuDAO.updateCoffeeItem(coffee);
            if (result) {
                logger.info("Coffee item updated: {}", coffee.getName());
            }
            return result;
        } catch (Exception e) {
            logger.error("Error updating coffee item: {}", e.getMessage());
            return false;
        }
    }

    public boolean toggleCoffeeAvailability(int coffeeId, boolean available) {
        try {
            boolean result = coffeeMenuDAO.updateAvailability(coffeeId, available);
            if (result) {
                logger.info("Coffee availability updated: coffeeId={}, available={}", coffeeId, available);
            }
            return result;
        } catch (Exception e) {
            logger.error("Error updating coffee availability: {}", e.getMessage());
            return false;
        }
    }

    public List<Order> getAllOrders() {
        return orderDAO.getAllOrders();
    }

    public boolean updateOrderStatus(int orderId, Order.OrderStatus status) {
        try {
            boolean result = orderDAO.updateOrderStatus(orderId, status);
            if (result) {
                logger.info("Order status updated: orderId={}, status={}", orderId, status);
            }
            return result;
        } catch (Exception e) {
            logger.error("Error updating order status: {}", e.getMessage());
            return false;
        }
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public int getTotalOrders() {
        return getAllOrders().size();
    }

    public BigDecimal getTotalRevenue() {
        return getAllOrders().stream()
                .filter(order -> order.getPaymentStatus() == Order.PaymentStatus.COMPLETED)
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}