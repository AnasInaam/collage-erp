# 🔧 How to Set Environment Variables in Render

## 📋 Environment Variables You Need

```bash
DATABASE_URL=<your-postgresql-url>
SPRING_PROFILES_ACTIVE=prod
PORT=10000
DDL_AUTO=update
JWT_SECRET=your-secure-secret
```

---

## 🗄️ **STEP 1: CREATE POSTGRESQL DATABASE FIRST**

### 1.1 Go to Render Dashboard
1. **Open**: https://dashboard.render.com
2. **Login** with your GitHub account
3. **Click "New +"** button
4. **Select "PostgreSQL"**

### 1.2 Configure Database
```
Name: college-erp-database
Database: college_erp
User: college_erp_user
Region: Oregon (US West) [or closest to you]
PostgreSQL Version: 15
Plan: Free (for testing) or Starter ($7/month for production)
```

### 1.3 Get Your DATABASE_URL
After creating the database, you'll see:
```
✅ External Database URL: 
postgresql://college_erp_user:XXX_PASSWORD_XXX@dpg-XXXXX.oregon-postgres.render.com:5432/college_erp
```

**⚠️ COPY THIS URL!** You'll need it for the environment variables.

---

## 🌐 **STEP 2: CREATE WEB SERVICE**

### 2.1 Create Web Service
1. **Click "New +"** again
2. **Select "Web Service"**
3. **Connect GitHub** and select your repository: `AnasInaam/collage-erp`
4. **Configure Basic Settings**:
   ```
   Name: college-erp-system
   Region: Oregon (US West) [same as database]
   Branch: main
   Root Directory: [leave empty]
   ```

### 2.2 Configure Build & Deploy
```
Runtime: Java
Build Command: mvn clean package -DskipTests
Start Command: java -Dserver.port=$PORT -jar target/college-erp-1.0.0.jar
```

---

## ⚙️ **STEP 3: SET ENVIRONMENT VARIABLES**

### 3.1 Access Environment Variables
1. **Don't click "Create Web Service" yet!**
2. **Scroll down** to "Environment Variables" section
3. **Click "Add Environment Variable"** for each variable

### 3.2 Add Each Variable

#### Variable 1: DATABASE_URL
```
Key: DATABASE_URL
Value: postgresql://college_erp_user:XXX_PASSWORD_XXX@dpg-XXXXX.oregon-postgres.render.com:5432/college_erp
```
*Replace with your actual External Database URL from Step 1.3*

#### Variable 2: SPRING_PROFILES_ACTIVE
```
Key: SPRING_PROFILES_ACTIVE
Value: prod
```

#### Variable 3: PORT
```
Key: PORT
Value: 10000
```

#### Variable 4: DDL_AUTO
```
Key: DDL_AUTO
Value: update
```

#### Variable 5: JWT_SECRET
```
Key: JWT_SECRET
Value: myVerySecureJWTSecretKeyThatIsAtLeast256BitsLongForHMACAlgorithmSecurity123456789RenderDeployment2024
```
*Make this even more secure with random characters*

### 3.3 Optional Variables (Recommended)
```
Key: DB_MAX_CONNECTIONS
Value: 3

Key: LOG_LEVEL
Value: INFO

Key: EMAIL_VERIFICATION_ENABLED
Value: false
```

---

## 🎯 **VISUAL GUIDE: Where to Add Environment Variables**

### In Render Web Service Creation:

```
┌─────────────────────────────────────────┐
│ Create Web Service                      │
├─────────────────────────────────────────┤
│ Repository: AnasInaam/collage-erp       │
│ Branch: main                            │
│ Name: college-erp-system                │
│ Runtime: Java                           │
│ Build Command: mvn clean package...     │
│ Start Command: java -Dserver.port...    │
├─────────────────────────────────────────┤
│ 🔧 Environment Variables                │
├─────────────────────────────────────────┤
│ + Add Environment Variable              │
│                                         │
│ Key: DATABASE_URL                       │
│ Value: postgresql://college_erp_user... │
│                                         │
│ + Add Environment Variable              │
│                                         │
│ Key: SPRING_PROFILES_ACTIVE             │
│ Value: prod                             │
│                                         │
│ + Add Environment Variable              │
│                                         │
│ Key: PORT                               │
│ Value: 10000                            │
│                                         │
│ [Continue for all variables...]         │
└─────────────────────────────────────────┘
```

---

## 🔄 **ALTERNATIVE: Use render.yaml (Automatic Setup)**

If you want to automate this, your `render.yaml` file already contains the configuration:

```yaml
services:
  - type: web
    name: college-erp-system
    runtime: java
    buildCommand: mvn clean package -DskipTests
    startCommand: java -Dserver.port=$PORT -jar target/college-erp-1.0.0.jar
    envVars:
      - key: DATABASE_URL
        fromDatabase:
          name: college-erp-db
          property: connectionString
      - key: SPRING_PROFILES_ACTIVE
        value: prod
      - key: PORT
        value: 10000
      - key: DDL_AUTO
        value: update
      - key: JWT_SECRET
        value: myVerySecureJWTSecretKeyThatIsAtLeast256BitsLongForHMACAlgorithmSecurity123456789
    healthCheckPath: /actuator/health

databases:
  - name: college-erp-db
    databaseName: college_erp
    user: college_erp_user
```

### To Use render.yaml:
1. **Commit and push** your `render.yaml` to GitHub
2. **In Render dashboard**, click "New +" → "Blueprint"
3. **Connect your repository** and Render will automatically create both database and web service with all environment variables!

---

## 🧪 **STEP 4: VERIFY ENVIRONMENT VARIABLES**

### 4.1 After Creating Web Service
1. **Go to your web service dashboard**
2. **Click "Environment" tab**
3. **Verify all variables are set correctly**

### 4.2 Check Database Connection
Your DATABASE_URL should look like:
```
postgresql://college_erp_user:a1b2c3d4e5f6@dpg-ch1abc2def3ghi.oregon-postgres.render.com:5432/college_erp
```

**Components breakdown:**
- `college_erp_user` - Database username
- `a1b2c3d4e5f6` - Database password (auto-generated)
- `dpg-ch1abc2def3ghi.oregon-postgres.render.com` - Database host
- `5432` - PostgreSQL port
- `college_erp` - Database name

---

## 🚀 **STEP 5: DEPLOY**

### 5.1 Start Deployment
1. **Click "Create Web Service"** (if using manual setup)
2. **Or click "Deploy"** (if using Blueprint)
3. **Watch build logs** in real-time

### 5.2 Monitor Deployment
```
Expected log output:
[INFO] BUILD SUCCESS
[INFO] Total time: XX.XXX s
Started CollegeErpApplication in XX.XXX seconds
Tomcat started on port(s): 10000 (http)
```

---

## ✅ **STEP 6: TEST YOUR DEPLOYMENT**

### 6.1 Health Check
```bash
curl https://college-erp-system.onrender.com/actuator/health
```
**Expected response:**
```json
{"status":"UP"}
```

### 6.2 Test Database Connection
If health check passes, your database connection is working!

### 6.3 Test API
```bash
# Test user registration
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

## 🔧 **TROUBLESHOOTING**

### Issue 1: Database Connection Failed
**Check:**
- DATABASE_URL is correct (use External Database URL)
- Database and web service are in the same region
- Database is running (green status in Render dashboard)

### Issue 2: Environment Variable Not Working
**Solutions:**
- Make sure variable names are exact (case-sensitive)
- No extra spaces in values
- Redeploy after changing environment variables

### Issue 3: Build Fails
**Check:**
- Java version is 21
- Build command is correct: `mvn clean package -DskipTests`
- No compilation errors

### Issue 4: Application Won't Start
**Check:**
- PORT environment variable is set to 10000
- Start command has correct JAR file name
- All required environment variables are present

---

## 📝 **ENVIRONMENT VARIABLES CHECKLIST**

Before deploying, verify you have:

- [ ] ✅ DATABASE_URL (from PostgreSQL database)
- [ ] ✅ SPRING_PROFILES_ACTIVE=prod
- [ ] ✅ PORT=10000
- [ ] ✅ DDL_AUTO=update
- [ ] ✅ JWT_SECRET (secure 256-bit string)
- [ ] ✅ DB_MAX_CONNECTIONS=3 (optional)
- [ ] ✅ LOG_LEVEL=INFO (optional)

---

## 🎉 **SUCCESS!**

Once deployed successfully, your College ERP will be live at:
```
https://college-erp-system.onrender.com
```

The application will automatically:
- 🗄️ Create database tables
- 🔐 Set up JWT authentication
- 📊 Enable health monitoring
- 🔒 Use HTTPS