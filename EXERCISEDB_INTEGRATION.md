# ExerciseDB Integration

## Overview

The CompleFit Backend integrates with the ExerciseDB API (https://exercisedb.p.rapidapi.com) to provide a comprehensive database of exercises that users can search and add to their workouts.

## Setup

### 1. Get API Key

1. Visit [RapidAPI ExerciseDB](https://rapidapi.com/justin-WFnsXH_t6/api/exercisedb)
2. Subscribe to the API (free tier available)
3. Copy your API key

### 2. Configure Environment Variables

Add to your `.env` file or environment:

```bash
EXERCISEDB_API_KEY=your-api-key-here
```

### 3. Configuration Files

The configuration is already set up in:
- `application.yml` - Base configuration
- `application-local.yml` - Local development settings

## API Endpoints

### Exercise Search Endpoints

All endpoints require authentication (JWT token).

#### Get All Exercises
```
GET /api/exercises?limit=20&offset=0
```

#### Get Exercise by ID
```
GET /api/exercises/{id}
```

#### Search by Name
```
GET /api/exercises/search/name/{name}?limit=20&offset=0
```
Example: `/api/exercises/search/name/press`

#### Search by Target Muscle
```
GET /api/exercises/search/target/{target}?limit=20&offset=0
```
Examples: `glutes`, `biceps`, `triceps`, `abs`

#### Search by Body Part
```
GET /api/exercises/search/bodypart/{bodyPart}?limit=20&offset=0
```
Examples: `back`, `chest`, `legs`, `shoulders`

#### Search by Equipment
```
GET /api/exercises/search/equipment/{equipment}?limit=20&offset=0
```
Examples: `dumbbell`, `barbell`, `cable`, `bodyweight`, `kettlebell`

## Using Exercises in Workouts

### Workflow

1. **Search for Exercise**: Use the search endpoints to find exercises
2. **Get Exercise Details**: Note the `id` from the search results
3. **Create Workout Exercise**: Use the `exerciseId` in the workout exercise creation

### Example: Creating a Workout Exercise

**Option 1: Using ExerciseDB (Recommended)**
```json
POST /api/workout-exercises
{
  "exerciseId": "0001",  // ID from ExerciseDB API
  "sets": 3,
  "repetitions": 12,
  "weight": 20.5,
  "restTimeSeconds": 60,
  "durationSeconds": 30
}
```

When you provide an `exerciseId`, the system automatically fetches:
- Exercise name
- Description (from instructions)
- Video URL (animated GIF)

**Option 2: Manual Entry**
```json
POST /api/workout-exercises
{
  "exerciseName": "Barbell Bench Press",
  "description": "Lie on bench, lower bar to chest, press up",
  "videoUrl": "https://example.com/video.mp4",
  "sets": 3,
  "repetitions": 12,
  "weight": 60.0,
  "restTimeSeconds": 90,
  "durationSeconds": 40
}
```

## Technical Details

### Architecture

- **ExerciseService**: Handles all communication with ExerciseDB API
- **ExerciseController**: REST endpoints for exercise search
- **WorkoutExerciseService**: Enhanced to auto-populate from ExerciseDB
- **WebClient**: Spring WebFlux reactive HTTP client for API calls

### Error Handling

- `ExerciseException.notFound()`: Exercise ID not found in database
- `ExerciseException.apiError()`: API communication error
- `ExerciseException.invalidApiKey()`: Authentication failed (401/403)

### Response Format

ExerciseDTO includes:
```typescript
{
  id: string,
  name: string,
  gifUrl: string,              // Animated GIF demonstration
  instructions: string[],      // Step-by-step guide
  primaryMuscle: string,       // Target muscle
  secondaryMuscles: string[],  // Additional muscles worked
  bodyPart: string,            // Body region
  equipment: string            // Required equipment
}
```

## Rate Limiting

The RapidAPI free tier includes rate limits. Consider:
- Caching frequently accessed exercises (future enhancement)
- Using pagination wisely
- Informing users about search limits

## Testing

Access Swagger UI at `http://localhost:8090/swagger-ui.html` to test all endpoints interactively.

## Future Enhancements

- [ ] Cache popular exercises in local database
- [ ] Favorite/bookmark exercises per user
- [ ] Custom exercise creation
- [ ] Exercise history and analytics
