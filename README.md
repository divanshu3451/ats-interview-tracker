# ATS Interview Tracker

Enterprise-grade job application tracking system built with Angular 17+ and Spring Boot 3.

## Project Structure

```
/backend  - Spring Boot 3.x + Hibernate + PostgreSQL
/frontend - Angular 17+ (Standalone Components) + Material UI
```

## Quick Start

### Prerequisites
- Java 17+
- Node.js 18+
- Docker & Docker Compose
- Maven 3.9+
- Angular CLI 17+

### Database Setup
```bash
docker-compose up -d
```

### Backend
```bash
cd backend
mvn spring-boot:run
```
API: http://localhost:8080
Swagger: http://localhost:8080/swagger-ui.html

### Frontend
```bash
cd frontend
npm install
ng serve
```
App: http://localhost:4200

## Version History

- **v1.0** - Core features (CRUD, Kanban board, metrics dashboard)
- **v2.0** - JWT authentication and multi-tenancy (planned)

## Tech Stack

**Backend:**
- Spring Boot 3.2.x
- Hibernate 6.4.x
- PostgreSQL 16
- Flyway (migrations)
- SpringDoc OpenAPI

**Frontend:**
- Angular 17+ (Standalone components)
- Angular Material
- RxJS
- Signals API
- Chart.js

## License
MIT
