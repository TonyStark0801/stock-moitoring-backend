# 🏗️ Stock Monitoring Microservices Architecture

A comprehensive microservices architecture for stock monitoring application with separate services for authentication, user profiles, and master data management.

## 📁 Project Structure

```
stock-monitoring/
├── stock-commons/              # Shared library with common DTOs and utilities
├── api-gateway/               # API Gateway for routing and security
├── auth-service/              # Authentication & Authorization service
├── profile-service/           # User profile management service
├── master-data-service/       # Stock data and reference data service
├── docker-compose.yml         # Multi-service orchestration
├── build-all.sh              # Build script for all services
└── README.md
```

## 🚀 Quick Start

### Prerequisites
- Java 24
- Maven 3.8+
- Docker & Docker Compose
- PostgreSQL (for local development)

### 1. Build All Services
```bash
./build-all.sh
```

### 2. Start with Docker
```bash
docker-compose up -d
```

### 3. Access Services
- **API Gateway**: http://localhost:8080
- **Auth Service**: http://localhost:8081
- **Profile Service**: http://localhost:8082
- **Master Data Service**: http://localhost:8083

## 🔧 Service Details

### 🔐 Auth Service (Port 8081)
- User registration and login
- JWT token generation and validation
- Role-based access control
- **Endpoints**:
  - `POST /api/auth/register` - User registration
  - `POST /api/auth/login` - User login
  - `POST /api/auth/validate` - Token validation

### 👤 Profile Service (Port 8082)
- User profile management
- Personal information updates
- User preferences
- **Endpoints**:
  - `GET /api/profile/{userId}` - Get user profile
  - `POST /api/profile/{userId}` - Create profile
  - `PUT /api/profile/{userId}` - Update profile
  - `DELETE /api/profile/{userId}` - Delete profile

### 📊 Master Data Service (Port 8083)
- Stock information management
- Exchange and sector data
- Stock search and filtering
- **Endpoints**:
  - `GET /api/stocks` - Get all stocks
  - `GET /api/stocks/{id}` - Get stock by ID
  - `GET /api/stocks/symbol/{symbol}` - Get stock by symbol
  - `GET /api/stocks/search?query={query}` - Search stocks
  - `POST /api/stocks` - Create stock
  - `PUT /api/stocks/{id}` - Update stock
  - `DELETE /api/stocks/{id}` - Delete stock

### 🌐 API Gateway (Port 8080)
- Routes requests to appropriate services
- Centralized entry point
- Request/Response logging
- **Routes**:
  - `/api/auth/**` → Auth Service
  - `/api/profile/**` → Profile Service
  - `/api/stocks/**` → Master Data Service

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
```bash
# Test auth service
curl -X POST http://localhost:8081/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","email":"test@example.com","password":"password123"}'

# Test through API Gateway
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","email":"test@example.com","password":"password123"}'
```

## 📝 API Documentation

### Authentication Flow
1. Register user: `POST /api/auth/register`
2. Login: `POST /api/auth/login`
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

### Application Properties
Each service has its own `application.yml` with service-specific configurations.

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

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License.
