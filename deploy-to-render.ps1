#!/usr/bin/env pwsh

# Render Deployment Script for College ERP
# This script prepares your project for Render deployment

Write-Host "🚀 Preparing College ERP for Render Deployment..." -ForegroundColor Green

# Step 1: Verify build
Write-Host "Building project..." -ForegroundColor Yellow
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21"
$env:M2_HOME = "C:\apache-maven-3.9.11" 
$env:PATH = "$env:JAVA_HOME\bin;$env:M2_HOME\bin;$env:PATH"

try {
    mvn clean package -DskipTests
    if ($LASTEXITCODE -eq 0) {
        Write-Host "Build successful!" -ForegroundColor Green
    } else {
        Write-Host "Build failed. Please fix build errors before deploying." -ForegroundColor Red
        exit 1
    }
} catch {
    Write-Host "Build failed. Maven not configured properly." -ForegroundColor Red
    exit 1
}

# Step 2: Verify PostgreSQL dependency
Write-Host "📦 Checking PostgreSQL dependency..." -ForegroundColor Yellow
$depTree = mvn dependency:tree 2>$null
if ($depTree -match "postgresql") {
    Write-Host "✅ PostgreSQL driver found" -ForegroundColor Green
} else {
    Write-Host "❌ PostgreSQL driver missing" -ForegroundColor Red
    exit 1
}

# Step 3: Check render.yaml
Write-Host "📄 Checking render.yaml..." -ForegroundColor Yellow
if (Test-Path "render.yaml") {
    Write-Host "✅ render.yaml found" -ForegroundColor Green
} else {
    Write-Host "❌ render.yaml missing" -ForegroundColor Red
    exit 1
}

# Step 4: Verify production properties
Write-Host "⚙️ Checking production configuration..." -ForegroundColor Yellow
$prodProps = Get-Content "src\main\resources\application-prod.properties" -ErrorAction SilentlyContinue
if ($prodProps -match "postgresql") {
    Write-Host "✅ PostgreSQL configured in production" -ForegroundColor Green
} else {
    Write-Host "❌ PostgreSQL not configured in production properties" -ForegroundColor Red
    exit 1
}

# Step 5: Git status check
Write-Host "📁 Checking git status..." -ForegroundColor Yellow
$gitStatus = git status --porcelain 2>$null
if ($gitStatus) {
    Write-Host "⚠️ Uncommitted changes detected. Consider committing before deployment." -ForegroundColor Yellow
    Write-Host "Files with changes:" -ForegroundColor Gray
    git status --short
} else {
    Write-Host "✅ Git repository is clean" -ForegroundColor Green
}

# Deployment instructions
Write-Host ""
Write-Host "🎯 Your project is ready for Render deployment!" -ForegroundColor Green
Write-Host ""
Write-Host "Next steps:" -ForegroundColor Cyan
Write-Host "1. Push your code to GitHub if you haven't already" -ForegroundColor White
Write-Host "2. Go to https://dashboard.render.com" -ForegroundColor White
Write-Host "3. Create PostgreSQL database (see RENDER_COMPLETE_GUIDE.md)" -ForegroundColor White
Write-Host "4. Create web service from your GitHub repo" -ForegroundColor White
Write-Host "5. Set environment variables:" -ForegroundColor White
Write-Host ""
Write-Host "   Required Environment Variables:" -ForegroundColor Yellow
Write-Host "   DATABASE_URL=<your-postgresql-url>" -ForegroundColor Gray
Write-Host "   SPRING_PROFILES_ACTIVE=prod" -ForegroundColor Gray
Write-Host "   PORT=10000" -ForegroundColor Gray
Write-Host "   DDL_AUTO=update" -ForegroundColor Gray
Write-Host "   JWT_SECRET=your-secure-secret" -ForegroundColor Gray
Write-Host ""
Write-Host "📖 Full deployment guide: RENDER_COMPLETE_GUIDE.md" -ForegroundColor Cyan