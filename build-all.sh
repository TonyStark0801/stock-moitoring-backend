#!/bin/bash

echo "🏗️  Building Stock Monitoring Microservices..."

# Build shared commons first
echo "📦 Building stock-commons..."
cd stock-commons
mvn clean install -DskipTests
cd ..

# Build all services
echo "🔐 Building auth-service..."
cd auth-service
mvn clean package -DskipTests
cd ..

echo "👤 Building profile-service..."
cd profile-service
mvn clean package -DskipTests
cd ..

echo "📊 Building master-data-service..."
cd master-data-service
mvn clean package -DskipTests
cd ..

echo "🌐 Building api-gateway..."
cd api-gateway
mvn clean package -DskipTests
cd ..

echo "✅ All services built successfully!"
echo "🚀 Run 'docker-compose up' to start all services"
