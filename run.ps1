# College ERP - PowerShell Run Script

param(
    [Parameter(Mandatory=$true)]
    [ValidateSet("dev", "dev-h2", "prod", "test", "build")]
    [string]$Profile
)

# Add Maven to PATH if not already there
$env:PATH += ";C:\apache-maven-3.9.11\bin"

Write-Host "College ERP Application Runner" -ForegroundColor Green
Write-Host "================================" -ForegroundColor Green

switch ($Profile) {
    "dev" {
        Write-Host "Running in Development mode with Supabase PostgreSQL..." -ForegroundColor Yellow
        mvn spring-boot:run "-Dspring-boot.run.profiles=dev"
    }
    "dev-h2" {
        Write-Host "Running in Development mode with H2 database (fallback)..." -ForegroundColor Cyan
        mvn spring-boot:run "-Dspring-boot.run.profiles=dev-h2"
    }
    "prod" {
        Write-Host "Running in Production mode with Supabase PostgreSQL..." -ForegroundColor Red
        mvn spring-boot:run "-Dspring-boot.run.profiles=prod"
    }
    "test" {
        Write-Host "Running tests..." -ForegroundColor Cyan
        mvn test "-Dspring.profiles.active=test"
    }
    "build" {
        Write-Host "Building application..." -ForegroundColor Magenta
        mvn clean package -DskipTests
    }
}

Write-Host "`nExecution completed!" -ForegroundColor Green
