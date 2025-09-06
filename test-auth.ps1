# Test Role-Based Authentication Script
Write-Host "🧪 Testing College ERP Role-Based Authentication" -ForegroundColor Green
Write-Host "=================================================" -ForegroundColor Green

# Base URL
$baseUrl = "http://localhost:8080"

# Test credentials for different roles
$testUsers = @(
    @{ username = "admin"; password = "admin123"; expectedRole = "ROLE_ADMIN" },
    @{ username = "student"; password = "student123"; expectedRole = "ROLE_STUDENT" },
    @{ username = "faculty"; password = "faculty123"; expectedRole = "ROLE_FACULTY" }
)

Write-Host "`n🔍 Testing API Health..." -ForegroundColor Yellow
try {
    $health = Invoke-RestMethod -Uri "$baseUrl/actuator/health" -Method GET
    Write-Host "✅ API Health: $($health.status)" -ForegroundColor Green
} catch {
    Write-Host "❌ API Health Check Failed: $($_.Exception.Message)" -ForegroundColor Red
    exit 1
}

Write-Host "`n🔐 Testing Authentication for each role..." -ForegroundColor Yellow

foreach ($user in $testUsers) {
    Write-Host "`n📝 Testing login for: $($user.username)" -ForegroundColor Cyan
    
    try {
        # Prepare login request
        $loginBody = @{
            username = $user.username
            password = $user.password
        } | ConvertTo-Json
        
        # Test login
        $loginResponse = Invoke-RestMethod -Uri "$baseUrl/api/auth/signin" -Method POST -Body $loginBody -ContentType "application/json"
        
        Write-Host "✅ Login successful for $($user.username)" -ForegroundColor Green
        Write-Host "   🆔 User ID: $($loginResponse.id)" -ForegroundColor White
        Write-Host "   👤 Username: $($loginResponse.username)" -ForegroundColor White
        Write-Host "   📧 Email: $($loginResponse.email)" -ForegroundColor White
        Write-Host "   🏷️  Roles: $($loginResponse.roles -join ', ')" -ForegroundColor White
        Write-Host "   🔑 JWT Token: $($loginResponse.token.Substring(0, 50))..." -ForegroundColor White
        
        # Verify role
        if ($loginResponse.roles -contains $user.expectedRole) {
            Write-Host "   ✅ Expected role verified: $($user.expectedRole)" -ForegroundColor Green
        } else {
            Write-Host "   ❌ Role mismatch. Expected: $($user.expectedRole), Got: $($loginResponse.roles -join ', ')" -ForegroundColor Red
        }
        
        # Test accessing a protected endpoint
        $headers = @{
            "Authorization" = "Bearer $($loginResponse.token)"
        }
        
        try {
            $userInfo = Invoke-RestMethod -Uri "$baseUrl/api/auth/me" -Method GET -Headers $headers
            Write-Host "   ✅ Protected endpoint access successful" -ForegroundColor Green
        } catch {
            Write-Host "   ❌ Protected endpoint access failed: $($_.Exception.Message)" -ForegroundColor Red
        }
        
    } catch {
        Write-Host "❌ Login failed for $($user.username): $($_.Exception.Message)" -ForegroundColor Red
    }
}

Write-Host "`n🎯 Testing Role-Based Access Control..." -ForegroundColor Yellow

# Test admin access to dashboard
try {
    $adminLogin = @{
        username = "admin"
        password = "admin123"
    } | ConvertTo-Json
    
    $adminResponse = Invoke-RestMethod -Uri "$baseUrl/api/auth/signin" -Method POST -Body $adminLogin -ContentType "application/json"
    $adminHeaders = @{
        "Authorization" = "Bearer $($adminResponse.token)"
    }
    
    # Test dashboard access (admin only)
    try {
        $dashboardStats = Invoke-RestMethod -Uri "$baseUrl/api/dashboard/stats" -Method GET -Headers $adminHeaders
        Write-Host "✅ Admin dashboard access successful" -ForegroundColor Green
        Write-Host "   📊 Total Users: $($dashboardStats.totalUsers)" -ForegroundColor White
    } catch {
        Write-Host "⚠️  Admin dashboard access test: $($_.Exception.Message)" -ForegroundColor Yellow
    }
    
} catch {
    Write-Host "❌ Admin dashboard test failed: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "`n🏁 Authentication Testing Complete!" -ForegroundColor Green
Write-Host "=================================================" -ForegroundColor Green
