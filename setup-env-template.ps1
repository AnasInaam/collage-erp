# Environment Variables Setup for PostgreSQL Production

# Copy this file to setup-env.ps1 and fill in your actual values from Render
# Then run: .\setup-env.ps1

# PostgreSQL Connection Details from Render
$env:DATABASE_URL = "jdbc:postgresql://your-host.oregon-postgres.render.com:5432/your_database_name"
$env:DB_USERNAME = "your_username"
$env:DB_PASSWORD = "your_password"

# JWT Secret (use a strong random string)
$env:JWT_SECRET = "your_very_strong_jwt_secret_key_here_minimum_32_characters"

# Spring Profile
$env:SPRING_PROFILES_ACTIVE = "prod"

# Port (Render will set this automatically)
$env:PORT = "8080"

Write-Host "Environment variables set for PostgreSQL production!" -ForegroundColor Green
Write-Host "You can now run: .\run.ps1 prod" -ForegroundColor Yellow

# To verify variables are set:
Write-Host "`nCurrent environment variables:" -ForegroundColor Cyan
Write-Host "DATABASE_URL: $($env:DATABASE_URL)" -ForegroundColor White
Write-Host "DB_USERNAME: $($env:DB_USERNAME)" -ForegroundColor White
Write-Host "DB_PASSWORD: [HIDDEN]" -ForegroundColor White
Write-Host "JWT_SECRET: [HIDDEN]" -ForegroundColor White
Write-Host "SPRING_PROFILES_ACTIVE: $($env:SPRING_PROFILES_ACTIVE)" -ForegroundColor White
Write-Host "PORT: $($env:PORT)" -ForegroundColor White
