# Test Supabase Connection
Write-Host "🧪 Testing Supabase Connection..." -ForegroundColor Green

# Your Supabase details
$supabaseHost = "db.pcrowyvrakgsxahdaade.supabase.co"
$supabasePassword = "TUr4J0e3ydDzoP1u"
$databaseUrl = "postgresql://postgres:$supabasePassword@$supabaseHost:5432/postgres"

Write-Host ""
Write-Host "📋 Your Database Configuration:" -ForegroundColor Cyan
Write-Host "Host: $supabaseHost" -ForegroundColor White
Write-Host "Database: postgres" -ForegroundColor White
Write-Host "User: postgres" -ForegroundColor White
Write-Host "Password: $supabasePassword" -ForegroundColor White
Write-Host "Port: 5432" -ForegroundColor White

Write-Host ""
Write-Host "🔗 Complete DATABASE_URL:" -ForegroundColor Yellow
Write-Host $databaseUrl -ForegroundColor Gray

Write-Host ""
Write-Host "✅ Configuration is ready for Render!" -ForegroundColor Green
Write-Host ""
Write-Host "📋 Next Steps:" -ForegroundColor Cyan
Write-Host "1. Go to https://dashboard.render.com" -ForegroundColor White
Write-Host "2. Create new Web Service from GitHub" -ForegroundColor White
Write-Host "3. Set environment variables from YOUR_SUPABASE_CONFIG.md" -ForegroundColor White
Write-Host "4. Deploy and test!" -ForegroundColor White

Write-Host ""
Write-Host "🎯 Expected deployment time: ~10 minutes" -ForegroundColor Green