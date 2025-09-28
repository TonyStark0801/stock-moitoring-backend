# PowerShell
Write-Host "🏗️  Building Stock Monitoring Microservices..."

# Build shared commons first
Write-Host "📦 Building stock-commons..."
Push-Location "stock-commons"
mvn clean install -DskipTests
Pop-Location

# Build all services
Write-Host "🔐 Building auth-service..."
Push-Location "auth-service"
mvn clean package -DskipTests
Pop-Location

Write-Host "👤 Building profile-service..."
Push-Location "profile-service"
mvn clean package -DskipTests
Pop-Location

Write-Host "📊 Building master-data-service..."
Push-Location "master-data-service"
mvn clean package -DskipTests 
Pop-Location

Write-Host "🌐 Building api-gateway..."
Push-Location "api-gateway"
mvn clean package -DskipTests
Pop-Location

Write-Host "✅ All services built successfully!"
Write-Host "Run 'docker-compose up' to start all services"