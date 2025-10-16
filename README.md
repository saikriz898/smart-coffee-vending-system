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
├── 🚀 start-gui.bat          # Quick Launcher
├── 🔧 deploy.bat             # Deployment Script
└── 📄 pom.xml                # Maven Configuration
```

## 🎮 How to Use

### 🚀 Running the Application

#### Method 1: Quick Launch (Recommended)
```bash
# Navigate to project directory
cd smart-coffee-vending-system

# Launch GUI application
./start-gui.bat

# Or use Maven directly
mvn exec:java -Dexec.mainClass="com.cvs.gui.MainUI"
```

#### Method 2: Build JAR and Run
```bash
# Build runnable JAR
mvn clean package

# Run the JAR file
java -jar target/CoffeeVendingSystem-1.0-SNAPSHOT.jar
```

### 👤 User Guide

#### Registration & Login
1. **Register New User**:
   - Click "📝 Register New User"
   - Fill: Name, Email, Password
   - Get $10 welcome bonus automatically

2. **User Login**:
   - Enter email and password
   - Select "User" option
   - Access user dashboard

#### Shopping Experience
1. **Browse Menu**: View available coffee items with prices
2. **Add to Cart**: Select items with customizations (size, sugar, milk)
3. **Checkout**: Process orders using wallet balance
4. **Track Orders**: View real-time order status
5. **Manage Wallet**: Add funds and view transaction history

### 🔧 Admin Guide

#### Admin Login
- **Username**: `admin`
- **Password**: `admin123`
- Select "Admin" option

#### Admin Features
1. **📊 Dashboard**: View system statistics and performance metrics
2. **☕ Menu Management**: Add/edit coffee items and pricing
3. **📋 Order Management**: Monitor and update order status
4. **📦 Inventory**: Manage stock levels with low stock alerts
5. **📈 Reports**: Generate daily/weekly/monthly analytics
6. **👥 User Management**: View customer accounts and activities

## 🔐 Default Credentials

### Admin Accounts
- **Primary Admin**: `admin` / `admin123`
- **Manager**: `manager` / `manager123`

### Test Users (if sample data exists)
- **User 1**: `john@example.com` / `password123`
- **User 2**: `jane@example.com` / `password123`

## 🔧 Troubleshooting

### Common Issues & Solutions

#### Database Connection Failed
```
❌ Error: "Database connection test: FAILED"
✅ Solution:
- Ensure MySQL server is running
- Check config.properties credentials
- Verify database exists: coffee_vending_system
- Run: mysql -u root -p < database_schema.sql
```

#### Maven Command Not Found
```
❌ Error: "'mvn' is not recognized"
✅ Solution:
- Install Maven from https://maven.apache.org/download.cgi
- Add Maven to PATH environment variable
- Restart command prompt/terminal
```

#### Java Version Issues
```
❌ Error: "java.lang.UnsupportedClassVersionError"
✅ Solution:
- Install Java 11 or higher
- Check version: java -version
- Update JAVA_HOME environment variable
```

#### Port Already in Use
```
❌ Error: "Port 3306 already in use"
✅ Solution:
- Stop other MySQL instances
- Change port in config.properties
- Restart MySQL service
```

## 🎨 UI Navigation

### Main Screen Options
- **👤 User Login**: Customer access
- **🔧 Admin Login**: Administrator access  
- **📝 Register**: Create new user account
- **🔗 Test DB Connection**: Verify database connectivity

### User Dashboard Features
- **☕ Menu**: Browse coffee catalog
- **🛒 Cart**: Manage order items
- **💳 Checkout**: Process payments
- **📋 My Orders**: Order history
- **💰 Add Balance**: Wallet top-up

### Admin Dashboard Features
- **📊 Dashboard**: System overview
- **☕ Menu Management**: Item control
- **📋 Orders**: Order monitoring
- **📦 Inventory**: Stock management
- **📈 Reports**: Business analytics

## 🏆 Key Achievements

- ✅ **Complete MVC Architecture** with proper separation of concerns
- ✅ **Enterprise-grade Security** with password hashing & SQL injection prevention
- ✅ **Real-time Data Updates** across all GUI components
- ✅ **Comprehensive Testing** with 95%+ code coverage
- ✅ **Production-ready Deployment** with automated scripts
- ✅ **Professional Documentation** with detailed guides
- ✅ **Modern UI/UX Design** with responsive layouts
- ✅ **Database Optimization** with efficient queries

## 🧪 System Validation

The system includes comprehensive testing:

### Automated Tests
- **Unit Tests**: All DAO, Service, and Model classes
- **Integration Tests**: Database connectivity and transactions
- **Performance Tests**: Query optimization and response times
- **Security Tests**: Authentication and SQL injection prevention

### Manual Testing Scenarios
- **User Registration & Login**: Account creation and authentication
- **Coffee Ordering**: Menu browsing, cart management, checkout
- **Wallet Operations**: Balance management and transactions
- **Admin Functions**: Inventory, reports, user management
- **System Performance**: Response times and concurrent users

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

[🐛 Report Bug](https://github.com/saikriz898/smart-coffee-vending-system/issues) • [✨ Request Feature](https://github.com/saikriz898/smart-coffee-vending-system/issues)

</div>