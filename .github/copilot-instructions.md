# CompleFit Backend - AI Coding Agent Instructions

## Architecture Overview

This is a **Spring Boot 3.5.6** fitness management application using **Java 25**, PostgreSQL, and JWT authentication. The system manages Users, Trainers, Students, and Workouts with a clear multi-role architecture.

### Domain Model Structure
- **User** (`tb_users`): Base entity implementing `UserDetails` with roles (STUDENT, TRAINER, ADMIN)
- **Student/Trainer**: One-to-one relationships with User via `tb_user_id` foreign key
- **Workout** → **WorkoutExercise**: One-to-many relationship; workouts belong to students, optionally created by trainers
- All IDs use UUID with `GenerationType.UUID` or `GenerationType.AUTO`

## Module Organization Pattern

Every domain module follows this strict structure (see `student/`, `trainer/`, `auth/`, `exercise/`, etc.):
```
domain/           # JPA entities with Lombok (@Entity, @Getter, @Setter, @NoArgsConstructor, @AllArgsConstructor)
dto/              # Java records for Request/Response/Update DTOs with Jakarta validation
mapper/           # Static utility classes for entity ↔DTO conversion
repository/       # JpaRepository interfaces
service/          # Business logic with constructor injection
controller/       # REST controllers at /api/{module}, constructor injection
exception/        # Module-specific exceptions extending GlobalException
config/           # Module-specific configuration (optional, e.g., ExerciseDbConfig)
```

**Example:** `StudentService` depends on `StudentRepository` AND `UserRepository` because students require user lookup.

**Note:** The `exercise` module does NOT have domain/repository as it's a proxy to ExerciseDB API (no local storage).

## Critical Conventions

### DTOs
- Use **Java records** for all DTOs (not classes)
- **Request DTOs**: Include `@NotNull`, `@NotBlank`, `@Size` Jakarta validation
- **Response DTOs**: Return computed fields (e.g., `fullName` = `firstName + " " + lastName`)
- **Update DTOs**: All fields optional; mappers check null before updating

### Mappers
```java
// Static methods only - no instance creation
public static Entity toEntity(RequestDTO dto, dependencies...)
public static void updateEntity(Entity entity, UpdateDTO dto) // Null-safe updates
public static ResponseDTO toResponse(Entity entity)
```

### Exception Handling
- All business exceptions extend `GlobalException(message, HttpStatus)`
- Static factory pattern: `StudentException.notFound(UUID id)`
- Caught by `GlobalExceptionHandler` returning `ErrorResponseDTO`
- Never throw raw `RuntimeException` - create typed exceptions

### Controllers
```java
@RestController
@RequestMapping("/api/{module}")  // All endpoints under /api
public class XController {
    private final XService service;  // Constructor injection
    
    @PostMapping  // Returns 200 OK with DTO
    @GetMapping("/{id}")  // PathVariable for IDs
    @PutMapping("/{id}")  // Full updates
    @DeleteMapping("/{id}")  // Returns 204 No Content
}
```

## Security Configuration

Located in `infra/config/security/`:
- **JwtAuthenticationFilter**: Validates JWT from `Authorization: Bearer <token>`, skips `/api/auth/**`
- **RateLimitFilter**: Uses Bucket4j for rate limiting (runs before JWT filter)
- **SecurityConfiguration**: 
  - Public: `/api/auth/**`, `/api/users/register`, Swagger UI, health endpoint
  - Authenticated: `/api/exercises/**` (search exercises from ExerciseDB)
  - All other `/api/**` requires authentication
- **TokenService**: Uses Auth0 JWT library with HMAC256, stores role in claims

## Database & Migrations

- **Flyway** manages schema with versioned SQL in `src/main/resources/db/migration/`
- Naming: `V1__initial_schema.sql`, `V2__workout_relationship.sql`
- PostgreSQL-specific: Uses `VARCHAR`, `NUMERIC`, `TIMESTAMP`, UUID primary keys
- Indexes on foreign keys: `CREATE INDEX idx_workout_student_id ON tb_workouts(student_id)`
- Cascade rules: Students/Trainers `ON DELETE CASCADE` from users; workouts `ON DELETE SET NULL` for trainers

## Development Workflow

### Local Setup
1. Run `docker-compose up -d` (starts PostgreSQL on `${POSTGRES_PORT}` + pgAdmin)
2. Set environment variables in `.env` or IDE: 
   - Database: `POSTGRES_USER`, `POSTGRES_PASSWORD`, `POSTGRES_DB`, `POSTGRES_PORT`
   - Auth: `JWT_SECRET`
   - ExerciseDB: `EXERCISEDB_API_KEY` (get from https://rapidapi.com/justin-WFnsXH_t6/api/exercisedb)
3. Spring profile: `spring.profiles.active=local` (loads `application-local.yml`)
4. Application runs on port **8090** (`server.port: 8090`)

### Build & Run
```bash
./mvnw clean install        # Build
./mvnw spring-boot:run      # Run with local profile
./mvnw test                 # Run tests
```

### Testing
- Tests use `@WebMvcTest(controllers = XController.class)` with `@AutoConfigureMockMvc(addFilters = false)`
- Always import `GlobalExceptionHandler` via `@Import(GlobalExceptionHandler.class)`
- Mock services with `@MockitoBean`
- Use `MockMvc` for HTTP assertions, `ObjectMapper` for JSON serialization

## API Documentation

- **SpringDoc OpenAPI** at `/swagger-ui.html` and `/v3/api-docs`
- Configuration in `infra/config/swagger/OpenApiConfig.java`
- Swagger endpoints are public (no auth required)

## Common Patterns

### External API Integration (ExerciseDB)
```java
// WebClient configuration with headers
@Bean
public WebClient exerciseDbWebClient() {
    return WebClient.builder()
            .baseUrl(apiUrl)
            .defaultHeader("X-RapidAPI-Key", apiKey)
            .build();
}

// Reactive API call with error handling
webClient.get()
    .uri("/exercises/name/{name}", name)
    .retrieve()
    .bodyToFlux(ExerciseDTO.class)
    .onErrorResume(WebClientResponseException.class, this::handleApiError)
    .block();
```

### Auto-populating from External Data
```java
// WorkoutExerciseService: If exerciseId provided, fetch from ExerciseDB
if (dto.exerciseId() != null && !dto.exerciseId().isBlank()) {
    ExerciseDTO data = exerciseService.getById(dto.exerciseId());
    entity.setExerciseName(data.name());
    entity.setDescription(String.join(". ", data.instructions()));
}
```

### Entity Relationships
```java
@OneToOne(fetch = FetchType.LAZY, optional = false)
@JoinColumn(name = "tb_user_id", nullable = false, unique = true)
private User user;
```

### Service Layer Validation
```java
User user = userRepository.findById(dto.userId())
    .orElseThrow(() -> StudentException.notFound(dto.userId()));
```

### Null-Safe Updates in Mappers
```java
if (dto.goal() != null) student.setGoal(dto.goal());
```

### Environment-Specific Config
- `application.yml`: Base config (JPA, Flyway, server settings)
- `application-local.yml`: Datasource URL with env vars `${POSTGRES_PORT}`, JWT secret

## Key Files Reference

- **Security**: `infra/config/security/SecurityConfiguration.java`
- **Global Exception Handling**: `infra/exceptions/GlobalExceptionHandler.java`
- **JWT Logic**: `auth/service/TokenService.java`
- **User Model**: `user/domain/User.java` (implements `UserDetails`)
- **Migration Template**: `db/migration/V1__initial_schema.sql`
- **Test Template**: `test/.../user/controller/UserControllerTest.java`
- **External API Integration**: `exercise/service/ExerciseService.java`, `exercise/config/ExerciseDbConfig.java`
- **ExerciseDB Integration Guide**: `EXERCISEDB_INTEGRATION.md`
