# 🚀 RENDER DEPLOYMENT CHECKLIST

## ✅ Pre-Deployment Verification

### 1. PostgreSQL Compatibility ✅
- [x] PostgreSQL driver (42.6.0) included in dependencies
- [x] Application builds successfully with Java 21
- [x] Production properties configured for PostgreSQL
- [x] JPA entities use GenerationType.IDENTITY (PostgreSQL compatible)
- [x] Connection pooling configured for Render's limits

### 2. Configuration Files ✅
- [x] `render.yaml` - Updated for native Java build (not Docker)
- [x] `application-prod.properties` - PostgreSQL configuration
- [x] `application-render.properties` - Render-specific optimizations
- [x] Database initialization script created

### 3. Environment Variables Required 📋
```bash
DATABASE_URL=postgresql://user:pass@host:5432/college_erp
SPRING_PROFILES_ACTIVE=prod
PORT=10000
DDL_AUTO=update
SHOW_SQL=false
LOG_LEVEL=INFO
DB_MAX_CONNECTIONS=3
JWT_SECRET=your-secure-secret-256-bits-long
EMAIL_VERIFICATION_ENABLED=false
```

## 🎯 Deployment Steps

### Step 1: Create PostgreSQL Database on Render
1. Login to Render Dashboard: https://dashboard.render.com
2. Click "New +" → "PostgreSQL"
3. Configure:
   - Name: `college-erp-db`
   - Database: `college_erp`
   - User: `college_erp_user`
   - Plan: Free (or Starter for production)
4. Save the External Database URL

### Step 2: Create Web Service
1. Click "New +" → "Web Service"
2. Connect GitHub repository: `AnasInaam/collage-erp`
3. Configure:
   - Name: `college-erp-system`
   - Runtime: Java
   - Build Command: `mvn clean package -DskipTests`
   - Start Command: `java -Dserver.port=$PORT -jar target/college-erp-1.0.0.jar`

### Step 3: Set Environment Variables
Copy the environment variables from the checklist above and set them in your Render web service.

### Step 4: Deploy
Click "Create Web Service" and wait for deployment to complete.

## 🧪 Testing Your Deployment

### Health Check
```bash
curl https://college-erp-system.onrender.com/actuator/health
# Expected: {"status":"UP"}
```

### User Registration Test
```bash
curl -X POST https://college-erp-system.onrender.com/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Test",
    "lastName": "User", 
    "email": "test@example.com",
    "password": "SecurePass123",
    "username": "testuser"
  }'
```

### Database Tables Verification
Your PostgreSQL database should automatically create these tables:
- users
- user_roles
- departments
- courses
- students
- faculty
- fee_structures
- fee_payments
- student_enrollments
- attendance

## 🔧 Troubleshooting

### Common Issues:

1. **Build Fails**
   - Check Java version is 21
   - Verify Maven build works locally: `mvn clean package`

2. **Database Connection Fails**
   - Verify DATABASE_URL is correct (External Database URL from Render)
   - Ensure database and web service are in same region

3. **Application Won't Start**
   - Check PORT environment variable (should be 10000)
   - Verify JAR file name matches start command

4. **Health Check Fails**
   - Check if application is running
   - Verify actuator endpoints are enabled

## 💰 Pricing

### Free Tier (Development)
- Web Service: Free (spins down after 15 min inactivity)
- PostgreSQL: Free (1 GB storage)
- Total: $0/month

### Production Ready
- Web Service: Starter $7/month (always on)
- PostgreSQL: Starter $7/month (dedicated resources)
- Total: $14/month

## 🎉 Next Steps After Deployment

1. **Test all API endpoints**
2. **Create default admin user**
3. **Set up monitoring and alerts**
4. **Configure custom domain (optional)**
5. **Set up automated backups**
6. **Enable SSL (automatic with Render)**

Your College ERP system is now PostgreSQL-ready and can be deployed to Render!