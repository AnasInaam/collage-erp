# PostgreSQL Compatibility Test Script for Windows
# This script tests if your application can connect to PostgreSQL

Write-Host "🧪 Testing PostgreSQL Compatibility..." -ForegroundColor Green

# Test 1: Check if PostgreSQL driver is in classpath
Write-Host "📦 Checking PostgreSQL driver..." -ForegroundColor Yellow
$mvnOutput = mvn dependency:tree 2>$null
if ($mvnOutput -match "postgresql") {
    Write-Host "✅ PostgreSQL driver found" -ForegroundColor Green
} else {
    Write-Host "❌ PostgreSQL driver missing" -ForegroundColor Red
}

# Test 2: Validate application properties
Write-Host "⚙️ Validating application properties..." -ForegroundColor Yellow
$prodProps = Get-Content "src\main\resources\application-prod.properties" -ErrorAction SilentlyContinue
if ($prodProps -match "org.postgresql.Driver") {
    Write-Host "✅ PostgreSQL driver configured" -ForegroundColor Green
} else {
    Write-Host "❌ PostgreSQL driver not configured" -ForegroundColor Red
}

# Test 3: Check for H2-specific configurations that might conflict
Write-Host "🔍 Checking for H2 conflicts..." -ForegroundColor Yellow
if ($prodProps -match "h2") {
    Write-Host "⚠️ H2 references found in production config" -ForegroundColor Yellow
} else {
    Write-Host "✅ No H2 conflicts in production" -ForegroundColor Green
}

# Test 4: Build the application
Write-Host "🔨 Building application..." -ForegroundColor Yellow
try {
    mvn clean compile -q 2>$null
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ Build successful" -ForegroundColor Green
    } else {
        Write-Host "❌ Build failed" -ForegroundColor Red
    }
} catch {
    Write-Host "❌ Build failed" -ForegroundColor Red
}

# Test 5: Check entity annotations for PostgreSQL compatibility
Write-Host "🏗️ Checking entity annotations..." -ForegroundColor Yellow
$entityFiles = Get-ChildItem -Path "src\main\java" -Filter "*.java" -Recurse
$identityGenFound = $false
foreach ($file in $entityFiles) {
    $content = Get-Content $file.FullName
    if ($content -match "GenerationType.IDENTITY") {
        $identityGenFound = $true
        break
    }
}

if ($identityGenFound) {
    Write-Host "✅ Using IDENTITY generation (PostgreSQL compatible)" -ForegroundColor Green
} else {
    Write-Host "⚠️ Check ID generation strategy" -ForegroundColor Yellow
}

Write-Host "✨ PostgreSQL compatibility check complete!" -ForegroundColor Green

# Instructions for manual testing
Write-Host ""
Write-Host "🚀 Manual Testing Instructions:" -ForegroundColor Cyan
Write-Host "1. Start a local PostgreSQL instance" -ForegroundColor White
Write-Host "2. Create database: CREATE DATABASE college_erp_test;" -ForegroundColor White  
Write-Host "3. Update application-test.properties with PostgreSQL URL" -ForegroundColor White
Write-Host "4. Run: mvn spring-boot:run -Dspring-boot.run.profiles=test" -ForegroundColor White
Write-Host "5. Check if tables are created automatically" -ForegroundColor White

Write-Host ""
Write-Host "📋 Environment Variables for Render:" -ForegroundColor Cyan
Write-Host "DATABASE_URL=postgresql://user:pass@host:5432/college_erp" -ForegroundColor Gray
Write-Host "SPRING_PROFILES_ACTIVE=prod" -ForegroundColor Gray
Write-Host "DDL_AUTO=update" -ForegroundColor Gray
Write-Host "JWT_SECRET=your-secure-secret" -ForegroundColor Gray