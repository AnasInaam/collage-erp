# 🚀 Deployment Guide - College ERP System

## 📋 Prerequisites
- Java 17+
- Maven 3.6+
- PostgreSQL Database (Supabase configured)

## 🔧 Environment Variables for Production

### Required Environment Variables
```bash
DATABASE_URL=jdbc:postgresql://your-db-host:5432/your-db-name?sslmode=require
DB_USERNAME=your-username
DB_PASSWORD=your-password
JWT_SECRET=your-super-secure-jwt-secret-256-bits-minimum
PORT=8080
SPRING_PROFILES_ACTIVE=prod
```

## 🏗️ Build Commands

### Development Build
```bash
mvn clean compile
```

### Production Build
```bash
mvn clean package -DskipTests
```

### Run Tests
```bash
mvn test
```

## 🚀 Deployment Options

### 1. Local JAR Deployment
```bash
# Build the application
.\run.ps1 build

# Run with production profile
java -jar target/college-erp-1.0.0.jar --spring.profiles.active=prod

# OR use the run script
.\run.ps1 run
```

### 2. Docker Deployment
```bash
# Build the Docker image
docker build -t college-erp:latest .

# Run the container
docker run -d \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e DATABASE_URL=your_database_url \
  -e DB_USERNAME=your_username \
  -e DB_PASSWORD=your_password \
  -e JWT_SECRET=your_jwt_secret \
  --name college-erp \
  college-erp:latest
```

### 3. Heroku Deployment
```bash
# Login to Heroku
heroku login

# Create a new Heroku app
heroku create your-college-erp-app

# Set environment variables
heroku config:set DATABASE_URL=your_supabase_url
heroku config:set DB_USERNAME=postgres
heroku config:set DB_PASSWORD=your_password
heroku config:set JWT_SECRET=your_jwt_secret
heroku config:set SPRING_PROFILES_ACTIVE=prod

# Deploy
git add .
git commit -m "Deploy to Heroku"
git push heroku main
```

### 4. Render Deployment
1. Connect your GitHub repository to Render
2. Create a new Web Service
3. Configure build settings:
   - **Build Command**: `mvn clean package -DskipTests`
   - **Start Command**: `java -jar target/college-erp-1.0.0.jar`
4. Set environment variables in Render dashboard
5. Deploy!

## 🌐 Application URLs

### Development (localhost:8080)
- **Home**: http://localhost:8080
- **Dashboard**: http://localhost:8080/dashboard.html
- **API Docs**: http://localhost:8080/swagger-ui.html
- **Health Check**: http://localhost:8080/actuator/health

### Production
Replace `localhost:8080` with your deployed domain

## 🔑 Default Credentials
| Role | Username | Password |
|------|----------|----------|
| Admin | `admin` | `admin123` |
| Student | `student` | `student123` |
| Faculty | `faculty` | `faculty123` |

## 🛠️ Troubleshooting

### Common Issues

1. **Database Connection Failed**
   - Check DATABASE_URL format
   - Verify credentials
   - Ensure database server is accessible

2. **Application Won't Start**
   - Check Java version (must be 17+)
   - Verify all environment variables are set
   - Check application logs

3. **Port Already in Use**
   - Change PORT environment variable
   - Kill process using the port: `netstat -ano | findstr :8080`

### Health Checks
```bash
# Check application health
curl http://your-domain/actuator/health

# Check if database is connected
curl http://your-domain/api/dashboard/stats
```

## 📊 Performance Optimization

### JVM Options for Production
```bash
java -Xmx512m -Xms256m -XX:+UseG1GC -jar college-erp-1.0.0.jar
```

### Database Connection Pool Tuning
Adjust these in `application-prod.properties`:
```properties
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=2
spring.datasource.hikari.connection-timeout=30000
```

## 🔒 Security Considerations

1. **Never expose database credentials in code**
2. **Use strong JWT secrets (256+ bits)**
3. **Enable HTTPS in production**
4. **Regularly update dependencies**
5. **Monitor application logs**

## 📈 Monitoring

### Application Metrics
- **Health**: `/actuator/health`
- **Info**: `/actuator/info`
- **Metrics**: `/actuator/metrics` (if enabled)

### Log Monitoring
- Application logs are written to console
- Configure log aggregation in production
- Monitor error rates and response times

---

For more detailed information, see the main [README.md](README.md) file.
