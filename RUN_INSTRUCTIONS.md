# ☕ Coffee Vending System - Run Instructions

## 🚀 How to Run the Application

### Method 1: Using Maven (Recommended)
```bash
# Navigate to project directory
cd C:\College\Project\SE-Project\CoffeeVendingSystem

# Compile the project
mvn clean compile

# Run the application
mvn exec:java -Dexec.mainClass="com.cvs.gui.MainUI"
```

### Method 2: Using PowerShell (Correct Syntax)
```powershell
# Navigate to project directory
cd C:\College\Project\SE-Project\CoffeeVendingSystem

# Run with proper PowerShell syntax
Start-Process mvn -ArgumentList "exec:java", "-Dexec.mainClass=com.cvs.gui.MainUI"

# OR run directly without Start-Process
mvn exec:java -Dexec.mainClass="com.cvs.gui.MainUI"
```

### Method 3: Build JAR and Run
```bash
# Build runnable JAR
mvn clean package

# Run the JAR file
java -jar target/CoffeeVendingSystem-1.0-SNAPSHOT.jar
```

## 📋 Prerequisites Setup

### 1. Database Setup (Required)
```sql
-- Option 1: Run the schema file (if MySQL is installed)
mysql -u root -p < database_schema.sql

-- Option 2: Manual setup
mysql -u root -p
CREATE DATABASE coffee_vending_system;
USE coffee_vending_system;
-- Then copy and paste the contents of database_schema.sql
```

### 2. Configuration
Update `src/main/resources/config.properties`:
```properties
db.url=jdbc:mysql://localhost:3306/coffee_vending_system?useSSL=false&serverTimezone=UTC
db.username=root
db.password=your_mysql_password
```

## 🎯 Testing the Application

### User Testing
1. **Register New User**:
   - Click "Register New User"
   - Fill: Name, Email, Password
   - Get $10 welcome bonus

2. **User Login**:
   - Email: your_registered_email
   - Password: your_password
   - Select "User" radio button

3. **Place Orders**:
   - Browse coffee menu
   - Add items to cart with customizations
   - Process checkout with wallet payment

### Admin Testing
1. **Admin Login**:
   - Username: `admin`
   - Password: `admin123`
   - Select "Admin" radio button

2. **Admin Features**:
   - Dashboard: View statistics
   - Menu Management: Add/edit coffee items
   - Orders: Monitor and update order status
   - Inventory: Manage ingredient quantities
   - Reports: Generate daily/weekly/monthly reports

## 🔧 Troubleshooting

### Common Issues & Solutions

#### 1. Database Connection Failed
```
Error: "Database connection test: FAILED"
Solution:
- Ensure MySQL server is running
- Check config.properties credentials
- Verify database exists: coffee_vending_system
```

#### 2. Maven Command Not Found
```
Error: "'mvn' is not recognized"
Solution:
- Install Maven: https://maven.apache.org/download.cgi
- Add Maven to PATH environment variable
- Restart command prompt/PowerShell
```

#### 3. Java Version Issues
```
Error: "java.lang.UnsupportedClassVersionError"
Solution:
- Ensure Java 11 or higher is installed
- Check: java -version
- Update JAVA_HOME if needed
```

#### 4. Port Already in Use
```
Error: "Port 3306 already in use"
Solution:
- Stop other MySQL instances
- Change port in config.properties
- Restart MySQL service
```

## 📱 Application Features

### User Features
- ✅ User registration with email validation
- ✅ Secure login with password hashing
- ✅ Browse coffee menu with prices
- ✅ Shopping cart with customizations (size, sugar, milk)
- ✅ Wallet-based payment system
- ✅ Order history and receipt generation
- ✅ Real-time balance updates

### Admin Features
- ✅ Admin dashboard with statistics
- ✅ Menu management (add/edit/toggle availability)
- ✅ Order monitoring and status updates
- ✅ Inventory management with low stock alerts
- ✅ Comprehensive reporting (daily/weekly/monthly)
- ✅ User management and analytics

## 🎨 UI Navigation

### Main Screen
- **User Login**: For customers
- **Admin Login**: For administrators
- **Register**: Create new user account
- **Test DB Connection**: Verify database connectivity

### User Dashboard
- **Menu**: Browse available coffee items
- **Cart**: Add items with customizations
- **Checkout**: Process orders and payments
- **My Orders**: View order history
- **Add Balance**: Top up wallet

### Admin Dashboard
- **📊 Dashboard**: System statistics
- **☕ Menu Management**: Coffee item control
- **📋 Orders**: Order monitoring
- **📦 Inventory**: Stock management
- **📈 Reports**: Analytics and reports

## 🔐 Default Credentials

### Admin Accounts
- Username: `admin` | Password: `admin123`
- Username: `manager` | Password: `manager123`

### Test User (if database has sample data)
- Email: `john@example.com` | Password: `password123`
- Email: `jane@example.com` | Password: `password123`

## 📞 Support

If you encounter issues:
1. Check the console output for error messages
2. Verify database connection in the application
3. Ensure all prerequisites are installed
4. Check the logs for detailed error information

---

**Ready to Run!** 🚀 The Coffee Vending System is fully functional with all features implemented.