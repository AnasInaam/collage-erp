@echo off
REM College ERP - Run Script for Different Profiles

echo College ERP Application Runner
echo ================================

if "%1"=="dev" (
    echo Running in Development mode with Supabase PostgreSQL...
    mvn spring-boot:run -Dspring-boot.run.profiles=dev
) else if "%1"=="prod" (
    echo Running in Production mode with Supabase PostgreSQL...
    mvn spring-boot:run -Dspring-boot.run.profiles=prod
) else if "%1"=="test" (
    echo Running tests...
    mvn test -Dspring.profiles.active=test
) else if "%1"=="build" (
    echo Building application...
    mvn clean package -DskipTests
) else (
    echo Usage: run.bat [dev^|prod^|test^|build]
    echo.
    echo   dev   - Run with Supabase PostgreSQL for development
    echo   prod  - Run with Supabase PostgreSQL for production
    echo   test  - Run unit tests
    echo   build - Build the application
    echo.
    echo Examples:
    echo   run.bat dev    - Start development server
    echo   run.bat prod   - Start production server
    echo   run.bat test   - Run tests
    echo   run.bat build  - Build JAR file
)
