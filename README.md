# Health Tracker Backend

A simple Spring Boot backend application for logging and retrieving daily health metrics such as nutritional information and bodyweight.

This project is intentionally minimal and is being developed incrementally as a learning and portfolio project, with the goal of evolving into a personal health tracking tool.

---

## Features

- Log daily health metrics via REST API
- Retrieve all logged metrics
- Retrieve the most recently logged metric
- Health check endpoint for service monitoring
---

## API Endpoints

### Health Check
`GET /api/health`

Returns `ok` if the server is running.

### Get All Daily Metrics
`GET /api/daily-metrics`

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

### Get Latest Daily Metric
`GET /api/daily-metrics/latest`

## Tech Stack
- Java 21
- Spring Boot
- Maven
- RESTful API
- In-memory storage (temporary)
- Layered architecture (controller -> service)

## Running the Project Locally

`./mvnw spring-boot:run`

### The application will start on: 

http://localhost:8080

## Future Improvements
- Persist data using a database (SQLite or PostgreSQL)
- Add validation for inputs
- Introduce a repository layer
- Build a simple frontend for data entry and visualization

