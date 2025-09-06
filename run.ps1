# College ERP - PowerShell Run Script

param(
    [Parameter(Mandatory=$true)]
    [ValidateSet("dev", "dev-h2", "prod", "test", "build", "run")]
    [string]$Profile
)

# Maven executable path
$mvnCmd = "C:\apache-maven-3.9.11\bin\mvn.cmd"

Write-Host "College ERP Application Runner" -ForegroundColor Green
Write-Host "================================" -ForegroundColor Green

# Check if Maven is available
if (-not (Test-Path $mvnCmd)) {
    Write-Host "ERROR: Maven not found at $mvnCmd" -ForegroundColor Red
    Write-Host "Please install Maven or update the path in this script." -ForegroundColor Red
    exit 1
}

switch ($Profile) {
    "dev" {
        Write-Host "Running in Development mode with Supabase PostgreSQL..." -ForegroundColor Yellow
        & $mvnCmd spring-boot:run "-Dspring-boot.run.profiles=dev"
    }
    "dev-h2" {
        Write-Host "Running in Development mode with H2 database (fallback)..." -ForegroundColor Cyan
        & $mvnCmd spring-boot:run "-Dspring-boot.run.profiles=dev-h2"
    }
    "prod" {
        Write-Host "Running in Production mode with Supabase PostgreSQL..." -ForegroundColor Red
        & $mvnCmd spring-boot:run "-Dspring-boot.run.profiles=prod"
    }
    "test" {
        Write-Host "Running tests..." -ForegroundColor Cyan
        & $mvnCmd test "-Dspring.profiles.active=test"
    }
    "build" {
        Write-Host "Building application..." -ForegroundColor Magenta
        & $mvnCmd clean package -DskipTests
        if ($LASTEXITCODE -eq 0) {
            Write-Host "Build successful! JAR created: target/college-erp-1.0.0.jar" -ForegroundColor Green
        } else {
            Write-Host "Build failed with exit code: $LASTEXITCODE" -ForegroundColor Red
        }
    }
    "run" {
        Write-Host "Running built JAR in production mode..." -ForegroundColor Blue
        if (Test-Path "target/college-erp-1.0.0.jar") {
            java -jar target/college-erp-1.0.0.jar --spring.profiles.active=prod
        } else {
            Write-Host "JAR file not found. Please run: .\run.ps1 build" -ForegroundColor Red
        }
    }
}

Write-Host "`nExecution completed!" -ForegroundColor Green
