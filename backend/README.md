# Backend - ATS Interview Tracker

Spring Boot 3.2 backend service for ATS Interview Tracker.

## Tech Stack

- **Spring Boot** 3.2.1  
- **Hibernate** 6.4.x (via Spring Data JPA)
- **PostgreSQL** 16
- **Flyway** (database migrations)
- **SpringDoc OpenAPI** 2.3.0 (Swagger UI)
- **Lombok** (code generation)

## Prerequisites

- Java 17+
- Docker & Docker Compose (for PostgreSQL)

## Setup

### 1. Start PostgreSQL Database

From the project root:
```bash
docker-compose up -d
```

### 2. Run the Application

If Maven is installed:
```bash
cd backend
mvn spring-boot:run
```

Or use Maven Wrapper (if you have `mvnw` or `mvnw.cmd`):
```bash
cd backend
./mvnw spring-boot:run   # Linux/Mac
.\mvnw.cmd spring-boot:run  # Windows
```

The application will start on **http://localhost:8080**

### 3. Access Swagger UI

Once running, navigate to:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs (JSON)**: http://localhost:8080/api-docs

## Database Migrations

Flyway migrations are located in `src/main/resources/db/migration/`:
- `V1__init_schema.sql` - Initial schema (Company, Application, Interview tables)
- `V2__seed_sample_data.sql` - Sample test data

Migrations run automatically on application startup.

## API Endpoints

### Applications
- `GET /api/applications` - List applications (paginated, filterable)
- `POST /api/applications` - Create application
- `GET /api/applications/{id}` - Get application by ID
- `PUT /api/applications/{id}` - Update application
- `PATCH /api/applications/{id}/status` - Update status (Kanban)
- `DELETE /api/applications/{id}` - Delete application

### Interviews
- `GET /api/interviews/{id}` - Get interview by ID
- `GET /api/interviews/application/{applicationId}` - List interviews for application
- `GET /api/interviews/upcoming?daysAhead=7` - Upcoming interviews
- `POST /api/interviews/application/{applicationId}` - Create interview
- `PUT /api/interviews/{id}` - Update interview
- `DELETE /api/interviews/{id}` - Delete interview

### Companies
- `GET /api/companies` - List all companies
- `POST /api/companies` - Create company
- `GET /api/companies/{id}` - Get company by ID
- `PUT /api/companies/{id}` - Update company
- `DELETE /api/companies/{id}` - Delete company

### Metrics
- `GET /api/metrics/dashboard` - Dashboard analytics

## API Response Format

All endpoints return standardized `ApiResponse<T>`:

```json
{
  "success": true,
  "data": { ... },
  "message": "Operation successful",
  "timestamp": "2024-12-08T23:30:00"
}
```

Error responses return `ErrorResponse`:

```json
{
  "success": false,
  "message": "Resource not found",
  "errors": {},
  "timestamp": "2024-12-08T23:30:00"
}
```

## Project Structure

```
src/main/java/com/atstrack/
├── entity/           # JPA entities (Company, Application, Interview)
├── repository/       # Spring Data JPA repositories
├── dto/              # Data Transfer Objects
├── service/          # Business logic layer
├── controller/       # REST controllers
├── exception/        # Custom exceptions
├── response/         # API response wrappers
└── config/           # Configuration (CORS, OpenAPI)
```

## Exception Handling

Custom exceptions with appropriate HTTP status codes:
- `ResourceNotFoundException` → 404
- `ValidationException` → 400
- `DuplicateResourceException` → 409
- `BadRequestException` → 400

## Testing

Run tests:
```bash
mvn test
```

## Configuration

Application properties are in `src/main/resources/application.yml`.

Key configurations:
- Database URL: `jdbc:postgresql://localhost:5432/ats_tracker`
- Server port: `8080`
- Flyway enabled: `true`
- SQL logging: `true` (development)
