package com.cvs.dao;

import com.cvs.models.CoffeeMenu;
import com.cvs.utils.DBConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoffeeMenuDAO {
    private static final Logger logger = LoggerFactory.getLogger(CoffeeMenuDAO.class);

    public boolean createCoffeeItem(CoffeeMenu coffee) {
        String sql = "INSERT INTO coffee_menu (name, price, description, available) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, coffee.getName());
            stmt.setBigDecimal(2, coffee.getPrice());
            stmt.setString(3, coffee.getDescription());
            stmt.setBoolean(4, coffee.isAvailable());
            
            int result = stmt.executeUpdate();
            if (result > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    coffee.setCoffeeId(rs.getInt(1));
                }
                logger.info("Coffee item created: {}", coffee.getName());
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error creating coffee item: {}", e.getMessage());
        }
        return false;
    }

    public CoffeeMenu getCoffeeById(int coffeeId) {
        String sql = "SELECT * FROM coffee_menu WHERE coffee_id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, coffeeId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToCoffee(rs);
            }
        } catch (SQLException e) {
            logger.error("Error getting coffee by ID: {}", e.getMessage());
        }
        return null;
    }

    public List<CoffeeMenu> getAllCoffeeItems() {
        List<CoffeeMenu> coffeeItems = new ArrayList<>();
        String sql = "SELECT * FROM coffee_menu ORDER BY name";
        
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                coffeeItems.add(mapResultSetToCoffee(rs));
            }
        } catch (SQLException e) {
            logger.error("Error getting all coffee items: {}", e.getMessage());
        }
        return coffeeItems;
    }

    public List<CoffeeMenu> getAvailableCoffeeItems() {
        List<CoffeeMenu> coffeeItems = new ArrayList<>();
        String sql = "SELECT * FROM coffee_menu WHERE available = true ORDER BY name";
        
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                coffeeItems.add(mapResultSetToCoffee(rs));
            }
        } catch (SQLException e) {
            logger.error("Error getting available coffee items: {}", e.getMessage());
        }
        return coffeeItems;
    }

    public boolean updateCoffeeItem(CoffeeMenu coffee) {
        String sql = "UPDATE coffee_menu SET name = ?, price = ?, description = ?, available = ? WHERE coffee_id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, coffee.getName());
            stmt.setBigDecimal(2, coffee.getPrice());
            stmt.setString(3, coffee.getDescription());
            stmt.setBoolean(4, coffee.isAvailable());
            stmt.setInt(5, coffee.getCoffeeId());
            
            int result = stmt.executeUpdate();
            if (result > 0) {
                logger.info("Coffee item updated: {}", coffee.getName());
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error updating coffee item: {}", e.getMessage());
        }
        return false;
    }

    public boolean updateAvailability(int coffeeId, boolean available) {
        String sql = "UPDATE coffee_menu SET available = ? WHERE coffee_id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setBoolean(1, available);
            stmt.setInt(2, coffeeId);
            
            int result = stmt.executeUpdate();
            if (result > 0) {
                logger.info("Coffee availability updated: coffeeId={}, available={}", coffeeId, available);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error updating coffee availability: {}", e.getMessage());
        }
        return false;
    }

    public boolean deleteCoffeeItem(int coffeeId) {
        String sql = "DELETE FROM coffee_menu WHERE coffee_id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, coffeeId);
            int result = stmt.executeUpdate();
            
            if (result > 0) {
                logger.info("Coffee item deleted: coffeeId={}", coffeeId);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error deleting coffee item: {}", e.getMessage());
        }
        return false;
    }

    private CoffeeMenu mapResultSetToCoffee(ResultSet rs) throws SQLException {
        CoffeeMenu coffee = new CoffeeMenu();
        coffee.setCoffeeId(rs.getInt("coffee_id"));
        coffee.setName(rs.getString("name"));
        coffee.setPrice(rs.getBigDecimal("price"));
        coffee.setDescription(rs.getString("description"));
        coffee.setAvailable(rs.getBoolean("available"));
        coffee.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return coffee;
    }
}