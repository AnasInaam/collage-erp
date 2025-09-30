# 🚨 Railway Deployment Crash - Troubleshooting Guide

## 🔍 **COMMON RAILWAY DEPLOYMENT ISSUES & FIXES**

### **Issue 1: Build Command Not Detected**
Railway might not detect the correct build command for Maven.

**Fix**: Set custom build command
1. Go to your Railway service → **Settings**
2. **Build Command**: `mvn clean package -DskipTests`
3. **Start Command**: `java -jar target/college-erp-1.0.0.jar`

### **Issue 2: Java Version Mismatch**
Railway might default to Java 11/17 instead of Java 21.

**Fix**: Set Java version environment variable
```bash
JAVA_VERSION=21
```

### **Issue 3: Port Configuration**
Railway auto-assigns PORT, but Spring Boot might not pick it up.

**Fix**: Ensure Spring Boot uses Railway's PORT
**Start Command**: `java -Dserver.port=$PORT -jar target/college-erp-1.0.0.jar`

### **Issue 4: Maven Wrapper Missing**
If using Maven wrapper but mvnw files are missing.

**Fix**: Use system Maven
**Build Command**: `mvn clean package -DskipTests`

### **Issue 5: Database Connection Before Tables Created**
App tries to connect before PostgreSQL is ready.

**Fix**: Add startup delay or health check
**Environment Variable**: `DDL_AUTO=create-drop` (for first deployment)

---

## 🔧 **STEP-BY-STEP FIX**

### **1. Check Railway Logs**
1. Go to Railway dashboard
2. Click on your service
3. Go to **"Deployments"** tab
4. Click on the failed deployment
5. **Check the logs** for error messages

### **2. Update Build Configuration**
In Railway service settings:

**Build Command**:
```bash
mvn clean package -DskipTests -Dmaven.test.skip=true
```

**Start Command**:
```bash
java -Dserver.port=$PORT -Djava.awt.headless=true -jar target/college-erp-1.0.0.jar
```

### **3. Set These Environment Variables**
```bash
# Java Configuration
JAVA_VERSION=21

# Application Configuration  
SPRING_PROFILES_ACTIVE=prod
DDL_AUTO=create-drop
SHOW_SQL=false
LOG_LEVEL=DEBUG

# Security
JWT_SECRET=jzXeEuq9lCDL3W29Kn1yLl3Mac2Kp9H5LlnCyg1PMJK4ox049pZ2wF2k3GSSOTr2

# Database
DB_MAX_CONNECTIONS=5

# Server
SERVER_PORT=$PORT
```

### **4. Redeploy**
1. **Trigger new deployment** in Railway
2. **Watch the logs** for build progress
3. **Check for errors** at each step

---

## 🎯 **ALTERNATIVE: CREATE railway.json CONFIG**

Let me create a Railway configuration file to ensure proper deployment:

```json
{
  "$schema": "https://railway.app/railway.schema.json",
  "build": {
    "builder": "NIXPACKS",
    "buildCommand": "mvn clean package -DskipTests"
  },
  "deploy": {
    "startCommand": "java -Dserver.port=$PORT -jar target/college-erp-1.0.0.jar",
    "restartPolicyType": "ON_FAILURE",
    "restartPolicyMaxRetries": 10
  }
}
```

---

## 🚨 **EMERGENCY ALTERNATIVE: DOCKERFILE DEPLOYMENT**

If Railway still has issues with Maven detection, we can force Docker deployment:

**Create railway.toml**:
```toml
[build]
builder = "DOCKERFILE"

[deploy]
startCommand = "java -Dserver.port=$PORT -jar target/college-erp-1.0.0.jar"
```

Your existing Dockerfile will work perfectly!

---

## 🔍 **DEBUGGING CHECKLIST**

Check Railway logs for these common errors:

- [ ] **"Java version not found"** → Set JAVA_VERSION=21
- [ ] **"mvn command not found"** → Use nixpacks or Dockerfile  
- [ ] **"Port binding failed"** → Ensure start command uses $PORT
- [ ] **"Database connection failed"** → Check PostgreSQL plugin is added
- [ ] **"JAR file not found"** → Verify build command creates JAR in target/
- [ ] **"Permission denied"** → Check file permissions

---

## 📞 **NEXT STEPS**

1. **Share the error logs** from Railway deployment
2. **I'll provide specific fix** based on the exact error
3. **We'll get your app deployed** successfully

**What error message are you seeing in Railway logs?** Let me know and I'll provide the exact fix!