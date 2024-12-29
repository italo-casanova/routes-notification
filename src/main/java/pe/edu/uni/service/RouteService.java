package pe.edu.uni.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import jakarta.enterprise.context.ApplicationScoped;
import pe.edu.uni.dto.Route;
import pe.edu.uni.dto.GeoJsonPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@ApplicationScoped
public class RouteService {

    private final MongoCollection<Document> routeCollection;

    public RouteService(MongoClient mongoClient) {
        this.routeCollection = mongoClient.getDatabase("travel_agency").getCollection("routes");
    }

    public Route getRouteForUser(String userId) {
        Document routeDoc = routeCollection.find(Filters.eq("userId", userId)).first();
        if (routeDoc != null) {
            // return toRoute(routeDoc);
            // TODO: implement this method
            //
            return null;
        }
        return null;
    }

    public List<GeoJsonPoint> findIntermediateSpotsNear(String userId, double latitude, double longitude,
            double radiusInMeters) {
        Route route = getRouteForUser(userId);
        if (route == null) {
            return new ArrayList<>();
        }

        return route.getIntermediateSpots().stream()
                .filter(spot -> isNear(latitude, longitude, spot.getLatitude(), spot.getLongitude(), radiusInMeters))
                .collect(Collectors.toList());
    }

    // Helper method to determine proximity using Haversine formula
    private boolean isNear(double lat1, double lon1, double lat2, double lon2, double radiusInMeters) {
        double distance = haversine(lat1, lon1, lat2, lon2);
        return distance <= (radiusInMeters / 1000.0); // Convert meters to kilometers
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371; // Earth's radius in kilometers
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Distance in kilometers
    }

    // private Route toRoute(Document document) {
    //     String id = document.getObjectId("_id").toString();
    //     String userId = document.getString("userId");
    //     String source = document.getString("source");
    //     Document destinationDoc = (Document) document.get("destination");
    //     GeoJsonPoint destination = new Destination(
    //             destinationDoc.getList("coordinates", Double.class).get(1),
    //             destinationDoc.getList("coordinates", Double.class).get(0));
    //     List<Document> intermediateDocs = document.getList("intermediateSpots", Document.class);
    //     List<IntermediateSpot> intermediateSpots = intermediateDocs.stream()
    //             .map(doc -> new IntermediateSpot(
    //                     doc.getString("name"),
    //                     doc.getDouble("latitude"),
    //                     doc.getDouble("longitude")))
    //             .collect(Collectors.toList());
    //
    //     return new Route(id, userId, source, destination, intermediateSpots);
    // }
}
