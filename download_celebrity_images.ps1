# PowerShell script to download celebrity images from Wikimedia Commons
# These are public domain/free use images

$assetsPath = "app\src\main\assets\celebrity_images"
$ErrorActionPreference = "Continue"

# Create directory if it doesn't exist
if (-not (Test-Path $assetsPath)) {
    New-Item -ItemType Directory -Force -Path $assetsPath | Out-Null
}

Write-Host "Downloading celebrity images from Wikimedia Commons..." -ForegroundColor Green
Write-Host ""

# Image URLs from Wikimedia Commons (public domain/free use)
# Using alternative URLs for better reliability
$images = @{
    "tom_hanks.jpg" = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a9/Tom_Hanks_TIFF_2019.jpg/400px-Tom_Hanks_TIFF_2019.jpg"
    "robert_downey_jr.jpg" = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/94/Robert_Downey_Jr_2014_Comic_Con_%28cropped%29.jpg/400px-Robert_Downey_Jr_2014_Comic_Con_%28cropped%29.jpg"
    "beyonce.jpg" = "https://upload.wikimedia.org/wikipedia/commons/thumb/1/17/Beyonc%C3%A9_at_The_Lion_King_European_Premiere_2019.png/400px-Beyonc%C3%A9_at_The_Lion_King_European_Premiere_2019.png"
    "michael_jackson.jpg" = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Michael_Jackson_1984.jpg/400px-Michael_Jackson_1984.jpg"
    "oprah_winfrey.jpg" = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9a/Oprah_in_2014.jpg/400px-Oprah_in_2014.jpg"
    "lebron_james.jpg" = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7a/LeBron_James_%2851959977144%29_%28cropped%29.jpg/400px-LeBron_James_%2851959977144%29_%28cropped%29.jpg"
}

$successCount = 0
$failCount = 0

foreach ($imageName in $images.Keys) {
    $url = $images[$imageName]
    $outputPath = Join-Path $assetsPath $imageName
    
    try {
        Write-Host "Downloading $imageName..." -ForegroundColor Yellow -NoNewline
        $response = Invoke-WebRequest -Uri $url -OutFile $outputPath -UseBasicParsing -TimeoutSec 30
        Write-Host " [OK]" -ForegroundColor Green
        $successCount++
    }
    catch {
        Write-Host " [FAILED]" -ForegroundColor Red
        Write-Host "  Error: $($_.Exception.Message)" -ForegroundColor Red
        $failCount++
    }
}

Write-Host ""
Write-Host "Download Summary:" -ForegroundColor Cyan
Write-Host "  Success: $successCount" -ForegroundColor Green
Write-Host "  Failed: $failCount" -ForegroundColor $(if ($failCount -gt 0) { "Red" } else { "Green" })
Write-Host ""
Write-Host "Images location: $((Resolve-Path $assetsPath).Path)" -ForegroundColor Cyan

if ($successCount -gt 0) {
    Write-Host ""
    Write-Host "Next steps:" -ForegroundColor Yellow
    Write-Host "1. Verify the downloaded images in the assets folder" -ForegroundColor White
    Write-Host "2. Update CelebrityQuestionsActivity.java to use assets folder images" -ForegroundColor White
    Write-Host "3. Rebuild the app in Android Studio" -ForegroundColor White
}
