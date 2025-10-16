@echo off
echo ========================================
echo COMPREHENSIVE SYSTEM VALIDATION
echo ========================================

echo 1. Database Connection Test...
mvn test -Dtest=DBConnectorTest -q
if %errorlevel% neq 0 (echo FAIL: Database & exit /b 1)
echo PASS: Database

echo 2. Model Layer Test...
mvn test -Dtest=*ModelTest -q
if %errorlevel% neq 0 (echo FAIL: Models & exit /b 1)
echo PASS: Models

echo 3. Service Layer Test...
mvn test -Dtest=*ServiceTest -q
if %errorlevel% neq 0 (echo FAIL: Services & exit /b 1)
echo PASS: Services

echo 4. DAO Layer Test...
mvn test -Dtest=*DAOTest -q
if %errorlevel% neq 0 (echo FAIL: DAOs & exit /b 1)
echo PASS: DAOs

echo 5. Full Build Test...
mvn clean package -q
if %errorlevel% neq 0 (echo FAIL: Build & exit /b 1)
echo PASS: Build

echo 6. JAR Execution Test...
java -cp target\CoffeeVendingSystem-1.0-SNAPSHOT.jar com.cvs.utils.DBConnector
if %errorlevel% neq 0 (echo FAIL: JAR & exit /b 1)
echo PASS: JAR

echo.
echo ✅ ALL TESTS PASSED - SYSTEM READY