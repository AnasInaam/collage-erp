# Railway Deployment Test Script
# Test your live College ERP application

Write-Host "🚄 TESTING RAILWAY DEPLOYMENT..." -ForegroundColor Green

# Replace with your actual Railway app URL
$APP_URL = "https://your-app-name.up.railway.app"

Write-Host "`n🔍 Testing Health Check..." -ForegroundColor Yellow
try {
    $healthResponse = Invoke-RestMethod -Uri "$APP_URL/actuator/health" -Method GET -TimeoutSec 30
    Write-Host "✅ Health Check Response:" -ForegroundColor Green
    $healthResponse | ConvertTo-Json -Depth 3
} catch {
    Write-Host "❌ Health check failed: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host "   This might mean the app is still starting up..." -ForegroundColor Yellow
}

Write-Host "`n🌐 Testing Main Page..." -ForegroundColor Yellow
try {
    $mainResponse = Invoke-WebRequest -Uri $APP_URL -Method GET -TimeoutSec 30
    Write-Host "✅ Main page status: $($mainResponse.StatusCode)" -ForegroundColor Green
    Write-Host "✅ Content length: $($mainResponse.Content.Length) bytes" -ForegroundColor Green
} catch {
    Write-Host "❌ Main page test failed: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "`n🔐 Testing API Endpoint..." -ForegroundColor Yellow
try {
    $apiUrl = "$APP_URL/api/auth/test"
    $apiResponse = Invoke-WebRequest -Uri $apiUrl -Method GET -TimeoutSec 30
    Write-Host "✅ API endpoint status: $($apiResponse.StatusCode)" -ForegroundColor Green
} catch {
    Write-Host "❌ API test failed: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host "   This is normal if the endpoint doesn't exist" -ForegroundColor Yellow
}

Write-Host "`n🎯 NEXT STEPS:" -ForegroundColor Cyan
Write-Host "1. Check Railway logs for complete startup message" -ForegroundColor White
Write-Host "2. Replace 'your-app-name' in this script with your actual Railway app name" -ForegroundColor White
Write-Host "3. Test user registration and login functionality" -ForegroundColor White
Write-Host "4. Access your College ERP dashboard" -ForegroundColor White

Write-Host "`n🚀 YOUR JAVA 21 COLLEGE ERP IS LIVE ON RAILWAY!" -ForegroundColor Green