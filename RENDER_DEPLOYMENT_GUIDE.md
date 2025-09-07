# Render Deployment Guide for College ERP System

This guide provides step-by-step instructions for deploying the College ERP System to Render with PostgreSQL database.

## Prerequisites

- GitHub account with your College ERP repository
- Render account (sign up at [render.com](https://render.com))
- PostgreSQL database (we'll use Render's PostgreSQL service)

## Step 1: Prepare Your Repository

1. **Ensure your code is pushed to GitHub**:
   ```bash
   git add .
   git commit -m "Prepare for Render deployment"
   git push origin main
   ```

2. **Verify Maven build works locally**:
   ```bash
   mvn clean package -DskipTests
   ```

## Step 2: Create PostgreSQL Database on Render

1. **Log in to Render Dashboard**:
   - Go to [dashboard.render.com](https://dashboard.render.com)
   - Click "New +" button

2. **Create PostgreSQL Database**:
   - Select "PostgreSQL"
   - Choose a name: `college-erp-db`
   - Choose region closest to your users
   - Select plan (free tier available)
   - Click "Create Database"

3. **Get Database Connection Details**:
   - Once created, go to your database dashboard
   - Copy the following information:
     - **External Database URL** (starts with `postgres://`)
     - **Host**
     - **Port** (usually 5432)
     - **Database Name**
     - **Username**
     - **Password**

## Step 3: Create Web Service on Render

1. **Create New Web Service**:
   - Click "New +" → "Web Service"
   - Connect your GitHub account
   - Select your College ERP repository

2. **Configure Web Service**:
   - **Name**: `college-erp-system`
   - **Region**: Same as your database
   - **Branch**: `main`
   - **Runtime**: `Java`
   - **Build Command**:
     ```bash
     mvn clean package -DskipTests
     ```
   - **Start Command**:
     ```bash
     java -jar target/college-erp-1.0.0.jar
     ```

## Step 4: Configure Environment Variables

In your Render web service settings, add these environment variables:

### Database Configuration
```env
DATABASE_URL=<your-render-postgresql-external-url>
DB_USERNAME=<your-database-username>
DB_PASSWORD=<your-database-password>
```

### Application Configuration
```env
SPRING_PROFILES_ACTIVE=prod
DDL_AUTO=update
SHOW_SQL=false
LOG_LEVEL=INFO
PORT=8080
```

### JWT Security
```env
JWT_SECRET=myVerySecureJWTSecretKeyThatIsAtLeast256BitsLongForHMACAlgorithmSecurity123456789
```

### Health Check Configuration
```env
MANAGEMENT_ENDPOINTS_WEB_EXPOSURE_INCLUDE=health,info
```

## Step 5: Deploy the Application

1. **Initial Deployment**:
   - Click "Create Web Service"
   - Render will automatically start building and deploying

2. **Monitor Deployment**:
   - Watch the build logs in Render dashboard
   - Wait for deployment to complete (usually 5-10 minutes)

3. **Verify Deployment**:
   - Once deployed, click on your service URL
   - Check health endpoint: `https://your-app.onrender.com/actuator/health`

## Step 6: Initialize Database

After successful deployment, initialize your database with default data:

1. **Access your application**:
   - URL: `https://your-college-erp-system.onrender.com`

2. **Create default admin user** (if needed):
   - Use the registration endpoint or database migration scripts

## Step 7: Test Your Deployment

### Health Check
```bash
curl https://your-college-erp-system.onrender.com/actuator/health
```

### Authentication Test
```bash
# Register a new user
curl -X POST https://your-college-erp-system.onrender.com/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Test",
    "lastName": "User",
    "email": "test@example.com",
    "password": "password123",
    "role": "STUDENT"
  }'

# Login
curl -X POST https://your-college-erp-system.onrender.com/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "password123"
  }'
```

## Step 8: Configure Custom Domain (Optional)

1. **Add Custom Domain**:
   - Go to your web service settings
   - Click "Settings" → "Custom Domains"
   - Add your domain name

2. **Update DNS**:
   - Add CNAME record pointing to your Render URL

## Environment-Specific Configurations

### Production Settings (`application-prod.properties`)
```properties
# Production Database Configuration
spring.datasource.url=${DATABASE_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

# Security Settings
spring.jpa.hibernate.ddl-auto=${DDL_AUTO:validate}
spring.jpa.show-sql=${SHOW_SQL:false}

# Logging
logging.level.com.example.collegeerp=${LOG_LEVEL:WARN}
logging.level.org.springframework.security=WARN

# Actuator (restricted in production)
management.endpoints.web.exposure.include=health
management.endpoint.health.show-details=never
```

## Troubleshooting

### Common Issues

1. **Build Failures**:
   - Check Java version compatibility (ensure Java 17)
   - Verify Maven build works locally
   - Check dependency conflicts

2. **Database Connection Issues**:
   - Verify DATABASE_URL format
   - Check database credentials
   - Ensure database is in same region

3. **Application Won't Start**:
   - Check environment variables
   - Verify JAR file name in start command
   - Review application logs

4. **Health Check Failures**:
   - Ensure `/actuator/health` endpoint is accessible
   - Check if actuator is properly configured
   - Verify application is listening on correct port

### Logs and Debugging

1. **Access Logs**:
   - Go to your web service dashboard
   - Click "Logs" tab
   - Monitor real-time logs

2. **Database Logs**:
   - Go to your PostgreSQL service
   - Check connection and query logs

### Performance Optimization

1. **Resource Allocation**:
   - Upgrade to paid plan for better performance
   - Consider scaling options

2. **Database Optimization**:
   - Index frequently queried columns
   - Monitor connection pool settings
   - Use database connection pooling

## Security Considerations

1. **Environment Variables**:
   - Never commit sensitive data to repository
   - Use Render's environment variable management

2. **Database Security**:
   - Use strong passwords
   - Enable SSL connections
   - Restrict database access

3. **Application Security**:
   - Use HTTPS (automatically provided by Render)
   - Configure CORS properly
   - Implement rate limiting

## Monitoring and Maintenance

1. **Health Monitoring**:
   - Set up alerts for downtime
   - Monitor application metrics

2. **Database Maintenance**:
   - Regular backups (automatic with Render)
   - Monitor database size and performance

3. **Updates and Deployments**:
   - Use GitHub integration for automatic deployments
   - Test in staging environment first

## Support and Resources

- **Render Documentation**: [render.com/docs](https://render.com/docs)
- **Spring Boot Deployment**: [spring.io/guides](https://spring.io/guides)
- **PostgreSQL on Render**: [render.com/docs/databases](https://render.com/docs/databases)

## Quick Deployment Checklist

- [ ] Code pushed to GitHub
- [ ] PostgreSQL database created on Render
- [ ] Web service configured with correct build/start commands
- [ ] Environment variables set
- [ ] `render.yaml` file created (optional)
- [ ] Deployment completed successfully
- [ ] Health check passing
- [ ] Authentication endpoints tested
- [ ] Database initialized with default data

Your College ERP System should now be successfully deployed on Render with PostgreSQL!
