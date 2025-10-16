@echo off
echo ========================================
echo Coffee Vending System - Test Execution
echo ========================================
echo.

echo 🚀 Starting comprehensive test suite...
echo.

echo 📋 Test Categories:
echo    1. Database Tests
echo    2. Functional Tests  
echo    3. Integration Tests
echo    4. GUI Tests
echo    5. Performance Tests
echo    6. Security Tests
echo    7. Load Tests
echo    8. End-to-End Tests
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
echo 🗄️  Running Database Tests...
mvn test -Dtest=DatabaseTest

echo.
echo ⚙️  Running Functional Tests...
mvn test -Dtest=FunctionalTest

echo.
echo 🔗 Running Integration Tests...
mvn test -Dtest=IntegrationTest

echo.
echo 🖥️  Running GUI Tests...
mvn test -Dtest=GUITest

echo.
echo ⚡ Running Performance Tests...
mvn test -Dtest=PerformanceTest

echo.
echo 🔒 Running Security Tests...
mvn test -Dtest=SecurityTest

echo.
echo 📈 Running Load Tests...
mvn test -Dtest=LoadTest

echo.
echo 🎯 Running End-to-End Tests...
mvn test -Dtest=EndToEndTest

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