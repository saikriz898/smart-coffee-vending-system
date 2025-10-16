# ☕ Java Coffee Vending System

A complete enterprise-grade Coffee Vending System built with Java Swing, MySQL, and Maven. Features modern GUI, secure authentication, real-time inventory management, and comprehensive admin dashboard.

## 🚀 Features

### 👤 User Features
- **Secure Authentication** - Registration/login with password hashing
- **Interactive Menu** - Browse coffee options with real-time pricing
- **Smart Ordering** - Add to cart, customize orders, place orders
- **Digital Wallet** - Add money, track balance, transaction history
- **Order Tracking** - Real-time order status and receipt generation

### 🔧 Admin Features
- **Inventory Management** - Add/edit/delete coffee items, stock tracking
- **User Management** - View all users, monitor activities
- **Order Management** - Track all orders, generate receipts
- **Analytics Dashboard** - Daily/monthly reports, sales analytics
- **System Monitoring** - Database health, performance metrics

### 🎨 Technical Features
- **Modern GUI** - Professional Swing interface with gradients
- **Database Integration** - MySQL with JDBC, transaction management
- **Security** - SQL injection prevention, secure authentication
- **Performance** - Optimized queries, connection pooling
- **Testing** - Comprehensive unit tests, system validation

## 🛠️ Tech Stack

- **Java 11** - Core language
- **Swing** - GUI framework
- **MySQL 8.0** - Database
- **Maven** - Build tool
- **JUnit 5** - Testing
- **SLF4J** - Logging

## ⚡ Quick Start

### Prerequisites
- Java 11+
- MySQL 8.0+
- Maven 3.6+

### Setup
1. **Clone repository**
2. **Setup database:**
   ```sql
   CREATE DATABASE coffee_vending_system;
   ```
3. **Configure database** in `src/main/resources/config.properties`
4. **Run application:**
   ```bash
   mvn clean package
   ./start-gui.bat
   ```

## 📊 System Architecture

```
├── GUI Layer (Swing)
├── Service Layer (Business Logic)
├── DAO Layer (Database Access)
└── MySQL Database
```

## 🧪 Testing

```bash
# Run all tests
mvn test

# System validation
./system-validation.bat

# Performance testing included
```

## 📱 Screenshots

- Modern login interface
- Interactive coffee menu
- Real-time admin dashboard
- Comprehensive reporting system

## 🏆 Project Highlights

- **Complete MVC Architecture**
- **Enterprise-grade Security**
- **Real-time Data Updates**
- **Comprehensive Testing Suite**
- **Production-ready Deployment**
- **Professional Documentation**

## 📄 Documentation

- [User Manual](USER_MANUAL.md)
- [Admin Guide](ADMIN_GUIDE.md)
- [System Requirements](SRS_Document.md)
- [Deployment Guide](PRODUCTION_DEPLOYMENT.md)

## 🤝 Contributing

This is an educational project demonstrating enterprise Java development practices.

## 📧 Contact

Built as part of Software Engineering coursework - demonstrating full-stack Java development with modern practices.