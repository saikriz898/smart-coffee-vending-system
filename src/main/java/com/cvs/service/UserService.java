package com.cvs.service;

import com.cvs.dao.UserDAO;
import com.cvs.models.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public boolean registerUser(String name, String email, String password) {
        try {
            if (userDAO.getUserByEmail(email) != null) {
                logger.warn("Registration failed: Email already exists - {}", email);
                return false;
            }
            
            String hashedPassword = DigestUtils.sha256Hex(password);
            User user = new User(name, email, hashedPassword);
            user.setBalance(new BigDecimal("10.00")); // Welcome bonus
            
            boolean result = userDAO.createUser(user);
            if (result) {
                logger.info("User registered successfully: {}", email);
            }
            return result;
        } catch (Exception e) {
            logger.error("Error registering user: {}", e.getMessage());
            return false;
        }
    }

    public User authenticateUser(String email, String password) {
        try {
            User user = userDAO.getUserByEmail(email);
            if (user != null) {
                String hashedPassword = DigestUtils.sha256Hex(password);
                if (hashedPassword.equals(user.getPassword())) {
                    logger.info("User authenticated successfully: {}", email);
                    return user;
                }
            }
            logger.warn("Authentication failed for email: {}", email);
            return null;
        } catch (Exception e) {
            logger.error("Error authenticating user: {}", e.getMessage());
            return null;
        }
    }

    public boolean addBalance(int userId, BigDecimal amount) {
        try {
            User user = userDAO.getUserById(userId);
            if (user != null) {
                BigDecimal newBalance = user.getBalance().add(amount);
                return userDAO.updateUserBalance(userId, newBalance);
            }
            return false;
        } catch (Exception e) {
            logger.error("Error adding balance: {}", e.getMessage());
            return false;
        }
    }

    public boolean deductBalance(int userId, BigDecimal amount) {
        try {
            User user = userDAO.getUserById(userId);
            if (user != null && user.getBalance().compareTo(amount) >= 0) {
                BigDecimal newBalance = user.getBalance().subtract(amount);
                return userDAO.updateUserBalance(userId, newBalance);
            }
            return false;
        } catch (Exception e) {
            logger.error("Error deducting balance: {}", e.getMessage());
            return false;
        }
    }

    public User getUserById(int userId) {
        return userDAO.getUserById(userId);
    }
}