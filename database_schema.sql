-- Coffee Vending System Database Schema
-- Phase 1 - Complete database structure with relationships

CREATE DATABASE IF NOT EXISTS coffee_vending_system;
USE coffee_vending_system;

-- Users table
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    balance DECIMAL(10,2) DEFAULT 0.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Admin table
CREATE TABLE admin (
    admin_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Coffee menu table
CREATE TABLE coffee_menu (
    coffee_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(8,2) NOT NULL,
    description TEXT,
    available BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Ingredients table
CREATE TABLE ingredients (
    ingredient_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    unit VARCHAR(20) DEFAULT 'grams',
    min_threshold INT DEFAULT 10,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Orders table
CREATE TABLE orders (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    payment_status ENUM('PENDING', 'COMPLETED', 'FAILED', 'REFUNDED') DEFAULT 'PENDING',
    order_status ENUM('PLACED', 'PREPARING', 'READY', 'DELIVERED', 'CANCELLED') DEFAULT 'PLACED',
    order_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Order items table
CREATE TABLE order_items (
    order_item_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    coffee_id INT NOT NULL,
    quantity INT DEFAULT 1,
    sugar_level ENUM('NO_SUGAR', 'LOW', 'MEDIUM', 'HIGH') DEFAULT 'MEDIUM',
    milk_level ENUM('NO_MILK', 'LOW', 'MEDIUM', 'HIGH') DEFAULT 'MEDIUM',
    size ENUM('SMALL', 'MEDIUM', 'LARGE') DEFAULT 'MEDIUM',
    item_price DECIMAL(8,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (coffee_id) REFERENCES coffee_menu(coffee_id)
);

-- Payments table
CREATE TABLE payments (
    payment_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_type ENUM('CASH', 'CARD', 'WALLET', 'UPI') NOT NULL,
    payment_status ENUM('SUCCESS', 'FAILED', 'PENDING') DEFAULT 'PENDING',
    transaction_id VARCHAR(100),
    payment_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE
);

-- Insert sample data
INSERT INTO admin (username, password) VALUES 
('admin', 'admin123'),
('manager', 'manager123');

INSERT INTO coffee_menu (name, price, description) VALUES 
('Espresso', 2.50, 'Strong black coffee'),
('Cappuccino', 3.50, 'Espresso with steamed milk foam'),
('Latte', 4.00, 'Espresso with steamed milk'),
('Americano', 3.00, 'Espresso with hot water'),
('Mocha', 4.50, 'Chocolate flavored coffee');

INSERT INTO ingredients (name, quantity, unit, min_threshold) VALUES 
('Coffee Beans', 1000, 'grams', 100),
('Milk', 2000, 'ml', 200),
('Sugar', 500, 'grams', 50),
('Chocolate', 300, 'grams', 30),
('Water', 5000, 'ml', 500);

INSERT INTO users (name, email, password, balance) VALUES 
('John Doe', 'john@example.com', 'password123', 50.00),
('Jane Smith', 'jane@example.com', 'password123', 75.00);