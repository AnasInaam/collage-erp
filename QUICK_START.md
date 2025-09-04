# 🎯 Quick Start Guide - Supabase PostgreSQL Setup Complete!

## ✅ What's Been Done

Your Spring Boot College ERP application has been successfully configured for Supabase PostgreSQL:

1. **Database Configuration**: Updated to use Supabase PostgreSQL with connection pooling
2. **H2 Removal**: Completely removed H2 dependencies from Maven
3. **Profile Management**: All profiles now use Supabase PostgreSQL
4. **Build Scripts**: Updated run scripts for Supabase integration

## 🚀 Database Connection Details

Your application is now connected to:
- **Host**: db.ogjutpejtgtaydlqsuez.supabase.co
- **Database**: postgres
- **Username**: postgres
- **Port**: 5432

## 💻 Local Development

### 🔧 First Time Setup (If using PowerShell scripts)
If you get "execution policy" errors, run this once:
```powershell
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
```

### 🚀 Running the Application

#### Method 1: PowerShell Script (Recommended)
```powershell
# Add Maven to PATH (if needed)
$env:PATH += ";C:\apache-maven-3.9.11\bin"

# Run in Development Mode
.\run.ps1 dev
```

#### Method 2: Batch Script (Alternative)
```cmd
run.bat dev
```

#### Method 3: Direct Maven Command
```powershell
$env:PATH += ";C:\apache-maven-3.9.11\bin"
mvn spring-boot:run "-Dspring-boot.run.profiles=dev"
```

### 🎯 Other Commands

#### Run in Production Mode:
```powershell
.\run.ps1 prod
# OR
run.bat prod
```

#### Run Tests:
```powershell
.\run.ps1 test
# OR
run.bat test
```

#### Build Application:
```powershell
.\run.ps1 build
# OR
run.bat build
```

## 📝 Default Users (Auto-created)
- **Admin**: `admin` / `admin123`
- **Student**: `student` / `student123`
- **Faculty**: `faculty` / `faculty123`

## 🔗 Important URLs (Local Development)
- Application: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- Health Check: `http://localhost:8080/actuator/health`

## 📚 Configuration Files Updated
- `pom.xml` - Removed H2, kept PostgreSQL and HikariCP
- `application.properties` - Configured for Supabase PostgreSQL
- `application-dev.properties` - Development profile with Supabase
- `application-prod.properties` - Production profile with Supabase
- `application-test.properties` - Test profile with Supabase
- `run.ps1` / `run.bat` - Updated run scripts

## ⚠️ Important Notes
- All profiles now use Supabase PostgreSQL (no more H2)
- Database tables will be auto-created/updated based on your entities
- `spring.jpa.hibernate.ddl-auto=update` ensures schema sync
- Connection pooling is configured with HikariCP for optimal performance
- SQL queries are visible in development mode (`show-sql=true`)

## 🔧 JPA/Hibernate Configuration
- **DDL Auto**: `update` (creates/updates tables automatically)
- **Dialect**: PostgreSQLDialect
- **Show SQL**: Enabled in development, disabled in production
- **Connection Pool**: HikariCP with optimized settings

Your application is now ready to run with Supabase PostgreSQL! 🎉

## 🚀 Quick Start
1. Run `.\run.ps1 dev` to start the application
2. Visit `http://localhost:8080` to see your application
3. Check `http://localhost:8080/swagger-ui.html` for API documentation
4. Login with default users listed above
