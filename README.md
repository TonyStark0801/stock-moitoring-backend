# 🏗️ Stock Monitoring Microservices Architecture

A comprehensive microservices architecture for stock monitoring application with separate services for authentication, user profiles, and master data management.

## 📁 Project Structure

```
stock-moitoring-backend/
├── stock-commons/              # Shared library with common DTOs and utilities
├── api-gateway/               # API Gateway for routing and security
├── auth-service/              # Authentication & Authorization service
├── profile-service/           # User profile management service
├── master-data-service/       # Stock data and reference data service
├── docker-compose.yml         # Multi-service orchestration
├── build.ps1                 # PowerShell build script for all services
├── build-all.sh              # Bash build script for all services
└── README.md
```

## 🚀 Quick Start

### Prerequisites
- Java 21
- Maven 3.8+
- Docker & Docker Compose
- PostgreSQL (for local development)

### 1. Build All Services

**For Windows (PowerShell):**
```powershell
.\build.ps1
```

**For Linux/Mac:**
```bash
./build-all.sh
```

### 2. Start with Docker
```bash
docker-compose up -d
```

### 3. Access Services
- **API Gateway**: http://localhost:8080
- **Auth Service**: http://localhost:8081/v1/api
- **Profile Service**: http://localhost:8082/v1/api  
- **Master Data Service**: http://localhost:8083/v1/api

## 🔧 Service Details

### 🔐 Auth Service (Port 8081)
- User registration and login
- JWT token generation and validation
- Role-based access control
- **Endpoints**:
  - `POST /v1/api/auth/register` - User registration
  - `POST /v1/api/auth/login` - User login
  - `POST /v1/api/auth/validate` - Token validation
  - `GET /v1/api/auth/health` - Health check

### 👤 Profile Service (Port 8082)
- User profile management
- Personal information updates
- User preferences
- **Endpoints**:
  - `GET /v1/api/profile/{userId}` - Get user profile
  - `POST /v1/api/profile/{userId}` - Create profile
  - `PUT /v1/api/profile/{userId}` - Update profile
  - `DELETE /v1/api/profile/{userId}` - Delete profile
  - `GET /v1/api/profile/health` - Health check

### 📊 Master Data Service (Port 8083)
- Stock information management
- Exchange and sector data
- Stock search and filtering
- **Endpoints**:
  - `GET /v1/api/stocks` - Get all stocks
  - `GET /v1/api/stocks/{id}` - Get stock by ID
  - `GET /v1/api/stocks/symbol/{symbol}` - Get stock by symbol
  - `GET /v1/api/stocks/search?query={query}` - Search stocks
  - `POST /v1/api/stocks` - Create stock
  - `PUT /v1/api/stocks/{id}` - Update stock
  - `DELETE /v1/api/stocks/{id}` - Delete stock
  - `GET /v1/api/stocks/health` - Health check

### 🌐 API Gateway (Port 8080)
- Routes requests to appropriate services
- Centralized entry point
- CORS configuration for frontend access
- **Routes**:
  - `/v1/api/auth/**` → Auth Service
  - `/v1/api/profile/**` → Profile Service  
  - `/v1/api/stocks/**` → Master Data Service
  - `/v1/api/exchanges/**` → Master Data Service
  - `/v1/api/sectors/**` → Master Data Service

## 🗄️ Database Schema

### Auth Service DB (Port 5432)
- `users` - User accounts and authentication
- `roles` - User roles and permissions

### Profile Service DB (Port 5433)
- `user_profiles` - User personal information
- `user_preferences` - User settings and preferences

### Master Data Service DB (Port 5434)
- `stocks` - Stock information
- `exchanges` - Stock exchanges
- `sectors` - Business sectors

## 🐳 Docker Services

- **postgres-auth**: PostgreSQL for auth service
- **postgres-profile**: PostgreSQL for profile service
- **postgres-masterdata**: PostgreSQL for master data service
- **redis**: Redis for caching and rate limiting
- **api-gateway**: API Gateway service
- **auth-service**: Authentication service
- **profile-service**: Profile management service
- **master-data-service**: Master data service

## 🔧 Development

### Local Development
1. Start databases: `docker-compose up postgres-auth postgres-profile postgres-masterdata redis -d`
2. Run services individually:
   ```bash
   cd auth-service && mvn spring-boot:run
   cd profile-service && mvn spring-boot:run
   cd master-data-service && mvn spring-boot:run
   cd api-gateway && mvn spring-boot:run
   ```

### Testing

**Using cURL:**
```bash
# Test auth service directly
curl -X POST http://localhost:8081/v1/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","email":"test@example.com","password":"password123"}'

# Test through API Gateway
curl -X POST http://localhost:8080/v1/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","email":"test@example.com","password":"password123"}'
```

**Using PowerShell:**
```powershell
# Register user through API Gateway
$body = @{ 
  username = "testuser"
  email = "test@example.com" 
  password = "password123" 
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/v1/api/auth/register" -Method POST -Body $body -ContentType "application/json"

# Test health endpoints
Invoke-RestMethod -Uri "http://localhost:8080/v1/api/auth/health" -Method GET
```

## 📝 API Documentation

### Authentication Flow
1. Register user: `POST /v1/api/auth/register`
2. Login: `POST /v1/api/auth/login`
3. Use JWT token in Authorization header: `Bearer <token>`
4. Access protected endpoints

### Common Response Format
```json
{
  "success": true,
  "message": "Success",
  "data": { ... },
  "timestamp": "2024-01-01T00:00:00",
  "errorCode": null
}
```

## 🛠️ Configuration

### Environment Variables
- `SPRING_DATASOURCE_URL` - Database connection URL
- `JWT_SECRET` - JWT signing secret
- `JWT_EXPIRATION` - JWT expiration time
- `SPRING_PROFILES_ACTIVE` - Active profile (docker, dev, prod)

### Application Properties
Each service has its own `application.yml` with service-specific configurations:
- **Default profile**: Configured for localhost development
- **Docker profile**: Uses container service names for inter-service communication

### Global Exception Handling
All services include the `stock-commons` module for:
- **Centralized exception handling** with `@RestControllerAdvice`
- **Custom exception classes** (`CustomException`)
- **Consistent error response format**
- **Proper HTTP status code mapping**

## 🔧 Recent Improvements

### ✅ Fixed Global Exception Handler
- Updated all microservice applications to scan the `stock-commons` package
- Ensures `CustomException` and other common exceptions are properly handled
- Provides consistent error responses across all services

### ✅ API Gateway Configuration
- Fixed routing configuration for Docker containers
- Added `application-docker.yml` with proper service discovery
- Corrected path stripping for microservice endpoints

### ✅ Enhanced Error Handling
- Custom exceptions now return meaningful error messages
- Proper HTTP status codes (400, 401, 404, etc.)
- Consistent error response structure

## 🚀 Deployment

### Production Deployment
1. Update database URLs in `application.yml`
2. Set production JWT secrets
3. Configure proper CORS settings
4. Use Docker Compose or Kubernetes for orchestration

### Monitoring
- Health checks: `/actuator/health`
- Metrics: `/actuator/metrics`
- Gateway routes: `/actuator/gateway/routes`

## 🐛 Troubleshooting

### Common Issues

**1. Port Already in Use**
```bash
# Check what's using the port
netstat -ano | findstr :8080
# Kill the process
taskkill /PID <PID> /F
```

**2. Docker Container Issues**
```bash
# Check container logs
docker logs <container-name>

# Restart specific service
docker-compose restart <service-name>

# Rebuild and restart
docker-compose up <service-name> --build -d
```

**3. Database Connection Issues**
- Ensure PostgreSQL containers are running
- Check database URLs in `application.yml`
- Verify network connectivity between containers

**4. Exception Not Handled**
- Verify `@SpringBootApplication` includes `scanBasePackages`
- Check that `stock-commons` dependency is included
- Ensure `GlobalExceptionHandler` is in the classpath

### Health Check Endpoints
```bash
# API Gateway
curl http://localhost:8080/actuator/health

# Individual Services
curl http://localhost:8081/v1/api/auth/health
curl http://localhost:8082/v1/api/profile/health  
curl http://localhost:8083/v1/api/stocks/health
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License.
