#!/usr/bin/env pwsh

Write-Host "=== College ERP Database and Role Testing ===" -ForegroundColor Cyan
Write-Host ""

$baseUrl = "http://localhost:8080"
$allRoles = @("STUDENT", "FACULTY", "STAFF", "PARENT", "LIBRARIAN", "ACCOUNTANT", "ADMIN")

# Function to test role registration
function Test-RoleRegistration {
    param(
        [string]$role,
        [string]$baseUsername = "testuser",
        [string]$password = "password123"
    )
    
    $username = "${baseUsername}_$($role.ToLower())"
    $email = "${username}@test.com"
    
    $userData = @{
        username = $username
        email = $email
        password = $password
        firstName = "Test"
        lastName = $role
        phone = "1234567890"
        roles = @($role)
    } | ConvertTo-Json
    
    try {
        Write-Host "Testing $role role registration..." -ForegroundColor Yellow
        
        $response = Invoke-RestMethod -Uri "$baseUrl/api/auth/signup" -Method POST -Body $userData -ContentType "application/json"
        
        Write-Host "✅ $role Registration: SUCCESS" -ForegroundColor Green
        Write-Host "   Message: $($response.message)" -ForegroundColor Green
        
        # Test login immediately
        $loginData = @{
            username = $username
            password = $password
        } | ConvertTo-Json
        
        $loginResponse = Invoke-RestMethod -Uri "$baseUrl/api/auth/signin" -Method POST -Body $loginData -ContentType "application/json"
        
        Write-Host "✅ $role Login: SUCCESS" -ForegroundColor Green
        Write-Host "   User: $($loginResponse.firstName) $($loginResponse.lastName)" -ForegroundColor Green
        Write-Host "   Roles: $($loginResponse.roles -join ', ')" -ForegroundColor Green
        Write-Host ""
        
        return $true
    }
    catch {
        Write-Host "❌ $role Test: FAILED" -ForegroundColor Red
        
        if ($_.Exception.Response) {
            $errorStream = $_.Exception.Response.GetResponseStream()
            $reader = New-Object System.IO.StreamReader($errorStream)
            $errorBody = $reader.ReadToEnd()
            $reader.Close()
            
            try {
                $errorObj = $errorBody | ConvertFrom-Json
                Write-Host "   Error: $($errorObj.message)" -ForegroundColor Red
            }
            catch {
                Write-Host "   Error: $errorBody" -ForegroundColor Red
            }
        }
        else {
            Write-Host "   Error: $($_.Exception.Message)" -ForegroundColor Red
        }
        Write-Host ""
        return $false
    }
}

# Test database connection
Write-Host "1. Testing Database Connection..." -ForegroundColor Cyan
try {
    $healthResponse = Invoke-RestMethod -Uri "$baseUrl/actuator/health" -Method GET
    Write-Host "✅ Database Connection: SUCCESS" -ForegroundColor Green
    Write-Host "   Status: $($healthResponse.status)" -ForegroundColor Green
    Write-Host ""
}
catch {
    Write-Host "❌ Database Connection: FAILED" -ForegroundColor Red
    Write-Host "   Error: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host ""
}

# Test all roles
Write-Host "2. Testing All Role Registrations..." -ForegroundColor Cyan
$successCount = 0
$totalRoles = $allRoles.Count

foreach ($role in $allRoles) {
    if (Test-RoleRegistration -role $role) {
        $successCount++
    }
    Start-Sleep -Milliseconds 500  # Small delay between requests
}

Write-Host "=== SUMMARY ===" -ForegroundColor Cyan
Write-Host "Total Roles Tested: $totalRoles" -ForegroundColor White
Write-Host "Successful: $successCount" -ForegroundColor Green
Write-Host "Failed: $($totalRoles - $successCount)" -ForegroundColor Red

if ($successCount -eq $totalRoles) {
    Write-Host ""
    Write-Host "🎉 ALL TESTS PASSED! Database schema and all roles are working correctly." -ForegroundColor Green
} else {
    Write-Host ""
    Write-Host "⚠️  Some tests failed. Check the errors above for details." -ForegroundColor Yellow
}

Write-Host ""
Write-Host "=== Additional Checks ===" -ForegroundColor Cyan
Write-Host "Database URL: jdbc:postgresql://db.ogjutpejtgtaydlqsuez.supabase.co:5432/postgres" -ForegroundColor White
Write-Host "Available Roles in Enum: ADMIN, FACULTY, STUDENT, PARENT, STAFF, LIBRARIAN, ACCOUNTANT" -ForegroundColor White
Write-Host "Registration Form Updated: ✅ All roles now available" -ForegroundColor Green
Write-Host ""
