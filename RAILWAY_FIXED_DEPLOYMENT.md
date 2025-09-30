# 🚄 RAILWAY DEPLOYMENT - CRASH FIXES APPLIED

## 🔧 **FIXES APPLIED TO YOUR PROJECT**

I've added configuration files to prevent Railway deployment crashes:

### **Files Added:**
1. **`railway.json`** - Railway deployment configuration
2. **`nixpacks.toml`** - Explicit Java 21 and Maven setup
3. **`RAILWAY_TROUBLESHOOTING.md`** - Troubleshooting guide

---

## 🚀 **DEPLOY TO RAILWAY (FIXED VERSION)**

### **Step 1: Commit & Push Fixes**
```bash
git add .
git commit -m "Add Railway deployment configuration files"
git push origin main
```

### **Step 2: Deploy to Railway**
1. **Go to**: https://railway.app
2. **Sign in with GitHub**
3. **Click "Deploy from GitHub repo"**
4. **Select**: `AnasInaam/collage-erp`
5. **Railway will now use the configuration files**

### **Step 3: Add PostgreSQL**
1. **In Railway project dashboard**
2. **Click "New Service"**
3. **Database** → **"Add PostgreSQL"**
4. **Railway auto-connects it to your app**

### **Step 4: Set Environment Variables**
Click on your web service → **Variables** tab:

```bash
# Required Variables
SPRING_PROFILES_ACTIVE=prod
JWT_SECRET=jzXeEuq9lCDL3W29Kn1yLl3Mac2Kp9H5LlnCyg1PMJK4ox049pZ2wF2k3GSSOTr2
DDL_AUTO=update
LOG_LEVEL=INFO
DB_MAX_CONNECTIONS=5

# Optional (for debugging if needed)
JAVA_VERSION=21
SHOW_SQL=false
EMAIL_VERIFICATION_ENABLED=false
```

---

## 🔍 **CONFIGURATION DETAILS**

### **railway.json** ensures:
- ✅ Correct build command with Maven
- ✅ Proper start command with port binding
- ✅ Restart policy for failed deployments

### **nixpacks.toml** ensures:
- ✅ Java 21 is used (not default Java 11/17)
- ✅ Maven is available during build
- ✅ Correct JAR execution

---

## 🧪 **VERIFY DEPLOYMENT**

After deployment, test:

### **1. Health Check**
```bash
curl https://your-app-name.up.railway.app/actuator/health
```
**Expected**: `{"status":"UP"}`

### **2. Database Tables**
Railway PostgreSQL should automatically create all tables.

### **3. User Registration**
```bash
curl -X POST https://your-app-name.up.railway.app/api/auth/register \
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

## 🚨 **IF STILL CRASHES**

### **Check Railway Logs**:
1. **Railway Dashboard** → **Your Service**
2. **"Deployments"** tab
3. **Click failed deployment**
4. **Check build and runtime logs**

### **Common Fix Commands**:
In Railway service settings:

**Build Command**:
```bash
mvn clean package -DskipTests -Dmaven.test.skip=true
```

**Start Command**:
```bash
java -Dserver.port=$PORT -jar target/college-erp-1.0.0.jar
```

---

## 💡 **ALTERNATIVE: USE DOCKERFILE**

If nixpacks still has issues, Railway can use your existing Dockerfile:

1. **In Railway service settings**
2. **Build Method**: "Dockerfile"
3. **Dockerfile Path**: "./Dockerfile"

Your Dockerfile is already configured for Java 21!

---

## 🎯 **NEXT STEPS**

1. **Commit the configuration files** (I'll help you)
2. **Try deployment again**
3. **If it crashes, share the logs** and I'll provide specific fixes

**Let's get your College ERP deployed successfully!** 🚀