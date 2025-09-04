# 🚀 Command Reference - Multiple Ways to Run Your Application

## 🔧 PowerShell Execution Policy Fix (One-time setup)

If you get "execution policy" errors, run this once:
```powershell
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
```

## 💻 Method 1: PowerShell Script (Recommended)

```powershell
# Add Maven to PATH first (if needed)
$env:PATH += ";C:\apache-maven-3.9.11\bin"

# Then run your application
.\run.ps1 dev     # Development mode
.\run.ps1 prod    # Production mode  
.\run.ps1 test    # Run tests
.\run.ps1 build   # Build application
```

## 💻 Method 2: Batch Script (Alternative)

```cmd
run.bat dev     # Development mode
run.bat prod    # Production mode
run.bat test    # Run tests
run.bat build   # Build application
```

## 💻 Method 3: Direct Maven Commands

```powershell
# Add Maven to PATH first
$env:PATH += ";C:\apache-maven-3.9.11\bin"

# Development mode with Supabase PostgreSQL
mvn spring-boot:run "-Dspring-boot.run.profiles=dev"

# Production mode
mvn spring-boot:run "-Dspring-boot.run.profiles=prod"

# Run tests
mvn test "-Dspring.profiles.active=test"

# Build application
mvn clean package -DskipTests

# Run built JAR
java -jar target/spring-boot-demo-1.0.0.jar
```

## 💻 Method 4: VS Code Tasks

You can also use the pre-configured VS Code tasks:
1. Open Command Palette (`Ctrl+Shift+P`)
2. Type "Tasks: Run Task"
3. Select one of the available tasks

## 🌐 Application URLs

Once started, your application will be available at:
- **Main Application**: http://localhost:8080
- **Swagger API Docs**: http://localhost:8080/swagger-ui.html
- **Health Check**: http://localhost:8080/actuator/health

## 👥 Default Login Credentials

- **Admin**: username=`admin`, password=`admin123`
- **Student**: username=`student`, password=`student123`
- **Faculty**: username=`faculty`, password=`faculty123`

## 🔍 Troubleshooting

### PowerShell Script Issues
```powershell
# Check execution policy
Get-ExecutionPolicy

# Fix restricted policy
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
```

### Maven Not Found
```powershell
# Add Maven to PATH
$env:PATH += ";C:\apache-maven-3.9.11\bin"

# Verify Maven is working
mvn -version
```

### Port Already in Use
```powershell
# Check what's using port 8080
netstat -ano | findstr :8080

# Kill process by PID (replace XXXX with actual PID)
taskkill /PID XXXX /F

# Or use a different port
mvn spring-boot:run "-Dspring-boot.run.profiles=dev" "-Dserver.port=8081"
```

## ✅ Quick Start (Choose Your Preferred Method)

### Option A: PowerShell (Recommended)
```powershell
$env:PATH += ";C:\apache-maven-3.9.11\bin"
.\run.ps1 dev
```

### Option B: Batch Script
```cmd
run.bat dev
```

### Option C: Direct Maven
```powershell
$env:PATH += ";C:\apache-maven-3.9.11\bin"
mvn spring-boot:run "-Dspring-boot.run.profiles=dev"
```

Choose the method that works best for your environment! 🚀
