# Risk Assessment System Architecture

## Overview
The Risk Assessment System is a full-stack web application designed to manage and track organizational risks. The system provides CRUD operations for risk management with a clean separation between frontend and backend components.

## System Architecture

### Backend (Spring Boot)
- **Framework**: Spring Boot 2.7.18 with Java 17
- **Database**: MySQL 8.0 with JPA/Hibernate
- **Architecture Pattern**: Layered architecture (Controller → Service → Repository → Entity)
- **API**: RESTful web services with JSON responses
- **Testing**: JUnit 5 with MockMvc for integration testing
- **Coverage**: JaCoCo plugin configured with 80% minimum threshold

### Frontend (Angular)
- **Framework**: Angular 20.0 with TypeScript 5.8
- **Routing**: Angular Router for SPA navigation
- **HTTP Client**: Angular HttpClient for API communication
- **Build Tool**: Angular CLI with Webpack bundling

### Database Schema
The system uses a single main entity:

#### Risk Entity
- **riskId**: Primary key (Long, auto-generated)
- **riskDate**: Date when risk was identified (LocalDate, required)
- **riskType**: Enumerated type (MARKET_PRACTICE, REGULATORY, OPERATIONAL, STRATEGIC)
- **riskProbability**: Enumerated type (LOW, MEDIUM, HIGH)
- **riskDesc**: Risk description (String, required)
- **riskStatus**: Enumerated type (OPEN, IN_PROGRESS, CLOSED)
- **riskRemarks**: Additional comments (String, optional)
- **riskImpact**: Impact level (LOW, MEDIUM, HIGH)

## Data Flow

### Create/Update Risk Flow
1. User submits risk form in Angular frontend
2. Frontend validates form data and converts to RiskDTO
3. HTTP POST/PUT request sent to Spring Boot REST API
4. Controller validates request and delegates to RiskService
5. Service layer applies business logic and calls RiskRepository
6. Repository persists data to MySQL database via JPA
7. Response flows back through layers to frontend
8. Frontend updates UI with success/error feedback

### Read Operations Flow
1. Frontend requests risk data via HTTP GET
2. Controller delegates to Service layer
3. Service calls Repository to fetch from database
4. Data converted to DTOs and returned as JSON
5. Frontend receives and displays data in components

## Key Components

### Backend Components
- **RiskController**: REST endpoints for CRUD operations
- **RiskService**: Business logic and data transformation
- **RiskRepository**: JPA repository for database operations
- **RiskDTO**: Data transfer object for API communication
- **Risk**: JPA entity mapping to database table

### Frontend Components
- **DashboardComponent**: Main view displaying risk summary
- **RiskFormComponent**: Form for creating/editing risks
- **RiskService**: Angular service for API communication
- **AppRoutingModule**: Route configuration for navigation

## Configuration

### Database Configuration
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/risk_assessment
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
```

### CORS Configuration
The backend is configured to accept requests from any origin for development purposes.

## Testing Strategy

### Backend Testing
- **Unit Tests**: Service layer business logic testing
- **Integration Tests**: Controller layer with MockMvc
- **Entity Tests**: JPA entity validation and behavior
- **Coverage Target**: Minimum 80% code coverage via JaCoCo

### Frontend Testing
- **Component Tests**: Angular component behavior testing
- **Service Tests**: HTTP client and data transformation testing
- **Build Verification**: Compilation and bundling validation

## Security Considerations
- Input validation on both frontend and backend
- JPA prevents SQL injection through parameterized queries
- CORS configuration should be restricted in production
- Database credentials should be externalized in production

## Deployment Architecture
- **Backend**: Spring Boot executable JAR with embedded Tomcat
- **Frontend**: Static files served by web server (nginx/Apache)
- **Database**: MySQL server instance
- **Environment**: Separate configurations for dev/staging/production

## Future Enhancements
- Authentication and authorization system
- Risk assessment workflows and approvals
- Reporting and analytics dashboard
- Email notifications for risk status changes
- File attachment support for risk documentation
