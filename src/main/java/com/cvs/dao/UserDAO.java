package com.cvs.dao;

import com.cvs.models.User;
import com.cvs.utils.DBConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

    public boolean createUser(User user) {
        String sql = "INSERT INTO users (name, email, password, balance) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setBigDecimal(4, user.getBalance());
            
            int result = stmt.executeUpdate();
            if (result > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    user.setUserId(rs.getInt(1));
                }
                logger.info("User created successfully: {}", user.getEmail());
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error creating user: {}", e.getMessage());
        }
        return false;
    }

    public User getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            logger.error("Error getting user by ID: {}", e.getMessage());
        }
        return null;
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            logger.error("Error getting user by email: {}", e.getMessage());
        }
        return null;
    }

    public boolean updateUserBalance(int userId, BigDecimal newBalance) {
        String sql = "UPDATE users SET balance = ? WHERE user_id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setBigDecimal(1, newBalance);
            stmt.setInt(2, userId);
            
            int result = stmt.executeUpdate();
            if (result > 0) {
                logger.info("User balance updated: userId={}, newBalance={}", userId, newBalance);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error updating user balance: {}", e.getMessage());
        }
        return false;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY created_at DESC";
        
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            logger.error("Error getting all users: {}", e.getMessage());
        }
        return users;
    }

    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            int result = stmt.executeUpdate();
            
            if (result > 0) {
                logger.info("User deleted: userId={}", userId);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error deleting user: {}", e.getMessage());
        }
        return false;
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setBalance(rs.getBigDecimal("balance"));
        user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        user.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return user;
    }
}