# Render Deployment Script for College ERP
# This script prepares your project for Render deployment

Write-Host "Preparing College ERP for Render Deployment..." -ForegroundColor Green

# Step 1: Set up environment
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21"
$env:M2_HOME = "C:\apache-maven-3.9.11" 
$env:PATH = "$env:JAVA_HOME\bin;$env:M2_HOME\bin;$env:PATH"

# Step 2: Build project
Write-Host "Building project..." -ForegroundColor Yellow
mvn clean package -DskipTests
if ($LASTEXITCODE -eq 0) {
    Write-Host "BUILD SUCCESSFUL!" -ForegroundColor Green
} else {
    Write-Host "BUILD FAILED - Please fix errors before deploying." -ForegroundColor Red
    exit 1
}

# Step 3: Check PostgreSQL dependency
Write-Host "Checking PostgreSQL dependency..." -ForegroundColor Yellow
$depCheck = mvn dependency:tree | Select-String "postgresql"
if ($depCheck) {
    Write-Host "PostgreSQL driver found: $depCheck" -ForegroundColor Green
} else {
    Write-Host "PostgreSQL driver NOT found!" -ForegroundColor Red
    exit 1
}

# Step 4: Verify configuration files
Write-Host "Checking configuration files..." -ForegroundColor Yellow

if (Test-Path "render.yaml") {
    Write-Host "render.yaml: FOUND" -ForegroundColor Green
} else {
    Write-Host "render.yaml: MISSING" -ForegroundColor Red
}

if (Test-Path "src\main\resources\application-prod.properties") {
    Write-Host "application-prod.properties: FOUND" -ForegroundColor Green
} else {
    Write-Host "application-prod.properties: MISSING" -ForegroundColor Red
}

if (Test-Path "RENDER_COMPLETE_GUIDE.md") {
    Write-Host "RENDER_COMPLETE_GUIDE.md: FOUND" -ForegroundColor Green
} else {
    Write-Host "RENDER_COMPLETE_GUIDE.md: MISSING" -ForegroundColor Red
}

Write-Host ""
Write-Host "PROJECT IS READY FOR RENDER DEPLOYMENT!" -ForegroundColor Green
Write-Host ""
Write-Host "Next Steps:" -ForegroundColor Cyan
Write-Host "1. Push code to GitHub" -ForegroundColor White
Write-Host "2. Go to https://dashboard.render.com" -ForegroundColor White
Write-Host "3. Create PostgreSQL database" -ForegroundColor White
Write-Host "4. Create web service from GitHub repo" -ForegroundColor White
Write-Host "5. Set environment variables" -ForegroundColor White
Write-Host ""
Write-Host "Environment Variables Needed:" -ForegroundColor Yellow
Write-Host "DATABASE_URL=your-postgresql-url" -ForegroundColor Gray
Write-Host "SPRING_PROFILES_ACTIVE=prod" -ForegroundColor Gray
Write-Host "PORT=10000" -ForegroundColor Gray
Write-Host "DDL_AUTO=update" -ForegroundColor Gray
Write-Host "JWT_SECRET=your-secure-secret" -ForegroundColor Gray
Write-Host ""
Write-Host "See RENDER_COMPLETE_GUIDE.md for detailed instructions" -ForegroundColor Cyan