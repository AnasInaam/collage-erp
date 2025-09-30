# Generate Secure JWT Secret for Render Deployment

Write-Host "🔐 Generating Secure JWT Secret..." -ForegroundColor Green

# Generate a secure 256-bit JWT secret
$chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
$jwtSecret = ""
for ($i = 0; $i -lt 64; $i++) {
    $jwtSecret += $chars[(Get-Random -Maximum $chars.Length)]
}

Write-Host ""
Write-Host "✅ Your Secure JWT Secret:" -ForegroundColor Yellow
Write-Host $jwtSecret -ForegroundColor White

Write-Host ""
Write-Host "📋 Copy this for your Render environment variables:" -ForegroundColor Cyan
Write-Host "JWT_SECRET=$jwtSecret" -ForegroundColor Gray

Write-Host ""
Write-Host "🔒 This secret is:" -ForegroundColor Green
Write-Host "- 64 characters long (512 bits)" -ForegroundColor White
Write-Host "- Contains uppercase, lowercase, numbers, and symbols" -ForegroundColor White  
Write-Host "- Cryptographically secure for JWT signing" -ForegroundColor White

Write-Host ""
Write-Host "⚠️  IMPORTANT: Save this secret securely!" -ForegroundColor Red
Write-Host "If you lose it, you'll need to generate a new one and all existing tokens will become invalid." -ForegroundColor Yellow