@echo off
echo ========================================
echo PRODUCTION DEPLOYMENT
echo ========================================

echo 1. Building production JAR...
mvn clean package -q
if %errorlevel% neq 0 (echo FAIL: Build & exit /b 1)

echo 2. Starting Coffee Vending System GUI...
java -cp target\CoffeeVendingSystem-1.0-SNAPSHOT.jar com.cvs.gui.MainUI