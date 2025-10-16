package com.cvs.dao;

import com.cvs.models.OrderItem;
import com.cvs.utils.DBConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO {
    private static final Logger logger = LoggerFactory.getLogger(OrderItemDAO.class);

    public boolean createOrderItem(OrderItem orderItem) {
        try (Connection conn = DBConnector.getConnection()) {
            return createOrderItem(orderItem, conn);
        } catch (SQLException e) {
            logger.error("Error creating order item: {}", e.getMessage());
            return false;
        }
    }

    public boolean createOrderItem(OrderItem orderItem, Connection conn) {
        String sql = "INSERT INTO order_items (order_id, coffee_id, quantity, sugar_level, milk_level, size, item_price) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, orderItem.getOrderId());
            stmt.setInt(2, orderItem.getCoffeeId());
            stmt.setInt(3, orderItem.getQuantity());
            stmt.setString(4, orderItem.getSugarLevel().name());
            stmt.setString(5, orderItem.getMilkLevel().name());
            stmt.setString(6, orderItem.getSize().name());
            stmt.setBigDecimal(7, orderItem.getItemPrice());
            
            int result = stmt.executeUpdate();
            if (result > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    orderItem.setOrderItemId(rs.getInt(1));
                }
                logger.debug("Order item created: orderItemId={}", orderItem.getOrderItemId());
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error creating order item: {}", e.getMessage());
        }
        return false;
    }

    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        List<OrderItem> orderItems = new ArrayList<>();
        String sql = "SELECT oi.*, cm.name as coffee_name " +
                     "FROM order_items oi " +
                     "JOIN coffee_menu cm ON oi.coffee_id = cm.coffee_id " +
                     "WHERE oi.order_id = ?";
        
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                orderItems.add(mapResultSetToOrderItem(rs));
            }
        } catch (SQLException e) {
            logger.error("Error getting order items by order ID: {}", e.getMessage());
        }
        return orderItems;
    }

    public OrderItem getOrderItemById(int orderItemId) {
        String sql = "SELECT oi.*, cm.name as coffee_name " +
                     "FROM order_items oi " +
                     "JOIN coffee_menu cm ON oi.coffee_id = cm.coffee_id " +
                     "WHERE oi.order_item_id = ?";
        
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, orderItemId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToOrderItem(rs);
            }
        } catch (SQLException e) {
            logger.error("Error getting order item by ID: {}", e.getMessage());
        }
        return null;
    }

    public boolean updateOrderItem(OrderItem orderItem) {
        String sql = "UPDATE order_items SET quantity = ?, sugar_level = ?, milk_level = ?, size = ?, item_price = ? WHERE order_item_id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, orderItem.getQuantity());
            stmt.setString(2, orderItem.getSugarLevel().name());
            stmt.setString(3, orderItem.getMilkLevel().name());
            stmt.setString(4, orderItem.getSize().name());
            stmt.setBigDecimal(5, orderItem.getItemPrice());
            stmt.setInt(6, orderItem.getOrderItemId());
            
            int result = stmt.executeUpdate();
            if (result > 0) {
                logger.info("Order item updated: orderItemId={}", orderItem.getOrderItemId());
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error updating order item: {}", e.getMessage());
        }
        return false;
    }

    public boolean deleteOrderItem(int orderItemId) {
        String sql = "DELETE FROM order_items WHERE order_item_id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, orderItemId);
            int result = stmt.executeUpdate();
            
            if (result > 0) {
                logger.info("Order item deleted: orderItemId={}", orderItemId);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error deleting order item: {}", e.getMessage());
        }
        return false;
    }

    private OrderItem mapResultSetToOrderItem(ResultSet rs) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderItemId(rs.getInt("order_item_id"));
        orderItem.setOrderId(rs.getInt("order_id"));
        orderItem.setCoffeeId(rs.getInt("coffee_id"));
        orderItem.setQuantity(rs.getInt("quantity"));
        orderItem.setSugarLevel(OrderItem.SugarLevel.valueOf(rs.getString("sugar_level")));
        orderItem.setMilkLevel(OrderItem.MilkLevel.valueOf(rs.getString("milk_level")));
        orderItem.setSize(OrderItem.Size.valueOf(rs.getString("size")));
        orderItem.setItemPrice(rs.getBigDecimal("item_price"));
        orderItem.setCoffeeName(rs.getString("coffee_name"));
        return orderItem;
    }
}