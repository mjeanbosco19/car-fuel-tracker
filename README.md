# Car Management & Fuel Tracking

**CodeHills AEM Academy Technical Assignment**
**Author:** Jean Bosco Mugiraneza
**Date:** January 2026

---

## Overview

A car management and fuel tracking system built with Java 21 and Spring Boot 4.0.1. The project demonstrates:

- **Part 1:** REST API development with Spring Boot
- **Part 2:** Manual Servlet implementation (HttpServlet)
- **Part 3:** CLI application using java.net.http.HttpClient

## Project Structure

```text
car-fuel-tracker/
├── backend/                 # Spring Boot REST API + Servlet
│   ├── src/main/java/
│   │   └── com/codehills/cartracker/
│   │       ├── controller/  # REST endpoints
│   │       ├── service/     # Business logic
│   │       ├── model/       # Domain entities
│   │       ├── dto/         # Request/Response objects
│   │       ├── servlet/     # Manual HttpServlet
│   │       └── config/      # Servlet configuration
│   └── src/test/java/       # Unit tests
├── cli/                     # Command Line Interface
│   └── src/main/java/
│       └── com/codehills/cli/
│           ├── CarTrackerCli.java  # Main entry point
│           ├── ApiClient.java      # HTTP client
│           └── model/              # CLI models
├── car-tracker              # CLI wrapper script
├── pom.xml                  # Parent POM
└── README.md
```

---

## Technology Stack

| Component   | Technology                  |
| ----------- | --------------------------- |
| Language    | Java 21 (LTS)               |
| Framework   | Spring Boot 4.0.1           |
| Build Tool  | Maven 3.9+                  |
| HTTP Client | java.net.http.HttpClient    |
| Validation  | Jakarta Bean Validation     |
| API Docs    | SpringDoc OpenAPI (Swagger) |
| Testing     | JUnit 5 + AssertJ           |

---

## Quick Start

### Prerequisites

- Java 21 or higher
- Maven 3.9 or higher (or use included Maven Wrapper)

### Step 1: Clone the Repository

```bash
git clone https://github.com/mjeanbosco19/car-fuel-tracker.git && \
cd car-fuel-tracker
```

### Step 2: Build the Project

```bash
./mvnw clean package
```

### Step 3: Start the Backend Server

```bash
./mvnw spring-boot:run -pl backend
```

The server starts at `http://localhost:8080`

---

## CLI User Guide

### Create a car

```bash
./car-tracker create-car --brand <brand> --model <model> --year <year>
```

**Example:**

```bash
./car-tracker create-car --brand Toyota --model Corolla --year 2020
```

**Output:**

```
Car created successfully!
ID: 1
Brand: Toyota
Model: Corolla
Year: 2020
```

### Add fuel entries

```bash
./car-tracker add-fuel --carId <id> --liters <liters> --price <price> --odometer <odometer>
```

**Example:**

```bash
./car-tracker add-fuel --carId 1 --liters 50 --price 80.00 --odometer 10000
```

**Output:**

```
Fuel entry added successfully!
ID: 1
Liters: 50.0
Price: 80.00
Odometer: 10000 km
```

**Second entry:**

```bash
./car-tracker add-fuel --carId 1 --liters 45 --price 72.00 --odometer 10500
```

**Output:**

```
Fuel entry added successfully!
ID: 2
Liters: 45.0
Price: 72.00
Odometer: 10500 km
```

### Get fuel statistics

```bash
./car-tracker fuel-stats --carId <id>
```

**Example:**

```bash
./car-tracker fuel-stats --carId 1
```

**Output:**

```
Total fuel: 95 L
Total cost: 152.00
Average consumption: 9.0 L/100km
```

**Quick test** (copy and run all at once)

```bash
./car-tracker create-car --brand Toyota --model Corolla --year 2020 && \
./car-tracker add-fuel --carId 1 --liters 50 --price 80.00 --odometer 10000 && \
./car-tracker add-fuel --carId 1 --liters 45 --price 72.00 --odometer 10500 && \
./car-tracker fuel-stats --carId 1
```

### CLI Environment Variable

By default, CLI connects to `http://localhost:8080`. Override with:

```bash
export CAR_TRACKER_API_URL=http://your-server:8080
./car-tracker fuel-stats --carId 1
```

---

## Testing with cURL

### Create car

```bash
curl -X POST http://localhost:8080/api/cars \
  -H "Content-Type: application/json" \
  -d '{"brand":"Toyota","model":"Corolla","year":2020}'
```

### Add fuel entries

```bash
curl -X POST http://localhost:8080/api/cars/1/fuel \
 -H "Content-Type: application/json" \
 -d '{"liters":50,"price":80,"odometer":10000}'
```

```bash
curl -X POST http://localhost:8080/api/cars/1/fuel \
-H "Content-Type: application/json" \
-d '{"liters":45,"price":72,"odometer":10500}'
```

### Get stats (REST)

```bash
curl http://localhost:8080/api/cars/1/fuel/stats
```

### Get stats (Servlet)

```bash
curl "http://localhost:8080/servlet/fuel-stats?carId=1"
```

### List all cars

```bash
curl http://localhost:8080/api/cars
```

---

## Running Tests

```bash
# Run all tests
./mvnw test

# Run backend tests only
./mvnw test -pl backend

# Run with verbose output
./mvnw test -pl backend -Dtest=CarServiceTest
```

**Test Coverage:**

- `CarServiceTest` - 8 unit tests covering:
  - Car creation and retrieval
  - Fuel entry management
  - Statistics calculation
  - Fuel consumption algorithm

---

## API Documentation

### Interactive API Docs (Swagger UI)

Start the server and open: **<http://localhost:8080/swagger-ui.html>**

### REST Endpoints (Part 1)

#### Create Car

```http
POST /api/cars
Content-Type: application/json

{
  "brand": "Toyota",
  "model": "Corolla",
  "year": 2020
}
```

**Response (201 Created):**

```json
{
  "id": 1,
  "brand": "Toyota",
  "model": "Corolla",
  "year": 2020,
  "createdAt": "2026-01-01T10:00:00",
  "fuelEntriesCount": 0
}
```

#### List All Cars

```http
GET /api/cars
```

**Response (200 OK):**

```json
[
  {
    "id": 1,
    "brand": "Toyota",
    "model": "Corolla",
    "year": 2020,
    "createdAt": "2026-01-01T10:00:00",
    "fuelEntriesCount": 0
  }
]
```

#### Add Fuel Entry

```http
POST /api/cars/{id}/fuel
Content-Type: application/json

{
  "liters": 45.0,
  "price": 72.00,
  "odometer": 10500
}
```

**Response (201 Created):**

```json
{
  "id": 1,
  "liters": 45.0,
  "price": 72.0,
  "odometer": 10500,
  "timestamp": "2026-01-01T20:00:00"
}
```

#### Get Fuel Statistics

```http
GET /api/cars/{id}/fuel/stats
```

**Response (200 OK):**

```json
{
  "totalFuel": 95.0,
  "totalCost": 152.0,
  "averageConsumption": 9.0,
  "entryCount": 2
}
```

### Servlet Endpoint (Part 2)

```http
GET /servlet/fuel-stats?carId={id}
```

Same response as REST fuel stats endpoint.

---

## Input Validation

### Create Car Request

| Field | Type    | Constraints                 |
| ----- | ------- | --------------------------- |
| brand | String  | Required, max 50 characters |
| model | String  | Required, max 50 characters |
| year  | Integer | Required, 1886-2100         |

### Add Fuel Request

| Field    | Type    | Constraints                |
| -------- | ------- | -------------------------- |
| liters   | Double  | Required, must be positive |
| price    | Double  | Required, must be >= 0     |
| odometer | Integer | Required, must be positive |

### Error Responses

**400 Bad Request** - Validation failed:

```json
{
  "brand": "Brand is required",
  "year": "Year must be at least 1886"
}
```

**404 Not Found** - Car doesn't exist:

```json
{
  "error": "Car not found with id: 999"
}
```

---

---

## Architecture Decisions

### 1. In-Memory Storage

Uses `ConcurrentHashMap` for thread-safe storage without database complexity:

```java
private final Map<Long, Car> cars = new ConcurrentHashMap<>();
private final AtomicLong carIdGenerator = new AtomicLong(0);
```

### 2. Shared Service Layer

Both REST Controller and Servlet use the same `CarService` instance:

```text
CarController ──┐
                ├──> CarService ──> ConcurrentHashMap
FuelStatsServlet┘
```

### 3. Fuel Consumption Calculation

The algorithm properly calculates L/100km by:

1. Sorting entries by odometer
2. Excluding first entry's fuel (consumed before tracking started)
3. Calculating: `(fuel consumed / distance) * 100`

### 4. Multi-Module Maven

Clean separation between backend and CLI:

- Each module builds independently
- Shared parent POM for dependency management
- CLI packaged as executable JAR with dependencies

---

## Author

**Jean Bosco Mugiraneza**
Kigali - Rwanda
