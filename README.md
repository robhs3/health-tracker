# Health Tracker Backend

A simple Spring Boot backend application for logging and retrieving daily health metrics such as nutritional information and bodyweight.

This project is intentionally minimal and is being developed incrementally as a learning and portfolio project, with the goal of evolving into a personal health tracking tool.

---

## Features

- Log daily health metrics via REST API
- Retrieve all logged metrics
- Retrieve the most recently logged metric
- Health check endpoint for service monitoring
- Retrieve health metrics between dates
- Retrieve health stats between dates
- SQLite integration for data persistence
- Validation for health metric inputs
- WebMVC controller slice tests (MockMvc) for GET endpoints (happy path)
---

## API Endpoints

### Health Check
`GET /api/health`

Returns `ok` if the server is running.

### Get All Daily Metrics
`GET /api/daily-metrics`

### Get Latest Daily Metric
`GET /api/daily-metrics/latest`

### Get Daily Metrics Between Dates
`GET /api/daily-metrics?from=YYYY-MM-DD&to=YYYY-MM-DD`

### Add a Daily Metric
`POST /api/daily-metrics`

Example request body:
```json
{
  "date": "2025-12-11",
  "weight": 168.2,
  "calories": 2600,
  "protein": 180
}
```

### Get Health Stats Between Dates
`GET /api/daily-metrics/stats?from=YYYY-MM-DD&to=YYYY-MM-DD`

## Tech Stack
- Java 17
- Spring Boot 4
- Maven
- RESTful API
- SQLite and JPA
- Layered architecture (controller -> service -> repository)

## Running the Project Locally

`./mvnw spring-boot:run`

### The application will start on: 

http://localhost:8080

## Testing
Controller-level WebMVC slice tests are implemented for GET endpoints to validate routing, parameter binding, and successful responses using mocked service dependencies.

## Future Improvements
- Build a simple frontend for data entry and visualization

