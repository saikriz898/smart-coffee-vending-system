# 🚀 Deployment Guide - Coffee Vending System

## 🌐 Online Deployment Options

### 1. 🔵 **AWS EC2 (Recommended for GUI Apps)**
```bash
# Launch EC2 instance with Ubuntu
# Install Docker and Docker Compose
sudo apt update
sudo apt install docker.io docker-compose
sudo usermod -aG docker $USER

# Clone repository
git clone https://github.com/saikriz898/smart-coffee-vending-system.git
cd smart-coffee-vending-system

# Deploy with Docker
chmod +x scripts/deploy.sh
./scripts/deploy.sh
```

### 2. 🌊 **Railway (Modern Platform)**
```bash
# Install Railway CLI
npm install -g @railway/cli

# Login and deploy
railway login
railway init
railway up
```

### 3. 🚀 **Heroku (Convert to Web App)**
```bash
# Install Heroku CLI
# Create Procfile for web deployment
echo "web: java -jar target/CoffeeVendingSystem-1.0-SNAPSHOT.jar" > Procfile

# Deploy
heroku create coffee-vending-system
git push heroku master
```

### 4. ☁️ **DigitalOcean Droplet**
```bash
# Create droplet with Docker
# SSH into droplet
ssh root@your-droplet-ip

# Install dependencies
apt update && apt install docker.io docker-compose git

# Deploy application
git clone https://github.com/saikriz898/smart-coffee-vending-system.git
cd smart-coffee-vending-system
docker-compose up -d
```

## 🐳 Docker Deployment

### Quick Start
```bash
# Build and run with Docker Compose
docker-compose up --build -d

# Check status
docker-compose ps

# View logs
docker-compose logs -f
```

### Manual Docker Build
```bash
# Build image
docker build -t coffee-vending-system .

# Run container
docker run -d \
  --name coffee-app \
  -p 8080:8080 \
  -e DB_URL="jdbc:mysql://host:3306/coffee_vending_system" \
  -e DB_USERNAME="user" \
  -e DB_PASSWORD="password" \
  coffee-vending-system
```

## 🌐 **Best Deployment Options for Java Swing GUI:**

### Option 1: AWS EC2 with X11 Forwarding
- **Cost**: Free tier available
- **GUI Support**: ✅ Full GUI support
- **Setup**: Medium complexity
- **URL**: Direct IP access

### Option 2: Convert to Web Application
- **Framework**: Spring Boot + Thymeleaf
- **GUI**: Web-based interface
- **Deployment**: Any cloud platform
- **Cost**: Free tiers available

### Option 3: VNC Server Setup
- **Platform**: Any VPS (DigitalOcean, Linode)
- **Access**: Web-based VNC viewer
- **GUI**: Full desktop environment
- **Cost**: $5-10/month

## 🔧 Environment Variables

```bash
# Database Configuration
DB_URL=jdbc:mysql://localhost:3306/coffee_vending_system
DB_USERNAME=coffeeuser
DB_PASSWORD=securepassword

# Application Settings
ENVIRONMENT=production
PASSWORD_SALT=your_secure_salt_here
```

## 📋 Deployment Checklist

- [ ] Database setup and migration
- [ ] Environment variables configured
- [ ] SSL certificates (for production)
- [ ] Backup strategy implemented
- [ ] Monitoring and logging setup
- [ ] Security hardening completed
- [ ] Performance testing done
- [ ] Documentation updated

## 🔍 Monitoring & Maintenance

```bash
# Check application health
curl http://your-domain/health

# View application logs
docker-compose logs coffee-app

# Database backup
docker exec coffee-mysql mysqldump -u root -p coffee_vending_system > backup.sql

# Update application
git pull origin master
docker-compose up --build -d
```