# Commute_Telemetry
Collect real-world commute time data, aggregate it, display it; decide if that job's worth the drive.


# Commute Time Tracker

A Java Spring Boot application that collects real-world commute time data from the Google Maps Distance Matrix API on a scheduled basis, stores it in PostgreSQL, and visualizes trends through a simple web dashboard.

Users define their destinations, configure a schedule, and let the app automatically gather commute data over time — capturing best case, worst case, and average travel times across different times of day.

## Why This Exists

I've been a remote worker for more than 5 years, but want to consider something hybrid/onsite:
- LinkedIn saying "25 miles away" !=  < 45 min commute
- I'm in a new area, so I don't know what commutes are like from town A to B, or to C, or to XYZ.
- "Over two weeks, what's the most reliable window to leave?" Would a new job allow that schedule?

# Project Plan (All subject to change)

This project is currently in a planning phase. Below is an overview of the intended tech stack, architecture, structure, and anticipated prerequisites.

## Tech Stack 

| Layer       | Technology                          |
|-------------|-------------------------------------|
| Backend     | Java, Spring Boot                   |
| Scheduling  | Spring `@Scheduled` with cron       |
| Database    | PostgreSQL                          |
| Frontend    | Thymeleaf + Chart.js                |
| Containers  | Docker, Docker Compose              |
| API         | Google Maps Distance Matrix API     |

## Architecture (Subject to Change) 

```
┌────────────────────────────────────────────────────┐
│                    Spring Boot App                 │
│                                                    │
│  ┌─────────────┐   ┌─────────────┐  ┌───────────┐  │
│  │ Scheduler   │      Service       │ Controller│  │
│  │ (cron jobs) │─▶     (API+     ◀─ (REST +    │  │
│  │             │     aggregation)   │ Thymeleaf)│  │
│  └─────────────┘   └──────┬──────┘  └───────────┘  │
│                           │                        │
│                    ┌──────▼──────┐                 │
│                    │ Repository  │                 │
│                    └──────┬──────┘                 │
└───────────────────────────┼──────────────────────-─┘
                            │
                     ┌──────▼──────┐
                     │  PostgreSQL │
                     │  (Docker)   │
                     └─────────────┘
```

### Project Structure

```
src/main/java/com/commutetracker/
├── controller/       # REST endpoints + Thymeleaf views
├── service/          # Business logic, Google Maps API client, aggregation
├── scheduler/        # Cron-based scheduled data collection
├── model/            # JPA entities and DTOs
├── repository/       # Spring Data JPA data access
└── config/           # Rate limiter, app configuration
```

## Prerequisites (Subject to Change)

- [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- A [Google Maps API key](https://developers.google.com/maps/documentation/distance-matrix/get-api-key) with the Distance Matrix API enabled


# Intended Features (Subject to Change)

## Features

### Scheduled Data Collection
The default collection windows are:
- **Morning:** 6:00 AM – 11:00 AM (every 30 minutes)
- **Evening:** 4:00 PM – 8:30 PM (every 30 minutes)

Each collection captures `duration` (no traffic) and `duration_in_traffic` from the Distance Matrix API, along with the traffic model estimate (`best_guess`, `optimistic`, `pessimistic`).

### Commute Dashboard
A Chart.js-powered visualization showing:
- **Best case**, **average**, and **worst case** commute times per destination
- Trends across time of day
- Selectable destinations via dropdown/tabs

### Rate Limiter
A built-in rate limiter caps the number of API calls per day to prevent unexpected billing. The dashboard includes a status indicator showing current usage (e.g., "42 / 500 calls used today") with visual warnings as the limit is approached.

### Destination Management
A GUI for adding and managing destinations:
- Label each destination (e.g., "East Boston", "Inner DC", "Company A")
- Enter an address or city/state
- Address validation via the Google Maps Geocoding API

### API Key Setup
On first launch, the app guides you through entering and validating your Google Maps API key before any data collection begins.

## API Cost Estimate

The Distance Matrix API with traffic data (Advanced tier) costs $10 per 1,000 elements. For a typical setup:

| Parameter         | Value     |
|-------------------|-----------|
| Destinations      | 20        |
| Intervals per day | 21        |
| Duration          | 14 days   |
| **Total elements**| **5,880** |
| **Estimated cost**| **~$58.80** |

Google provides a **$200/month free credit**, so for most users this project will run entirely free.

## Docker Compose

The entire application runs in two containers:

```yaml
services:
  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      - GOOGLE_MAPS_API_KEY=${GOOGLE_MAPS_API_KEY}
      - SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/commute
    depends_on:
      - db
  db:
    image: postgres:16
    environment:
      - POSTGRES_DB=commute
      - POSTGRES_PASSWORD=${DB_PASSWORD}
    volumes:
      - commute-data:/var/lib/postgresql/data

volumes:
  commute-data:
```

Data persists between restarts via the Docker volume.

## License

MIT
