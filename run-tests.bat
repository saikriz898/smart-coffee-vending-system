@echo off
echo ========================================
echo Coffee Vending System - Test Execution
echo ========================================
echo.

echo 🚀 Starting comprehensive test suite...
echo.

echo 📋 Test Categories:
echo    1. Database Connection Tests
echo    2. Functional Tests  
echo    3. Performance Tests
echo    4. Security Tests
echo    5. Simple Service Tests
echo    6. User DAO Tests
echo    7. Test Suite Orchestration
echo.

echo ⏱️  Estimated execution time: 2-3 minutes
echo.

pause

echo 🧪 Running all tests with coverage report...
mvn clean test jacoco:report

echo.
echo 📊 Test Results Summary:
echo    - Check target/surefire-reports/ for detailed results
echo    - Check target/site/jacoco/ for coverage report
echo.

echo 🔍 Running specific test categories (optional):
echo.
echo Press any key to run individual test categories, or close to finish...
pause

echo.
echo 🗄️  Running Database Connection Tests...
mvn test -Dtest=DBConnectorTest

echo.
echo ⚙️  Running Functional Tests...
mvn test -Dtest=FunctionalTest

echo.
echo ⚡ Running Performance Tests...
mvn test -Dtest=PerformanceTest

echo.
echo 🔒 Running Security Tests...
mvn test -Dtest=SecurityTest

echo.
echo 🚀 Running Simple Service Tests...
mvn test -Dtest=SimpleTest

echo.
echo 📁 Running User DAO Tests...
mvn test -Dtest=UserDAOTest

echo.
echo 🎯 Running Test Suite...
mvn test -Dtest=TestSuite

echo.
echo ✅ All tests completed!
echo.
echo 📋 Next Steps:
echo    1. Review test reports in target/surefire-reports/
echo    2. Check code coverage in target/site/jacoco/index.html
echo    3. Address any failing tests if present
echo    4. System is ready for deployment if all tests pass
echo.

pause