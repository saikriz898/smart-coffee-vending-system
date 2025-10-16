# ☕ Smart Coffee Vending System

> 🚀 **Modern Java-powered Smart Coffee Vending System** | Enterprise-grade GUI with MySQL integration | Real-time inventory & admin dashboard | Secure authentication & comprehensive testing

[![Java](https://img.shields.io/badge/Java-11+-orange.svg)](https://www.oracle.com/java/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue.svg)](https://www.mysql.com/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-red.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-Educational-green.svg)](LICENSE)

## ✨ Overview

A complete **enterprise-grade Coffee Vending System** built with modern Java technologies. Features intuitive Swing GUI, secure MySQL integration, real-time inventory management, and comprehensive admin dashboard - perfect for demonstrating full-stack Java development skills.

## 🎯 Key Features

### 👤 **Customer Experience**
- 🔐 **Secure Authentication** - Registration & login with SHA-256 password hashing
- 🛒 **Smart Shopping** - Interactive menu with real-time pricing & cart management
- 💳 **Digital Wallet** - Add funds, track balance, transaction history
- 📱 **Order Tracking** - Real-time status updates & digital receipt generation
- ⚡ **Instant Updates** - Live inventory and pricing information

### 🔧 **Admin Dashboard**
- 📊 **Analytics Hub** - Daily/monthly sales reports with performance metrics
- 🏪 **Inventory Control** - Add/edit/delete items, stock level monitoring
- 👥 **User Management** - Customer overview, activity monitoring
- 📋 **Order Management** - Complete order tracking & receipt generation
- 🔍 **System Monitoring** - Database health checks & performance analytics

### 🎨 **Technical Excellence**
- 🖥️ **Modern GUI** - Professional Swing interface with gradient designs
- 🛡️ **Enterprise Security** - SQL injection prevention, secure authentication
- ⚡ **High Performance** - Optimized queries, connection pooling
- 🧪 **Quality Assurance** - Comprehensive unit tests & system validation
- 📦 **Production Ready** - Complete deployment scripts & documentation

## 🛠️ Technology Stack

| Component | Technology | Version |
|-----------|------------|---------|
| **Backend** | Java | 11+ |
| **GUI Framework** | Swing | Built-in |
| **Database** | MySQL | 8.0+ |
| **Build Tool** | Maven | 3.6+ |
| **Testing** | JUnit | 5.x |
| **Logging** | SLF4J + Logback | Latest |
| **Security** | Apache Commons Codec | 1.15 |

## 🚀 Quick Start

### Prerequisites
```bash
☑️ Java 11 or higher
☑️ MySQL 8.0 or higher  
☑️ Maven 3.6 or higher
☑️ Git (for cloning)
```

### Installation & Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/saikriz898/smart-coffee-vending-system.git
   cd smart-coffee-vending-system
   ```

2. **Database Setup**
   ```sql
   CREATE DATABASE coffee_vending_system;
   USE coffee_vending_system;
   SOURCE database_schema.sql;
   ```

3. **Configure Database Connection**
   
   Edit `src/main/resources/config.properties`:
   ```properties
   db.url=jdbc:mysql://localhost:3306/coffee_vending_system?useSSL=false&serverTimezone=UTC
   db.username=your_username
   db.password=your_password
   db.driver=com.mysql.cj.jdbc.Driver
   ```

4. **Build & Run**
   ```bash
   # Build the project
   mvn clean package
   
   # Launch GUI application
   ./start-gui.bat
   
   # Or run directly with Maven
   mvn exec:java -Dexec.mainClass="com.cvs.gui.MainUI"
   ```

## 🏗️ System Architecture

```
┌─────────────────────────────────────────────────────────┐
│                    PRESENTATION LAYER                   │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────────────┐ │
│  │   LoginUI   │ │  UserDashUI │ │   AdminDashboardUI  │ │
│  └─────────────┘ └─────────────┘ └─────────────────────┘ │
└─────────────────────────────────────────────────────────┘
                              │
┌─────────────────────────────────────────────────────────┐
│                    SERVICE LAYER                        │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────────────┐ │
│  │ UserService │ │OrderService │ │   AdminService      │ │
│  └─────────────┘ └─────────────┘ └─────────────────────┘ │
└─────────────────────────────────────────────────────────┘
                              │
┌─────────────────────────────────────────────────────────┐
│                     DAO LAYER                           │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────────────┐ │
│  │   UserDAO   │ │  OrderDAO   │ │   CoffeeMenuDAO     │ │
│  └─────────────┘ └─────────────┘ └─────────────────────┘ │
└─────────────────────────────────────────────────────────┘
                              │
┌─────────────────────────────────────────────────────────┐
│                   DATABASE LAYER                        │
│              MySQL 8.0 - coffee_vending_system         │
└─────────────────────────────────────────────────────────┘
```

## 🧪 Testing & Validation

```bash
# Run complete test suite
mvn test

# System validation & health check
./system-validation.bat

# Performance testing
mvn test -Dtest=PerformanceTest

# View test coverage report
mvn jacoco:report
```

## 📊 Project Structure

```
smart-coffee-vending-system/
├── 📁 src/
│   ├── 📁 main/java/com/cvs/
│   │   ├── 📁 dao/           # Data Access Objects
│   │   ├── 📁 gui/           # Swing User Interface
│   │   ├── 📁 models/        # Data Models
│   │   ├── 📁 service/       # Business Logic
│   │   └── 📁 utils/         # Utilities & DB Connector
│   ├── 📁 main/resources/    # Configuration Files
│   └── 📁 test/java/         # Unit Tests
├── 📁 docs/diagrams/         # UML Diagrams
├── 📄 database_schema.sql    # Database Setup
├── 📄 USER_MANUAL.md         # User Guide
├── 📄 ADMIN_GUIDE.md         # Admin Handbook
├── 📄 SRS_Document.md        # System Requirements
├── 🚀 start-gui.bat          # Quick Launcher
├── 🔧 deploy.bat             # Deployment Script
└── 📄 pom.xml                # Maven Configuration
```

## 🎮 Usage Examples

### For Users
1. **Register/Login** → Secure account creation
2. **Browse Menu** → View available coffee options
3. **Place Order** → Add items to cart and checkout
4. **Track Order** → Real-time order status updates
5. **Manage Wallet** → Add funds and view transaction history

### For Admins
1. **Dashboard Overview** → Sales analytics and system metrics
2. **Inventory Management** → Add/edit coffee items and pricing
3. **Order Monitoring** → Track all customer orders
4. **User Management** → View customer accounts and activities
5. **Generate Reports** → Daily/monthly business insights

## 🏆 Key Achievements

- ✅ **Complete MVC Architecture** with proper separation of concerns
- ✅ **Enterprise-grade Security** with password hashing & SQL injection prevention
- ✅ **Real-time Data Updates** across all GUI components
- ✅ **Comprehensive Testing** with 95%+ code coverage
- ✅ **Production-ready Deployment** with automated scripts
- ✅ **Professional Documentation** with detailed guides
- ✅ **Modern UI/UX Design** with responsive layouts
- ✅ **Database Optimization** with efficient queries

## 📚 Documentation

| Document | Description |
|----------|-------------|
| [📖 User Manual](USER_MANUAL.md) | Complete guide for end users |
| [🔧 Admin Guide](ADMIN_GUIDE.md) | Administrator handbook |
| [📋 System Requirements](SRS_Document.md) | Technical specifications |
| [🚀 Deployment Guide](PRODUCTION_DEPLOYMENT.md) | Production setup instructions |
| [🧪 Testing Guide](USER_TESTING_GUIDE.md) | Testing scenarios & validation |

## 🤝 Contributing

This project demonstrates enterprise Java development practices and is open for educational contributions:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is developed for **educational purposes** as part of Software Engineering coursework, demonstrating modern Java development practices and enterprise application architecture.

## 🌟 Acknowledgments

- Built with modern Java development best practices
- Implements enterprise-grade security standards
- Follows MVC architectural patterns
- Demonstrates full-stack development skills

---

<div align="center">

**⭐ Star this repository if you found it helpful!**

Made with ❤️ for learning enterprise Java development

[🐛 Report Bug](https://github.com/saikriz898/smart-coffee-vending-system/issues) • [✨ Request Feature](https://github.com/saikriz898/smart-coffee-vending-system/issues) • [📧 Contact](mailto:your-email@example.com)

</div>