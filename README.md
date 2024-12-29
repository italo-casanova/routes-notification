# **Real-Time Notifications**

Real-Time Notifications is a Java-based application built with **Quarkus**, the Supersonic Subatomic Java Framework. This project is designed to provide location-based notifications for travel agencies. It processes real-time location updates and notifies users when they are near points of interest (POIs), intermediate spots, or their destination.

## **Overview**

The application uses the following key features:
- **Apache Kafka**: Handles real-time streaming of location updates.
- **MongoDB**: Stores route data, including user-specific routes and intermediate spots.
- **REST API**: Provides endpoints for integration and testing.
- **SmallRye OpenAPI**: Documents the REST APIs with an interactive Swagger UI.
- **Quarkus Scheduler**: Executes scheduled tasks, if needed for maintenance or batch operations.

---

## **Getting Started**

### **Running the Application in Dev Mode**

Run the application in development mode with live coding enabled:

```bash
./mvnw quarkus:dev

Note: Access the Quarkus Dev UI at http://localhost:8080/q/dev/ to manage your application during development.
```

## Kafka Setup

Ensure Apache Kafka is running locally or remotely. Update the application.properties file to configure Kafka broker settings:

```properties
kafka.bootstrap.servers=localhost:9092
```

## Start Kafka services before running the application.

Packaging and Running the Application

Package the application into a JAR file:

```bash
./mvnw package
```

## APIs and Endpoints

### REST API

Location Update Endpoint: Submit real-time location updates for processing.

```SH
    POST /locations/update
    Content-Type: application/json
    Body: {
        "userId": "user123",
        "latitude": 40.782900,
        "longitude": -73.965400
    }

```

### Swagger UI

View and test the APIs interactively: http://localhost:8080/q/swagger-ui/
Data Requirements
MongoDB Collections
Routes Collection

    Stores user-specific routes, including source, destination, and intermediate spots.

Example document:

```json
{
    "_id": "64abcde1234f567890abcdef",
    "userId": "user123",
    "source": {
        "type": "Point",
        "coordinates": [-77.02824, -12.04318]
    },
    "destination": {
        "type": "Point",
        "coordinates": [-77.042793, -12.046374]
    },
    "intermediateSpots": [
        { "name": "Spot A", "coordinates": [-77.035, -12.045] },
        { "name": "Spot B", "coordinates": [-77.0365, -12.047] }
    ]
}

Points of Interest Collection

    Stores globally relevant points of interest.

Example document:

{
    "_id": "64poi1234f567890abcdef",
    "name": "Central Park",
    "location": {
        "type": "Point",
        "coordinates": [-73.965355, 40.782865]
    },
    "description": "A large public park in New York City, USA.",
    "category": "Park"
}
```
