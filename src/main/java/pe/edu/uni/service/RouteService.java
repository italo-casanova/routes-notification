package pe.edu.uni.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import jakarta.enterprise.context.ApplicationScoped;
import pe.edu.uni.dto.Route;
import pe.edu.uni.dto.GeoJsonPoint;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class RouteService {

    private final MongoCollection<Document> routeCollection;

    public RouteService(MongoClient mongoClient) {
        this.routeCollection = mongoClient.getDatabase("travel_agency").getCollection("routes");
    }

    // Fetches a route for a specific user
    public Route getRouteForUser(String userId) {
        return routeCollection.find(Filters.eq("userId", userId))
                .map(this::toRoute)
                .first();
    }

    // Finds intermediate spots near a given location
    public List<GeoJsonPoint> findIntermediateSpotsNear(String userId, double latitude, double longitude, double radiusInMeters) {
        return getRouteForUser(userId)
                .getIntermediateSpots()
                .stream()
                .flatMap(map -> map.values().stream())
                .filter(point -> isNear(latitude, longitude, point.getLatitude(), point.getLongitude(), radiusInMeters))
                .collect(Collectors.toList());
    }

    // Converts MongoDB document to Route
    private Route toRoute(Document document) {
        String id = document.getObjectId("_id").toString();
        String userId = document.getString("userId");

        // Convert the source and destination to GeoJsonPoint
        GeoJsonPoint source = extractGeoJsonPoint(document, "source");
        GeoJsonPoint destination = extractGeoJsonPoint(document, "destination");

        // Convert intermediate spots
        List<HashMap<String, GeoJsonPoint>> intermediateSpots = document.getList("intermediateSpots", Document.class)
                .stream()
                .map(this::toIntermediateSpotMap)
                .collect(Collectors.toList());

        return new Route(id, userId, source, destination, intermediateSpots);
    }

    // Helper method to extract GeoJsonPoint from a document field
    private GeoJsonPoint extractGeoJsonPoint(Document document, String field) {
        return document.containsKey(field)
                ? new GeoJsonPoint(
                        document.get(field, Document.class).getList("coordinates", Double.class).get(0),
                        document.get(field, Document.class).getList("coordinates", Double.class).get(1))
                : null;
    }

    // Converts a MongoDB document to an intermediate spot map
    private HashMap<String, GeoJsonPoint> toIntermediateSpotMap(Document spotDoc) {
        String name = spotDoc.getString("name");
        GeoJsonPoint point = new GeoJsonPoint(
                spotDoc.getList("coordinates", Double.class).get(0),
                spotDoc.getList("coordinates", Double.class).get(1)
        );
        return new HashMap<>() {{
            put(name, point);
        }};
    }

    // Helper method to determine proximity using the Haversine formula
    private boolean isNear(double lat1, double lon1, double lat2, double lon2, double radiusInMeters) {
        return haversine(lat1, lon1, lat2, lon2) <= (radiusInMeters / 1000.0);
    }

    // Haversine formula for distance calculation
    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371; // Earth's radius in kilometers
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
