# Generate Secure JWT Secret for Render Deployment

Write-Host "Generating Secure JWT Secret..." -ForegroundColor Green

# Generate a secure 64-character JWT secret
$chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
$jwtSecret = ""
for ($i = 0; $i -lt 64; $i++) {
    $jwtSecret += $chars[(Get-Random -Maximum $chars.Length)]
}

Write-Host ""
Write-Host "Your Secure JWT Secret:" -ForegroundColor Yellow
Write-Host $jwtSecret -ForegroundColor White

Write-Host ""
Write-Host "Copy this for your Render environment variables:" -ForegroundColor Cyan
Write-Host "JWT_SECRET=$jwtSecret" -ForegroundColor Gray

Write-Host ""
Write-Host "This secret is 64 characters long and cryptographically secure." -ForegroundColor Green
Write-Host "IMPORTANT: Save this secret securely!" -ForegroundColor Red