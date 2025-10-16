package com.cvs.dao;

import com.cvs.models.Order;
import com.cvs.models.OrderItem;
import com.cvs.utils.DBConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private static final Logger logger = LoggerFactory.getLogger(OrderDAO.class);

    public boolean createOrder(Order order) {
        String sql = "INSERT INTO orders (user_id, total_amount, payment_status, order_status) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        try {
            conn = DBConnector.getConnection();
            conn.setAutoCommit(false);
            
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, order.getUserId());
                stmt.setBigDecimal(2, order.getTotalAmount());
                stmt.setString(3, order.getPaymentStatus().name());
                stmt.setString(4, order.getOrderStatus().name());
                
                int result = stmt.executeUpdate();
                if (result > 0) {
                    ResultSet rs = stmt.getGeneratedKeys();
                    if (rs.next()) {
                        order.setOrderId(rs.getInt(1));
                        
                        // Insert order items if present
                        if (order.getOrderItems() != null && !order.getOrderItems().isEmpty()) {
                            OrderItemDAO orderItemDAO = new OrderItemDAO();
                            for (OrderItem item : order.getOrderItems()) {
                                item.setOrderId(order.getOrderId());
                                if (!orderItemDAO.createOrderItem(item, conn)) {
                                    conn.rollback();
                                    return false;
                                }
                            }
                        }
                        
                        conn.commit();
                        logger.info("Order created successfully: orderId={}", order.getOrderId());
                        return true;
                    }
                }
            }
            conn.rollback();
        } catch (SQLException e) {
            logger.error("Error creating order: {}", e.getMessage());
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { /* ignore */ }
            }
        } finally {
            if (conn != null) {
                try { 
                    conn.setAutoCommit(true);
                    conn.close(); 
                } catch (SQLException e) { /* ignore */ }
            }
        }
        return false;
    }

    public Order getOrderById(int orderId) {
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Order order = mapResultSetToOrder(rs);
                // Load order items
                OrderItemDAO orderItemDAO = new OrderItemDAO();
                order.setOrderItems(orderItemDAO.getOrderItemsByOrderId(orderId));
                return order;
            }
        } catch (SQLException e) {
            logger.error("Error getting order by ID: {}", e.getMessage());
        }
        return null;
    }

    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY order_time DESC";
        
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                orders.add(mapResultSetToOrder(rs));
            }
        } catch (SQLException e) {
            logger.error("Error getting orders by user ID: {}", e.getMessage());
        }
        return orders;
    }

    public boolean updateOrderStatus(int orderId, Order.OrderStatus status) {
        String sql = "UPDATE orders SET order_status = ? WHERE order_id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status.name());
            stmt.setInt(2, orderId);
            
            int result = stmt.executeUpdate();
            if (result > 0) {
                logger.info("Order status updated: orderId={}, status={}", orderId, status);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error updating order status: {}", e.getMessage());
        }
        return false;
    }

    public boolean updatePaymentStatus(int orderId, Order.PaymentStatus status) {
        String sql = "UPDATE orders SET payment_status = ? WHERE order_id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status.name());
            stmt.setInt(2, orderId);
            
            int result = stmt.executeUpdate();
            if (result > 0) {
                logger.info("Payment status updated: orderId={}, status={}", orderId, status);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error updating payment status: {}", e.getMessage());
        }
        return false;
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders ORDER BY order_time DESC";
        
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                orders.add(mapResultSetToOrder(rs));
            }
        } catch (SQLException e) {
            logger.error("Error getting all orders: {}", e.getMessage());
        }
        return orders;
    }

    private Order mapResultSetToOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setOrderId(rs.getInt("order_id"));
        order.setUserId(rs.getInt("user_id"));
        order.setTotalAmount(rs.getBigDecimal("total_amount"));
        order.setPaymentStatus(Order.PaymentStatus.valueOf(rs.getString("payment_status")));
        order.setOrderStatus(Order.OrderStatus.valueOf(rs.getString("order_status")));
        order.setOrderTime(rs.getTimestamp("order_time").toLocalDateTime());
        return order;
    }
}