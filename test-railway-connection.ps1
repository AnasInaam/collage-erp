# Test Railway Database Connection

Write-Host "Testing Railway PostgreSQL Connection..." -ForegroundColor Green

# Your Railway DATABASE_URL
$databaseUrl = "postgresql://postgres:pMXXbwGGaqZTzECwxwBngQoWWAoisCDg@yamanote.proxy.rlwy.net:23833/railway"

Write-Host ""
Write-Host "Database Connection Details:" -ForegroundColor Yellow
Write-Host "Host: yamanote.proxy.rlwy.net" -ForegroundColor White
Write-Host "Port: 23833" -ForegroundColor White  
Write-Host "Database: railway" -ForegroundColor White
Write-Host "Username: postgres" -ForegroundColor White
Write-Host "Password: pMXXbwGGaqZTzECwxwBngQoWWAoisCDg" -ForegroundColor White

Write-Host ""
Write-Host "Complete Railway Environment Variables:" -ForegroundColor Cyan
Write-Host "DATABASE_URL=$databaseUrl" -ForegroundColor Gray
Write-Host "SPRING_PROFILES_ACTIVE=prod" -ForegroundColor Gray
Write-Host "JWT_SECRET=jzXeEuq9lCDL3W29Kn1yLl3Mac2Kp9H5LlnCyg1PMJK4ox049pZ2wF2k3GSSOTr2" -ForegroundColor Gray
Write-Host "DDL_AUTO=update" -ForegroundColor Gray
Write-Host "LOG_LEVEL=INFO" -ForegroundColor Gray
Write-Host "DB_MAX_CONNECTIONS=5" -ForegroundColor Gray
Write-Host "PORT=8080" -ForegroundColor Gray

Write-Host ""
Write-Host "Next Steps:" -ForegroundColor Green
Write-Host "1. Set these variables in Railway web service" -ForegroundColor White
Write-Host "2. Click Deploy in Railway" -ForegroundColor White
Write-Host "3. Watch logs for successful database connection" -ForegroundColor White
Write-Host "4. Test health endpoint: /actuator/health" -ForegroundColor White

Write-Host ""
Write-Host "Expected Success Log:" -ForegroundColor Yellow
Write-Host "HikariPool-1 - Added connection postgresql://postgres:pMXX...@yamanote.proxy.rlwy.net:23833/railway" -ForegroundColor Gray
Write-Host "Tomcat started on port(s): 8080 (http)" -ForegroundColor Gray