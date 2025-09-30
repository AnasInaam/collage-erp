# 🔧 RENDER ENVIRONMENT VARIABLES CONFIGURATION

## 📋 Complete Environment Variables Setup

### 🎯 **REQUIRED VARIABLES**

Copy these exact variable names and values into your Render web service:

```bash
# 1. DATABASE CONNECTION
Key: DATABASE_URL
Value: [Get this from your PostgreSQL database in Render - External Database URL]
Example: postgresql://college_erp_user:a1b2c3d4e5f6@dpg-ch1abc2def3ghi.oregon-postgres.render.com:5432/college_erp

# 2. APPLICATION PROFILE  
Key: SPRING_PROFILES_ACTIVE
Value: prod

# 3. SERVER PORT
Key: PORT
Value: 10000

# 4. DATABASE SCHEMA MANAGEMENT
Key: DDL_AUTO
Value: update

# 5. JWT SECURITY SECRET
Key: JWT_SECRET
Value: B16bE27V7x3RqFHmKbEUWA0WK1O717htLFqHv7rDMfPJzarXH5fIgHm3NyE7e9oc
# ⚠️ Use your own secure secret! Run .\generate-jwt-secret-simple.ps1 to generate one
```

### 🔧 **OPTIONAL VARIABLES (Recommended)**

```bash
# 6. DATABASE CONNECTION POOL
Key: DB_MAX_CONNECTIONS
Value: 3

# 7. LOGGING LEVEL
Key: LOG_LEVEL
Value: INFO

# 8. EMAIL VERIFICATION
Key: EMAIL_VERIFICATION_ENABLED
Value: false

# 9. SHOW SQL QUERIES (Debug)
Key: SHOW_SQL
Value: false
```

---

## 🗄️ **HOW TO GET DATABASE_URL**

### Step 1: Create PostgreSQL Database in Render
1. Go to https://dashboard.render.com
2. Click "New +" → "PostgreSQL"
3. Configure:
   ```
   Name: college-erp-database
   Database: college_erp
   User: college_erp_user
   Plan: Free
   ```

### Step 2: Copy External Database URL
After creation, you'll see something like:
```
Internal Database URL: postgresql://college_erp_user:xxx@dpg-xxx:5432/college_erp
External Database URL: postgresql://college_erp_user:a1b2c3d4e5f6@dpg-ch1abc2def3ghi.oregon-postgres.render.com:5432/college_erp
```

**✅ Use the EXTERNAL Database URL for DATABASE_URL variable**

---

## 🌐 **HOW TO SET VARIABLES IN RENDER**

### Method 1: Manual Setup (Recommended for beginners)

1. **Create Web Service**:
   - Go to Render Dashboard
   - Click "New +" → "Web Service"
   - Connect GitHub: `AnasInaam/collage-erp`

2. **Configure Service**:
   ```
   Name: college-erp-system
   Runtime: Java
   Build Command: mvn clean package -DskipTests
   Start Command: java -Dserver.port=$PORT -jar target/college-erp-1.0.0.jar
   ```

3. **Add Environment Variables**:
   - Scroll to "Environment Variables" section
   - Click "Add Environment Variable" for each variable above
   - Copy the exact Key and Value from the list above

4. **Deploy**:
   - Click "Create Web Service"

### Method 2: Automatic Setup (Using render.yaml)

Your project already includes `render.yaml`. To use it:

1. **Commit and push** all files to GitHub
2. **In Render**, click "New +" → "Blueprint"
3. **Connect repository** and Render will automatically create everything!

---

## ✅ **VERIFICATION CHECKLIST**

Before deploying, make sure you have:

- [ ] ✅ DATABASE_URL (from PostgreSQL External URL)
- [ ] ✅ SPRING_PROFILES_ACTIVE = prod
- [ ] ✅ PORT = 10000
- [ ] ✅ DDL_AUTO = update
- [ ] ✅ JWT_SECRET (64+ character secure string)
- [ ] ✅ PostgreSQL database created and running
- [ ] ✅ Web service configured with correct build commands

---

## 🧪 **TEST YOUR SETUP**

After deployment, verify with:

### 1. Health Check
```bash
curl https://college-erp-system.onrender.com/actuator/health
```
**Expected**: `{"status":"UP"}`

### 2. Database Connection Test
If health check passes, your database is connected!

### 3. Application Test
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

## 🚨 **TROUBLESHOOTING**

### Issue: Database Connection Failed
**Solution**: 
- Check DATABASE_URL is the External Database URL (not Internal)
- Verify database is in same region as web service
- Ensure database is running (green status in Render)

### Issue: Application Won't Start
**Solution**:
- Verify PORT=10000 is set
- Check all required environment variables are present
- Verify start command points to correct JAR file

### Issue: Build Fails
**Solution**:
- Test build locally: `mvn clean package -DskipTests`
- Check Java version is 21
- Verify no compilation errors

---

## 💡 **PRO TIPS**

1. **Use Blueprint (render.yaml)** for automatic setup
2. **Set JWT_SECRET first** - generate a new one with the script
3. **Use same region** for database and web service
4. **Test locally** before deploying to catch issues early
5. **Monitor logs** during first deployment

---

## 🎉 **SUCCESS!**

Once all environment variables are set correctly, your College ERP will be live at:
```
https://college-erp-system.onrender.com
```

The application will automatically:
- 🗄️ Create all database tables
- 🔐 Set up JWT authentication  
- 📊 Enable health monitoring
- 🔒 Use HTTPS encryption