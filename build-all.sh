#!/bin/bash

# Exit immediately if a command exits with a non-zero status
set -e

# Store the root directory
ROOT_DIR=$(pwd)

echo "🏗️  Building Stock Monitoring Microservices..."
echo "📍 Working directory: $ROOT_DIR"
echo ""

# Build shared commons first
echo "📦 Building stock-commons..."
cd "$ROOT_DIR/stock-commons" || exit 1
mvn clean install -DskipTests
echo "✅ stock-commons built successfully"
echo ""

# Build all services
echo "🔐 Building auth-service..."
cd "$ROOT_DIR/auth-service" || exit 1
mvn clean package -DskipTests
echo "✅ auth-service built successfully"
echo ""

echo "👤 Building profile-service..."
cd "$ROOT_DIR/profile-service" || exit 1
mvn clean package -DskipTests
echo "✅ profile-service built successfully"
echo ""

echo "📊 Building master-data-service..."
cd "$ROOT_DIR/master-data-service" || exit 1
mvn clean package -DskipTests
echo "✅ master-data-service built successfully"
echo ""

echo "🌐 Building api-gateway..."
cd "$ROOT_DIR/api-gateway" || exit 1
mvn clean package -DskipTests
echo "✅ api-gateway built successfully"
echo ""

# Return to root directory
cd "$ROOT_DIR"

echo ""
echo "✅ All services built successfully!"
echo "🚀 Run 'docker-compose up' to start all services"
