# Download MySQL Connector/J for Windows
# Run this script in PowerShell to automatically download and extract the driver

$libDir = ".\lib"
$jarFile = "$libDir\mysql-connector-java-8.0.33.jar"

# Check if lib directory exists
if (-not (Test-Path $libDir)) {
    New-Item -ItemType Directory -Path $libDir -Force | Out-Null
    Write-Host "Created lib directory"
}

# Check if JAR already exists
if (Test-Path $jarFile) {
    Write-Host "MySQL Connector/J already exists at: $jarFile"
    exit 0
}

# Download MySQL Connector/J
Write-Host "Downloading MySQL Connector/J 8.0.33..."
$url = "https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-8.0.33.zip"
$zipFile = "$libDir\mysql-connector-java-8.0.33.zip"

try {
    Invoke-WebRequest -Uri $url -OutFile $zipFile -ErrorAction Stop
    Write-Host "Downloaded successfully to: $zipFile"
    
    # Extract JAR from ZIP
    Write-Host "Extracting JAR file..."
    Expand-Archive -Path $zipFile -DestinationPath $libDir -Force
    
    # Find and move the JAR to lib root
    $extractedJar = Get-ChildItem -Path $libDir -Filter "mysql-connector-java-8.0.33.jar" -Recurse -ErrorAction Stop | Select-Object -First 1
    if ($extractedJar) {
        Move-Item -Path $extractedJar.FullName -Destination $jarFile -Force
        Write-Host "Extracted JAR to: $jarFile"
    } else {
        Write-Host "ERROR: Could not find JAR file in extracted archive"
        exit 1
    }
    
    # Cleanup
    Remove-Item -Path $zipFile -Force -ErrorAction SilentlyContinue
    Get-ChildItem -Path $libDir -Directory | ForEach-Object {
        Remove-Item -Path $_.FullName -Recurse -Force -ErrorAction SilentlyContinue
    }
    
    Write-Host "Setup complete! MySQL Connector/J is ready in: $jarFile"
    
} catch {
    Write-Host "ERROR: Failed to download or extract MySQL Connector/J"
    Write-Host $_.Exception.Message
    exit 1
}
