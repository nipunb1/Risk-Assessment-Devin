# Risk Assessment Application - Code Documentation

## Table of Contents
1. [Project Overview](#project-overview)
2. [Backend Components (Spring Boot)](#backend-components-spring-boot)
3. [Frontend Components (Angular)](#frontend-components-angular)
4. [Architecture Summary](#architecture-summary)
5. [Data Flow](#data-flow)

---

## Project Overview

The Risk Assessment Application is a full-stack web application built with a modern three-tier architecture:
- **Frontend**: Angular 20.0.3 with TypeScript
- **Backend**: Spring Boot 2.7.18 with Java 11
- **Database**: MySQL 8.0.42 with persistent storage

The application provides comprehensive risk management capabilities including risk creation, assessment, tracking, and reporting through a professional web interface.

---

## Backend Components (Spring Boot)

### 1. RiskAssessmentApplication.java
**Location**: `risk-assessment-backend/src/main/java/com/riskassessment/RiskAssessmentApplication.java`

**Purpose**: Main Spring Boot application entry point with auto-configuration.

**Class Details**:
- **Annotations**: `@SpringBootApplication` - Enables auto-configuration, component scanning, and configuration
- **Main Method**: `main(String[] args)` - Application bootstrap using `SpringApplication.run()`

**Key Features**:
- Automatic component scanning for the `com.riskassessment` package
- Embedded Tomcat server configuration
- Spring Boot auto-configuration for JPA, web, and validation

---

### 2. RiskController.java
**Location**: `risk-assessment-backend/src/main/java/com/riskassessment/controller/RiskController.java`

**Purpose**: REST API controller providing RESTful web services for risk management operations.

**Class Details**:
- **Annotations**: `@RestController`, `@RequestMapping("/api/risks")`, `@CrossOrigin(origins = "*")`
- **Dependencies**: `RiskService` (autowired)

**Key Methods**:
- `getAllRisks()` - **GET** `/api/risks` - Retrieves all risks ordered by date
- `getRiskById(Long id)` - **GET** `/api/risks/{id}` - Retrieves specific risk by ID
- `createRisk(RiskDTO riskDTO)` - **POST** `/api/risks` - Creates new risk with validation
- `updateRisk(Long id, RiskDTO riskDTO)` - **PUT** `/api/risks/{id}` - Updates existing risk
- `deleteRisk(Long id)` - **DELETE** `/api/risks/{id}` - Deletes risk by ID
- `getRisksByStatus(RiskStatus status)` - **GET** `/api/risks/status/{status}` - Filters by status
- `getRisksByType(RiskType type)` - **GET** `/api/risks/type/{type}` - Filters by type
- `getRisksByProbability(RiskProbability probability)` - **GET** `/api/risks/probability/{probability}` - Filters by probability
- `getRisksByImpact(RiskImpact impact)` - **GET** `/api/risks/impact/{impact}` - Filters by impact
- `getEnumValues()` - **GET** `/api/risks/enums` - Returns all enum constants for frontend dropdowns

**Inner Classes**:
- `EnumValues` - Static class containing arrays of all enum values for frontend consumption

**Error Handling**:
- Returns appropriate HTTP status codes (200, 201, 404, 400)
- Uses `Optional` pattern for null-safe operations
- Validation through `@Valid` annotation

---

### 3. RiskService.java
**Location**: `risk-assessment-backend/src/main/java/com/riskassessment/service/RiskService.java`

**Purpose**: Business logic layer providing core risk management operations and DTO transformations.

**Class Details**:
- **Annotations**: `@Service`
- **Dependencies**: `RiskRepository` (autowired)

**Key Methods**:
- `getAllRisks()` - Returns `List<RiskDTO>` ordered by risk date descending
- `getRiskById(Long id)` - Returns `Optional<RiskDTO>` for safe null handling
- `createRisk(RiskDTO riskDTO)` - Converts DTO to entity, saves, and returns DTO
- `updateRisk(Long id, RiskDTO riskDTO)` - Updates existing entity fields and returns DTO
- `deleteRisk(Long id)` - Returns boolean indicating successful deletion
- `getRisksByStatus(RiskStatus status)` - Filters and returns risks by status
- `getRisksByType(RiskType type)` - Filters and returns risks by type
- `getRisksByProbability(RiskProbability probability)` - Filters and returns risks by probability
- `getRisksByImpact(RiskImpact impact)` - Filters and returns risks by impact

**Data Transformation**:
- Converts between `Risk` entities and `RiskDTO` objects
- Uses Java 8 Streams for efficient data processing
- Maintains separation between domain model and API contracts

---

### 4. Risk.java (Entity)
**Location**: `risk-assessment-backend/src/main/java/com/riskassessment/entity/Risk.java`

**Purpose**: JPA entity representing risk assessment records with complete risk management workflow support.

**Class Details**:
- **Annotations**: `@Entity`, `@Table(name = "risk")`
- **Primary Key**: `riskId` (Long, auto-generated)

**Entity Fields**:
- `riskId` - Primary key, auto-generated (`@GeneratedValue`)
- `riskDate` - Risk identification date (`@NotNull`, `LocalDate`)
- `riskType` - Risk category (`@NotNull`, `@Enumerated(EnumType.STRING)`)
- `riskProbability` - Likelihood assessment (`@NotNull`, `@Enumerated(EnumType.STRING)`)
- `riskDesc` - Risk description (`@NotBlank`, `TEXT` column)
- `riskStatus` - Current status (`@NotNull`, `@Enumerated(EnumType.STRING)`)
- `riskRemarks` - Additional comments (`TEXT` column, optional)
- `riskImpact` - Impact assessment (`@NotNull`, `@Enumerated(EnumType.STRING)`)

**Nested Enums**:
1. **RiskType**: `MARKET_PRACTICE`, `CONFLICT_OF_INTEREST`, `PRICING`, `REGULATORY`, `GOVERNANCE`
2. **RiskProbability**: `LOW`, `MEDIUM`, `HIGH`
3. **RiskStatus**: `OPEN`, `IN_PROGRESS`, `CLOSED`
4. **RiskImpact**: `LOW`, `MEDIUM`, `HIGH`

**Enum Features**:
- Each enum has `displayName` field for user-friendly labels
- `getDisplayName()` method for UI presentation
- String-based database storage for readability

**Constructors**:
- Default constructor for JPA
- Full constructor for programmatic creation

---

### 5. RiskDTO.java
**Location**: `risk-assessment-backend/src/main/java/com/riskassessment/dto/RiskDTO.java`

**Purpose**: Data Transfer Object for API communication with validation constraints.

**Class Details**:
- Plain Java class with validation annotations
- Mirrors `Risk` entity structure for API contracts

**Validation Annotations**:
- `@NotNull` - Required fields: `riskDate`, `riskType`, `riskProbability`, `riskStatus`, `riskImpact`
- `@NotBlank` - Non-empty string: `riskDesc`
- Optional fields: `riskId` (for updates), `riskRemarks`

**Key Methods**:
- `RiskDTO(Risk risk)` - Constructor for entity-to-DTO conversion
- `toEntity()` - Converts DTO to `Risk` entity for persistence
- Standard getters/setters for all fields

**Data Transformation**:
- Bidirectional conversion between DTO and Entity
- Maintains validation rules separate from persistence layer
- Supports both create and update operations

---

### 6. RiskRepository.java
**Location**: `risk-assessment-backend/src/main/java/com/riskassessment/repository/RiskRepository.java`

**Purpose**: JPA repository interface providing data access operations with custom queries.

**Interface Details**:
- **Extends**: `JpaRepository<Risk, Long>`
- **Annotations**: `@Repository`

**Inherited Methods** (from JpaRepository):
- `save(Risk entity)` - Create/update operations
- `findById(Long id)` - Find by primary key
- `findAll()` - Retrieve all records
- `deleteById(Long id)` - Delete by primary key
- `existsById(Long id)` - Check existence

**Custom Query Methods**:
- `findByRiskStatus(RiskStatus riskStatus)` - Filter by status enum
- `findByRiskType(RiskType riskType)` - Filter by type enum
- `findByRiskProbability(RiskProbability riskProbability)` - Filter by probability enum
- `findByRiskImpact(RiskImpact riskImpact)` - Filter by impact enum

**Custom JPQL Queries**:
- `findAllOrderByRiskDateDesc()` - `@Query("SELECT r FROM Risk r ORDER BY r.riskDate DESC")`

**Features**:
- Automatic query generation from method names
- Type-safe enum-based filtering
- Custom sorting for dashboard display

---

## Frontend Components (Angular)

### 1. App.ts (Root Component)
**Location**: `risk-assessment-frontend/src/app/app.ts`

**Purpose**: Main Angular application component serving as the root container.

**Component Details**:
- **Selector**: `app-root`
- **Template**: `./app.html`
- **Styles**: `./app.css`
- **Imports**: `RouterOutlet` for navigation

**Properties**:
- `title` - Application title: `'risk-assessment-frontend'`

**Features**:
- Provides router outlet for component navigation
- Serves as application shell
- Minimal root component following Angular best practices

---

### 2. DashboardComponent.ts
**Location**: `risk-assessment-frontend/src/app/dashboard/dashboard.ts`

**Purpose**: Main dashboard component displaying risk list with filtering and management capabilities.

**Component Details**:
- **Selector**: `app-dashboard`
- **Template**: `./dashboard.html`
- **Styles**: `./dashboard.css`
- **Imports**: `CommonModule`, `RouterModule`, `FormsModule`
- **Implements**: `OnInit`

**Properties**:
- `risks: Risk[]` - Complete risk dataset
- `filteredRisks: Risk[]` - Filtered risks for display
- `selectedStatus: string` - Current filter status ('ALL' or enum value)
- `loading: boolean` - Loading state indicator
- `error: string | null` - Error message display

**Lifecycle Methods**:
- `ngOnInit()` - Initializes component and loads risks

**Core Methods**:
- `loadRisks()` - Fetches all risks from API with error handling
- `filterRisks()` - Applies status-based filtering to risk list
- `onStatusFilterChange(status: string)` - Updates filter and refreshes display
- `deleteRisk(id: number)` - Deletes risk with confirmation dialog

**Display Helper Methods**:
- `getRiskTypeDisplay(type: string)` - Converts enum to user-friendly labels
- `getRiskProbabilityDisplay(probability: string)` - Converts probability enum to display text
- `getRiskStatusDisplay(status: string)` - Converts status enum to display text
- `getProbabilityClass(probability: string)` - Returns CSS class for probability styling
- `getStatusClass(status: string)` - Returns CSS class for status styling

**Error Handling**:
- Comprehensive error handling for API calls
- User-friendly error messages
- Loading states for better UX

---

### 3. RiskFormComponent.ts
**Location**: `risk-assessment-frontend/src/app/risk-form/risk-form.ts`

**Purpose**: Form component handling both risk creation and editing with validation.

**Component Details**:
- **Selector**: `app-risk-form`
- **Template**: `./risk-form.html`
- **Styles**: `./risk-form.css`
- **Imports**: `CommonModule`, `FormsModule`
- **Implements**: `OnInit`

**Properties**:
- `risk: Risk` - Form data object with default values
- `isEditMode: boolean` - Determines create vs. edit behavior
- `loading: boolean` - Form submission state
- `error: string | null` - Validation/submission errors
- `enumValues: RiskEnums | null` - Dropdown options from backend

**Constructor Dependencies**:
- `RiskService` - API communication
- `Router` - Navigation after form submission
- `ActivatedRoute` - Route parameter extraction for edit mode

**Lifecycle Methods**:
- `ngOnInit()` - Loads enum values and determines edit mode from route

**Core Methods**:
- `loadEnumValues()` - Fetches dropdown options from backend
- `loadRisk(id: number)` - Loads existing risk data for editing
- `onSubmit()` - Handles form submission with validation
- `validateForm()` - Client-side form validation
- `onCancel()` - Navigates back to dashboard

**Display Helper Methods**:
- `getRiskTypeDisplay(type: string)` - Type enum to display conversion
- `getRiskProbabilityDisplay(probability: string)` - Probability enum to display conversion
- `getRiskStatusDisplay(status: string)` - Status enum to display conversion
- `getRiskImpactDisplay(impact: string)` - Impact enum to display conversion

**Form Features**:
- Dual-mode operation (create/edit)
- Client-side validation with error display
- Dynamic enum loading from backend
- Automatic navigation after successful submission

---

### 4. RiskService.ts
**Location**: `risk-assessment-frontend/src/app/services/risk.service.ts`

**Purpose**: Angular service providing HTTP client wrapper for backend API communication.

**Service Details**:
- **Injectable**: `providedIn: 'root'` (singleton service)
- **Dependencies**: `HttpClient` for API calls
- **Base URL**: `/api/risks`

**API Methods**:
- `getAllRisks()` - **GET** `/api/risks` → `Observable<Risk[]>`
- `getRiskById(id: number)` - **GET** `/api/risks/{id}` → `Observable<Risk>`
- `createRisk(risk: Risk)` - **POST** `/api/risks` → `Observable<Risk>`
- `updateRisk(id: number, risk: Risk)` - **PUT** `/api/risks/{id}` → `Observable<Risk>`
- `deleteRisk(id: number)` - **DELETE** `/api/risks/{id}` → `Observable<void>`
- `getRisksByStatus(status: string)` - **GET** `/api/risks/status/{status}` → `Observable<Risk[]>`
- `getEnumValues()` - **GET** `/api/risks/enums` → `Observable<RiskEnums>`

**Features**:
- Type-safe HTTP operations with TypeScript interfaces
- Observable-based async operations
- Centralized API endpoint management
- Error handling delegation to components

---

### 5. Risk.model.ts
**Location**: `risk-assessment-frontend/src/app/models/risk.model.ts`

**Purpose**: TypeScript interfaces and enums defining frontend data models synchronized with backend.

**Main Interface**:
```typescript
interface Risk {
  riskId?: number;           // Optional for create operations
  riskDate: string;          // ISO date string
  riskType: RiskType;        // Enum reference
  riskProbability: RiskProbability;
  riskDesc: string;          // Required description
  riskStatus: RiskStatus;    // Current status
  riskRemarks: string;       // Optional comments
  riskImpact: RiskImpact;    // Impact assessment
}
```

**Enums** (synchronized with backend Java enums):
1. **RiskType**: `MARKET_PRACTICE`, `CONFLICT_OF_INTEREST`, `PRICING`, `REGULATORY`, `GOVERNANCE`
2. **RiskProbability**: `LOW`, `MEDIUM`, `HIGH`
3. **RiskStatus**: `OPEN`, `IN_PROGRESS`, `CLOSED`
4. **RiskImpact**: `LOW`, `MEDIUM`, `HIGH`

**Helper Interface**:
```typescript
interface RiskEnums {
  riskTypes: { value: string; displayName: string }[];
  riskProbabilities: { value: string; displayName: string }[];
  riskStatuses: { value: string; displayName: string }[];
  riskImpacts: { value: string; displayName: string }[];
}
```

**Features**:
- Perfect synchronization with backend Java enums
- Type safety for all risk operations
- Support for both enum values and display names
- Optional ID field for create vs. update operations

---

### 6. app.routes.ts
**Location**: `risk-assessment-frontend/src/app/app.routes.ts`

**Purpose**: Angular routing configuration defining application navigation structure.

**Route Configuration**:
```typescript
const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'create-risk', component: RiskFormComponent },
  { path: 'edit-risk/:id', component: RiskFormComponent }
];
```

**Route Details**:
- **Root Route** (`''`) - Redirects to dashboard as default view
- **Dashboard Route** (`/dashboard`) - Main risk listing and management
- **Create Route** (`/create-risk`) - New risk creation form
- **Edit Route** (`/edit-risk/:id`) - Risk editing form with ID parameter

**Navigation Patterns**:
- Default landing on dashboard
- Parameterized routes for edit operations
- Single form component handling both create and edit modes
- Clean URL structure following REST conventions

---

## Architecture Summary

### Three-Tier Architecture
1. **Presentation Layer**: Angular frontend with TypeScript
2. **Business Logic Layer**: Spring Boot backend with Java
3. **Data Access Layer**: MySQL database with JPA/Hibernate

### Design Patterns
- **MVC Pattern**: Clear separation of Model (entities), View (Angular components), Controller (REST endpoints)
- **Repository Pattern**: Data access abstraction through JPA repositories
- **DTO Pattern**: Data transfer objects for API communication
- **Service Layer Pattern**: Business logic encapsulation in service classes
- **Dependency Injection**: Spring and Angular DI containers

### Key Architectural Decisions
- **RESTful API Design**: Standard HTTP methods and status codes
- **Enum Synchronization**: Matching enums between frontend TypeScript and backend Java
- **Validation Strategy**: Both client-side (Angular) and server-side (Bean Validation) validation
- **Error Handling**: Consistent error responses and user-friendly messages
- **State Management**: Component-based state with service communication

---

## Data Flow

### Risk Creation Flow
1. **Frontend**: User fills form in `RiskFormComponent`
2. **Validation**: Client-side validation in `validateForm()`
3. **API Call**: `RiskService.createRisk()` sends POST request
4. **Backend**: `RiskController.createRisk()` receives request
5. **Validation**: Server-side validation with `@Valid` annotation
6. **Business Logic**: `RiskService.createRisk()` processes request
7. **Data Access**: `RiskRepository.save()` persists to database
8. **Response**: DTO returned through all layers back to frontend
9. **Navigation**: Automatic redirect to dashboard on success

### Risk Retrieval Flow
1. **Frontend**: `DashboardComponent.loadRisks()` initiates request
2. **API Call**: `RiskService.getAllRisks()` sends GET request
3. **Backend**: `RiskController.getAllRisks()` handles request
4. **Business Logic**: `RiskService.getAllRisks()` processes request
5. **Data Access**: `RiskRepository.findAllOrderByRiskDateDesc()` queries database
6. **Transformation**: Entities converted to DTOs
7. **Response**: JSON array returned to frontend
8. **Display**: Component updates view with risk data

### Filtering Flow
1. **Frontend**: User selects filter in dashboard
2. **Local Filtering**: `DashboardComponent.filterRisks()` processes client-side
3. **Alternative**: Server-side filtering via status-specific endpoints
4. **Display**: Filtered results shown in table

### Enum Synchronization
1. **Backend**: Java enums defined in `Risk` entity
2. **API Endpoint**: `RiskController.getEnumValues()` exposes enum data
3. **Frontend**: `RiskService.getEnumValues()` fetches enum data
4. **Form Population**: Dropdowns populated with enum values and display names
5. **Type Safety**: TypeScript enums ensure compile-time type checking

This architecture ensures maintainability, scalability, and clear separation of concerns while providing a robust risk management solution.
