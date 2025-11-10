# CompleFit Backend

A comprehensive fitness management platform built with Spring Boot 3.5.6 and Java 25, featuring user management, workout tracking, and integration with the ExerciseDB API.

## Features

- 🔐 **JWT Authentication** - Secure token-based authentication with refresh tokens
- 👥 **Multi-Role System** - Students, Trainers, and Admins with role-based access
- 💪 **Exercise Database** - Integration with ExerciseDB API for 1000+ exercises
- 📝 **Workout Management** - Create, track, and manage custom workouts
- 🏋️ **Workout Exercises** - Detailed exercise tracking with sets, reps, and rest times
- 📊 **PostgreSQL Database** - Robust data persistence with Flyway migrations
- 📖 **OpenAPI Documentation** - Interactive API docs with Swagger UI
- ⚡ **Rate Limiting** - Built-in API rate limiting with Bucket4j

## Tech Stack

- **Java 25**
- **Spring Boot 3.5.6**
  - Spring Web
  - Spring Data JPA
  - Spring Security
  - Spring WebFlux (for external API calls)
- **PostgreSQL** - Primary database
- **Flyway** - Database migration tool
- **Auth0 JWT** - Token generation and validation
- **Lombok** - Reduce boilerplate code
- **SpringDoc OpenAPI** - API documentation
- **Docker Compose** - Local development environment

## Quick Start

### Prerequisites

- Java 25 or higher
- Docker and Docker Compose
- Maven 3.9+

### 1. Clone the Repository

```bash
git clone https://github.com/viniciusyr/complefit-backend.git
cd complefit-backend
```

### 2. Set Up Environment Variables

Copy the example environment file:

```bash
cp .env.example .env
```

Edit `.env` and configure:
- Database credentials
- JWT secret (min 256 bits)
- ExerciseDB API key (get from [RapidAPI](https://rapidapi.com/justin-WFnsXH_t6/api/exercisedb))

### 3. Start PostgreSQL

```bash
docker-compose up -d
```

This starts:
- PostgreSQL on port 5450
- pgAdmin on port 8085 (http://localhost:8085)

### 4. Run the Application

```bash
./mvnw spring-boot:run
```

The application will start on `http://localhost:8090`

### 5. Access API Documentation

Open Swagger UI: `http://localhost:8090/swagger-ui.html`

## API Endpoints

### Authentication (`/api/auth`)

- `POST /api/auth/login` - Login with email and password
- `POST /api/auth/refresh` - Refresh access token
- `POST /api/auth/logout` - Logout and invalidate refresh token

### Users (`/api/users`)

- `POST /api/users/register` - Register new user (public)
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

### Students (`/api/students`)

- `POST /api/students` - Create student profile
- `GET /api/students` - Get all students
- `GET /api/students/{id}` - Get student by ID
- `PUT /api/students/{id}` - Update student
- `DELETE /api/students/{id}` - Delete student

### Trainers (`/api/trainers`)

- `POST /api/trainers` - Create trainer profile
- `GET /api/trainers` - Get all trainers
- `GET /api/trainers/{id}` - Get trainer by ID
- `PUT /api/trainers/{id}` - Update trainer
- `DELETE /api/trainers/{id}` - Delete trainer

### Exercises (`/api/exercises`) 🆕

Search and browse 1000+ exercises from ExerciseDB:

- `GET /api/exercises` - Get all exercises (paginated)
- `GET /api/exercises/{id}` - Get exercise by ID
- `GET /api/exercises/search/name/{name}` - Search by name
- `GET /api/exercises/search/target/{muscle}` - Search by target muscle
- `GET /api/exercises/search/bodypart/{bodyPart}` - Search by body part
- `GET /api/exercises/search/equipment/{equipment}` - Search by equipment

### Workouts (`/api/workouts`)

- `POST /api/workouts` - Create workout
- `GET /api/workouts` - Get all workouts
- `GET /api/workouts/{id}` - Get workout by ID
- `PUT /api/workouts/{id}` - Update workout
- `DELETE /api/workouts/{id}` - Delete workout

### Workout Exercises (`/api/workout-exercises`)

- `POST /api/workout-exercises` - Add exercise to workout
- `GET /api/workout-exercises` - Get all workout exercises
- `GET /api/workout-exercises/{id}` - Get workout exercise by ID
- `PUT /api/workout-exercises/{id}` - Update workout exercise
- `DELETE /api/workout-exercises/{id}` - Delete workout exercise

## ExerciseDB Integration

See [EXERCISEDB_INTEGRATION.md](EXERCISEDB_INTEGRATION.md) for detailed documentation on:
- How to get an API key
- Available search endpoints
- Using exercises in workouts
- Auto-populating exercise details

### Example: Creating a Workout Exercise with ExerciseDB

```json
POST /api/workout-exercises
Authorization: Bearer <your-jwt-token>

{
  "exerciseId": "0001",  // From ExerciseDB API
  "sets": 3,
  "repetitions": 12,
  "weight": 20.5,
  "restTimeSeconds": 60,
  "durationSeconds": 30
}
```

The system automatically fetches and populates:
- Exercise name
- Instructions as description
- Animated GIF URL

## Development

### Project Structure

```
src/main/java/com/complefit/complefit/
├── auth/              # Authentication & authorization
├── user/              # User management
├── student/           # Student profiles
├── trainer/           # Trainer profiles
├── workout/           # Workout management
├── workoutexercise/   # Workout exercise details
├── exercise/          # ExerciseDB API integration
└── infra/
    ├── config/        # Security, Swagger, etc.
    └── exceptions/    # Global error handling
```

Each module follows the pattern:
- `domain/` - JPA entities
- `dto/` - Request/Response DTOs (Java records)
- `mapper/` - Entity ↔ DTO conversion
- `repository/` - Data access
- `service/` - Business logic
- `controller/` - REST endpoints
- `exception/` - Custom exceptions

### Running Tests

```bash
./mvnw test
```

### Building for Production

```bash
./mvnw clean package -DskipTests
java -jar target/CompleFit-0.0.1-SNAPSHOT.jar
```

## Database Schema

The application uses Flyway for database migrations. Schema files are in `src/main/resources/db/migration/`.

Key tables:
- `tb_users` - Base user information with roles
- `tb_students` - Student-specific data
- `tb_trainers` - Trainer credentials and info
- `tb_workouts` - Workout programs
- `tb_workout_exercises` - Individual exercises in workouts
- `tb_auth_users` - Refresh token storage

## Configuration

### Application Profiles

- `local` - Local development (default)
- Add custom profiles in `application-{profile}.yml`

### Key Properties

```yaml
server.port: 8090
spring.profiles.active: local

# Database
spring.datasource.url: jdbc:postgresql://localhost:5450/postgres

# JWT
api.security.token.expiration-hours: 2

# ExerciseDB
exercisedb.api-url: https://exercisedb.p.rapidapi.com
```

## Security

- JWT-based authentication with refresh tokens
- Password encryption using BCrypt
- Role-based access control (STUDENT, TRAINER, ADMIN)
- Rate limiting on API endpoints
- CORS configuration for frontend integration

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License.

## Contact

- Repository: [github.com/viniciusyr/complefit-backend](https://github.com/viniciusyr/complefit-backend)
- Issues: [github.com/viniciusyr/complefit-backend/issues](https://github.com/viniciusyr/complefit-backend/issues)

---

Built with ❤️ using Spring Boot and Java 25
