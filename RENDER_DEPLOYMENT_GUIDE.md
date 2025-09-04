# College ERP - Render Deployment Guide

## Summary of Changes Made

Your Spring Boot application has been successfully configured to use PostgreSQL from Render. Here's what was changed:

### 1. Database Configuration Changes

#### Updated `pom.xml`:
- Kept PostgreSQL dependency
- Added HikariCP for connection pooling
- Kept H2 for development/testing

#### Updated `application.properties`:
- Changed to use PostgreSQL as the primary database
- Added connection pooling configuration
- Set production profile as default

#### Created `application-prod.properties`:
- Production-specific PostgreSQL configuration
- Environment variable-based connection settings
- Optimized for Render deployment

#### Updated `application-dev.properties`:
- Kept H2 for local development
- Added option to use local PostgreSQL

#### Created `application-test.properties`:
- H2 configuration for unit tests
- Test-specific settings

### 2. Environment Variables Needed for Render

When deploying to Render, set these environment variables:

```
DATABASE_URL=your_render_postgresql_internal_url
DB_USERNAME=your_postgresql_username  
DB_PASSWORD=your_postgresql_password
JWT_SECRET=your_strong_jwt_secret_here
SPRING_PROFILES_ACTIVE=prod
PORT=8080
```

### 3. Render Deployment Steps

#### A. Create PostgreSQL Database:
1. Go to Render Dashboard
2. Create new PostgreSQL database
3. Note down the connection details

#### B. Deploy Web Service:
1. Connect your GitHub repository
2. Create new Web Service
3. Set build command: `mvn clean package -DskipTests`
4. Set start command: `java -jar target/spring-boot-demo-1.0.0.jar`
5. Add environment variables listed above
6. Deploy

### 4. Running Locally

#### Development Mode (H2):
```bash
run.bat dev
```
or
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

#### Production Mode (PostgreSQL):
```bash
run.bat prod
```
or
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

#### Run Tests:
```bash
run.bat test
```

#### Build Application:
```bash
run.bat build
```

### 5. Important Notes

- **First Deployment**: Set `spring.jpa.hibernate.ddl-auto=create` for first run, then change to `validate`
- **Security**: Use strong passwords and JWT secrets
- **Free Tier**: Render's free PostgreSQL expires after 90 days
- **Connection Pooling**: Configured for optimal performance

### 6. Database Schema Initialization

The `DataLoader` class will automatically create sample users:
- **Admin**: username=`admin`, password=`admin123`
- **Student**: username=`student`, password=`student123`  
- **Faculty**: username=`faculty`, password=`faculty123`

### 7. API Endpoints

After deployment, your API will be available at:
- Swagger UI: `https://your-app.onrender.com/swagger-ui.html`
- API Docs: `https://your-app.onrender.com/api-docs`
- Health Check: `https://your-app.onrender.com/actuator/health`

### 8. Troubleshooting

- Check Render logs for connection issues
- Verify environment variables are set correctly
- Ensure PostgreSQL database is running
- Check firewall/network settings

Your application is now ready for PostgreSQL deployment on Render! 🚀
