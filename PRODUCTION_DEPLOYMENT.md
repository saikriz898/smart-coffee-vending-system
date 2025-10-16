# Production Deployment Guide

## 🚀 Coffee Vending System - Production Deployment

This guide provides step-by-step instructions for deploying the Coffee Vending System to a production environment.

## 📋 Prerequisites

### System Requirements
- **Java Runtime**: JRE 11 or higher
- **Database**: MySQL Server 8.0+
- **Memory**: Minimum 512MB RAM
- **Storage**: 100MB free space
- **Network**: Internet connection for database access

### Software Dependencies
- MySQL JDBC Driver (included in JAR)
- All dependencies bundled in executable JAR

## 🗄️ Database Setup

### 1. Create Production Database
```sql
-- Connect to MySQL as root
mysql -u root -p

-- Create database
CREATE DATABASE coffee_vending_system;

-- Create application user
CREATE USER 'cvs_user'@'localhost' IDENTIFIED BY 'secure_password_here';
GRANT ALL PRIVILEGES ON coffee_vending_system.* TO 'cvs_user'@'localhost';
FLUSH PRIVILEGES;
```

### 2. Import Database Schema
```bash
# Import the database schema
mysql -u cvs_user -p coffee_vending_system < database_schema.sql
```

### 3. Verify Database Setup
```sql
-- Check tables are created
USE coffee_vending_system;
SHOW TABLES;

-- Verify admin user exists
SELECT * FROM admin;
```

## ⚙️ Application Configuration

### 1. Production Configuration File
Create `config.properties` in the same directory as the JAR:

```properties
# Production Database Configuration
db.url=jdbc:mysql://localhost:3306/coffee_vending_system
db.username=cvs_user
db.password=secure_password_here
db.driver=com.mysql.cj.jdbc.Driver

# Application Settings
app.name=Coffee Vending System
app.version=1.0.0
app.environment=production

# Logging Configuration
logging.level=INFO
logging.file=logs/application.log
```

### 2. Directory Structure
```
production/
├── CoffeeVendingSystem-1.0-SNAPSHOT.jar
├── config.properties
├── logs/
└── backup/
```

## 📦 Deployment Steps

### 1. Prepare Deployment Directory
```bash
# Create deployment directory
mkdir /opt/coffee-vending-system
cd /opt/coffee-vending-system

# Create subdirectories
mkdir logs backup
```

### 2. Deploy Application JAR
```bash
# Copy JAR file to deployment directory
cp CoffeeVendingSystem-1.0-SNAPSHOT.jar /opt/coffee-vending-system/

# Set executable permissions
chmod +x CoffeeVendingSystem-1.0-SNAPSHOT.jar
```

### 3. Deploy Configuration
```bash
# Copy production configuration
cp config.properties /opt/coffee-vending-system/
```

### 4. Start Application
```bash
# Start the application
cd /opt/coffee-vending-system
java -jar CoffeeVendingSystem-1.0-SNAPSHOT.jar

# Or with specific memory settings
java -Xmx512m -Xms256m -jar CoffeeVendingSystem-1.0-SNAPSHOT.jar
```

## 🔧 Service Configuration (Linux)

### 1. Create Systemd Service
Create `/etc/systemd/system/coffee-vending.service`:

```ini
[Unit]
Description=Coffee Vending System
After=network.target mysql.service

[Service]
Type=simple
User=cvs
Group=cvs
WorkingDirectory=/opt/coffee-vending-system
ExecStart=/usr/bin/java -jar CoffeeVendingSystem-1.0-SNAPSHOT.jar
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
```

### 2. Enable and Start Service
```bash
# Reload systemd
sudo systemctl daemon-reload

# Enable service
sudo systemctl enable coffee-vending

# Start service
sudo systemctl start coffee-vending

# Check status
sudo systemctl status coffee-vending
```

## 🔍 Monitoring and Maintenance

### 1. Log Monitoring
```bash
# View application logs
tail -f /opt/coffee-vending-system/logs/application.log

# View system service logs
journalctl -u coffee-vending -f
```

### 2. Health Checks
```bash
# Check if application is running
ps aux | grep CoffeeVendingSystem

# Check database connectivity
mysql -u cvs_user -p -e "SELECT 1"

# Check port usage (if applicable)
netstat -tlnp | grep java
```

### 3. Database Backup
```bash
# Create backup script
#!/bin/bash
DATE=$(date +%Y%m%d_%H%M%S)
mysqldump -u cvs_user -p coffee_vending_system > /opt/coffee-vending-system/backup/backup_$DATE.sql

# Schedule daily backups with cron
0 2 * * * /opt/coffee-vending-system/backup.sh
```

## 🔒 Security Considerations

### 1. Database Security
- Use strong passwords for database users
- Limit database user privileges to necessary operations only
- Enable MySQL SSL connections if possible
- Regular security updates for MySQL

### 2. Application Security
- Store configuration files with restricted permissions (600)
- Use environment variables for sensitive data
- Regular Java security updates
- Monitor application logs for suspicious activity

### 3. System Security
- Run application with non-root user
- Configure firewall rules appropriately
- Regular OS security updates
- Monitor system resources

## 🚨 Troubleshooting

### Common Issues

#### Database Connection Failed
```bash
# Check MySQL service
sudo systemctl status mysql

# Verify credentials
mysql -u cvs_user -p

# Check network connectivity
telnet localhost 3306
```

#### Application Won't Start
```bash
# Check Java version
java -version

# Verify JAR file integrity
java -jar CoffeeVendingSystem-1.0-SNAPSHOT.jar --version

# Check file permissions
ls -la CoffeeVendingSystem-1.0-SNAPSHOT.jar
```

#### GUI Display Issues
```bash
# For headless servers, ensure X11 forwarding
ssh -X user@server

# Or use VNC/remote desktop for GUI access
```

### Performance Issues
```bash
# Monitor memory usage
top -p $(pgrep java)

# Check database performance
mysql -u cvs_user -p -e "SHOW PROCESSLIST;"

# Monitor disk space
df -h
```

## 📊 Performance Tuning

### JVM Tuning
```bash
# Optimize garbage collection
java -XX:+UseG1GC -Xmx1g -Xms512m -jar CoffeeVendingSystem-1.0-SNAPSHOT.jar

# Enable JMX monitoring
java -Dcom.sun.management.jmxremote -jar CoffeeVendingSystem-1.0-SNAPSHOT.jar
```

### Database Tuning
```sql
-- Optimize MySQL configuration
SET GLOBAL innodb_buffer_pool_size = 128M;
SET GLOBAL query_cache_size = 32M;
```

## 🔄 Updates and Maintenance

### Application Updates
1. Stop the application service
2. Backup current JAR and configuration
3. Deploy new JAR file
4. Update configuration if needed
5. Start the application service
6. Verify functionality

### Database Updates
1. Backup database before changes
2. Apply schema changes during maintenance window
3. Test with application
4. Monitor for issues

## 📞 Support and Maintenance

### Maintenance Schedule
- **Daily**: Log review and backup verification
- **Weekly**: Performance monitoring and cleanup
- **Monthly**: Security updates and system review
- **Quarterly**: Full system backup and disaster recovery test

### Contact Information
- **Development Team**: [Your contact information]
- **System Administrator**: [Admin contact]
- **Emergency Contact**: [24/7 support if available]

## ✅ Deployment Checklist

- [ ] Database server installed and configured
- [ ] Application database created with proper schema
- [ ] Application user created with appropriate privileges
- [ ] JAR file deployed to production directory
- [ ] Configuration file updated for production
- [ ] Service configured and started
- [ ] Logs directory created and writable
- [ ] Backup strategy implemented
- [ ] Monitoring configured
- [ ] Security measures implemented
- [ ] Performance baseline established
- [ ] Documentation updated
- [ ] Team trained on production procedures

## 🎯 Success Criteria

The deployment is considered successful when:
- Application starts without errors
- Database connectivity is established
- GUI loads properly
- All core functions work (login, orders, payments, admin)
- Performance meets requirements (< 500ms database queries)
- Logs are being generated properly
- Backup system is functional

---

**Note**: This deployment guide assumes a Linux production environment. For Windows production deployment, adapt the service configuration and file paths accordingly.