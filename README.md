# ☕ Coffee Vending System

A professional Java Swing-based Coffee Vending System with JDBC-enabled MySQL database integration, featuring real-time updates, admin functionality, and modern GUI design.

## 🚀 Project Status

**Current Phase: Phase 0 - JDBC Initialization Complete**

### ✅ Completed Features
- ✅ Maven project setup with all dependencies
- ✅ JDBC driver dynamic loading
- ✅ Database connection testing
- ✅ Modern Swing GUI launcher
- ✅ Project structure and configuration
- ✅ Unit tests for database connectivity

### 🔄 Upcoming Phases
- **Phase 1**: Database schema, DAO layer, models, ER diagram
- **Phase 2**: Service layer, complete GUI, class diagram  
- **Phase 3**: Advanced GUI, admin features, sequence & activity diagrams
- **Phase 4**: Deployment, SRS documentation, use case diagram
- **Phase 5**: Testing & QA
- **Phase 6**: Finalization and production deployment

## 🏗️ Architecture

```
CoffeeVendingSystem/
├── src/main/java/com/cvs/
│   ├── models/          # Data models (Phase 1)
│   ├── dao/             # Database access objects (Phase 1)
│   ├── service/         # Business logic layer (Phase 2)
│   ├── gui/             # Swing user interface
│   └── utils/           # Utilities (DBConnector)
├── src/main/resources/  # Configuration files
├── src/test/java/       # Unit tests
└── docs/diagrams/       # UML diagrams (PNG format)
```

## 🛠️ Technologies

- **Java 11** - Core programming language
- **Swing** - GUI framework
- **JDBC** - Database connectivity
- **MySQL 8.0** - Database system
- **Maven** - Build and dependency management
- **JUnit 5** - Unit testing
- **SLF4J + Logback** - Logging
- **Apache Commons Codec** - Password hashing

## 📋 Prerequisites

1. **Java 11 or higher**
2. **MySQL Server 8.0+**
3. **Maven 3.6+**
4. **Git** (for version control)

## ⚡ Quick Start

### 1. Database Setup
```sql
-- Create database (Phase 1 will include full schema)
CREATE DATABASE coffee_vending_system;
```

### 2. Configuration
Update `src/main/resources/config.properties`:
```properties
db.url=jdbc:mysql://localhost:3306/coffee_vending_system?useSSL=false&serverTimezone=UTC
db.username=your_username
db.password=your_password
```

### 3. Build and Run
```bash
# Clone and navigate to project
cd CoffeeVendingSystem

# Compile project
mvn clean compile

# Run application
mvn exec:java -Dexec.mainClass="com.cvs.gui.MainUI"

# Or build runnable JAR
mvn clean package
java -jar target/CoffeeVendingSystem-1.0-SNAPSHOT.jar
```

### 4. Test Database Connection
- Launch the application
- Click "🔗 Test Database Connection" button
- Verify JDBC driver loading and connection success

## 🧪 Testing

```bash
# Run unit tests
mvn test

# Run specific test class
mvn test -Dtest=DBConnectorTest
```

## 📊 Features (Full System)

### 👤 User Features
- User registration and login
- Browse coffee menu with prices
- Customize orders (size, sugar, milk levels)
- Multiple payment options
- Real-time order tracking
- Digital receipt generation

### 🔧 Admin Features  
- Inventory management
- Menu updates and pricing
- Order monitoring and reports
- Daily/weekly/monthly analytics
- User management
- System configuration

### 🎨 GUI Features
- Modern responsive design
- Gradient backgrounds and hover effects
- Real-time data updates
- Error handling and validation
- Multi-panel navigation
- Professional color scheme

## 📈 Development Phases

| Phase | Description | Status |
|-------|-------------|--------|
| 0 | Project setup + JDBC initialization | ✅ Complete |
| 1 | Database + DAO + Models | 🔄 Next |
| 2 | Service layer + GUI | ⏳ Planned |
| 3 | Advanced GUI + Admin + Reports | ⏳ Planned |
| 4 | Deployment + SRS + Use Case | ⏳ Planned |
| 5 | Testing & QA | ⏳ Planned |
| 6 | Finalization | ⏳ Planned |

## 📋 Git Workflow

Each phase will be committed separately:
```bash
git add .
git commit -m "Phase 0 - Project setup + JDBC initialization"
git push origin main
```

## 🔧 Configuration

### Database Configuration
- **Host**: localhost:3306
- **Database**: coffee_vending_system  
- **Driver**: com.mysql.cj.jdbc.Driver
- **Connection Pool**: HikariCP (Phase 2)

### Application Configuration
- **GUI Framework**: Java Swing
- **Architecture**: MVC Pattern
- **Logging**: SLF4J + Logback
- **Build Tool**: Maven
- **Java Version**: 11+

## 📞 Support

For issues or questions:
1. Check the logs in `logs/` directory
2. Verify database connection settings
3. Ensure MySQL server is running
4. Check Java and Maven versions

## 📄 License

This project is developed for educational purposes as part of Software Engineering coursework.

---

**Next Step**: Implement Phase 1 - Database schema, DAO layer, and data models with ER diagram generation.