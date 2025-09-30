# 🔧 RENDER ENVIRONMENT VARIABLES - SUPABASE CONFIGURATION

## 📋 **EXACT ENVIRONMENT VARIABLES FOR YOUR RENDER DEPLOYMENT**

Copy these **exact** values into your Render web service environment variables:

```bash
# 1. DATABASE CONNECTION (Supabase)
Key: DATABASE_URL
Value: postgresql://postgres:TUr4J0e3ydDzoP1u@db.pcrowyvrakgsxahdaade.supabase.co:5432/postgres

# 2. APPLICATION PROFILE
Key: SPRING_PROFILES_ACTIVE
Value: prod

# 3. SERVER PORT
Key: PORT
Value: 10000

# 4. DATABASE SCHEMA MANAGEMENT
Key: DDL_AUTO
Value: update

# 5. JWT SECURITY SECRET (Generate your own using the script)
Key: JWT_SECRET
Value: B16bE27V7x3RqFHmKbEUWA0WK1O717htLFqHv7rDMfPJzarXH5fIgHm3NyE7e9oc

# 6. CONNECTION POOL (Optional but recommended)
Key: DB_MAX_CONNECTIONS
Value: 3

# 7. LOGGING LEVEL (Optional)
Key: LOG_LEVEL
Value: INFO

# 8. EMAIL VERIFICATION (Optional)
Key: EMAIL_VERIFICATION_ENABLED
Value: false
```

---

## 🚀 **STEP-BY-STEP RENDER DEPLOYMENT**

### Step 1: Create Render Web Service
1. Go to https://dashboard.render.com
2. Click "New +" → "Web Service"
3. Connect GitHub and select: `AnasInaam/collage-erp`

### Step 2: Configure Service
```
Name: college-erp-system
Runtime: Java
Build Command: mvn clean package -DskipTests
Start Command: java -Dserver.port=$PORT -jar target/college-erp-1.0.0.jar
```

### Step 3: Add Environment Variables
In the "Environment Variables" section, add each variable from the list above:

**Click "Add Environment Variable" 8 times and enter:**

1. **DATABASE_URL**: `postgresql://postgres:TUr4J0e3ydDzoP1u@db.pcrowyvrakgsxahdaade.supabase.co:5432/postgres`
2. **SPRING_PROFILES_ACTIVE**: `prod`
3. **PORT**: `10000`
4. **DDL_AUTO**: `update`
5. **JWT_SECRET**: `B16bE27V7x3RqFHmKbEUWA0WK1O717htLFqHv7rDMfPJzarXH5fIgHm3NyE7e9oc` *(or generate your own)*
6. **DB_MAX_CONNECTIONS**: `3`
7. **LOG_LEVEL**: `INFO`
8. **EMAIL_VERIFICATION_ENABLED**: `false`

### Step 4: Deploy
Click "Create Web Service" and watch the deployment!

---

## ✅ **VERIFY YOUR DEPLOYMENT**

### 1. Health Check
After deployment completes, test:
```bash
curl https://college-erp-system.onrender.com/actuator/health
```
**Expected response**: `{"status":"UP"}`

### 2. Check Supabase Tables
1. Go to your Supabase dashboard
2. Click "Table Editor"
3. You should see tables automatically created:
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

### 3. Test User Registration
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

---

## 🔐 **GENERATE SECURE JWT SECRET**

For better security, generate your own JWT secret:
```powershell
.\generate-jwt-secret-simple.ps1
```

Then replace the JWT_SECRET value with your generated secret.

---

## 🎯 **DEPLOYMENT TIMELINE**

- **Build Time**: ~5-10 minutes (first deployment)
- **Database Connection**: Automatic (Supabase)
- **Schema Creation**: Automatic (Spring Boot JPA)
- **Health Check**: Should pass immediately after startup

---

## 🚨 **TROUBLESHOOTING**

### If Health Check Fails:
1. Check Render logs for errors
2. Verify DATABASE_URL is exactly as shown above
3. Ensure Supabase project is active
4. Check all environment variables are set

### If Database Connection Fails:
1. Test connection from Supabase dashboard
2. Verify password is correct: `TUr4J0e3ydDzoP1u`
3. Check host: `db.pcrowyvrakgsxahdaade.supabase.co`

### If Tables Aren't Created:
1. Verify DDL_AUTO=update is set
2. Check application logs in Render
3. Ensure database user has permissions

---

## 💰 **COST: COMPLETELY FREE!**

- **Supabase PostgreSQL**: FREE (500MB)
- **Render Web Service**: FREE (with sleep after 15min inactivity)
- **Total**: $0/month

---

## 🎉 **SUCCESS!**

Once deployed, your College ERP will be live at:
```
https://college-erp-system.onrender.com
```

Your application will automatically:
- 🗄️ Connect to Supabase PostgreSQL
- 🔧 Create all database tables
- 🔐 Set up JWT authentication
- 📊 Enable health monitoring
- 🔒 Use HTTPS encryption

**Ready to deploy? Follow the steps above and your College ERP will be live in ~10 minutes!** 🚀