package pe.edu.uni.notification;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import pe.edu.uni.dto.LocationUpdate;
import pe.edu.uni.dto.Route;
import pe.edu.uni.service.NotificationService;
import pe.edu.uni.service.RouteService;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

@ApplicationScoped
public class LocationConsumer {

    private static final Logger LOG = Logger.getLogger(LocationConsumer.class);

    @Inject
    NotificationService notificationService;

    @Inject
    RouteService routeService;

    @Incoming("location-updates")
    public void processLocationUpdate(LocationUpdate update) {
        LOG.infof("Received location update: %s", update);

        // Retrieve the route and POIs for the user
    //     Route route = routeService.getRouteForUser(update.getUserId());
    //     if (route == null) {
    //         LOG.warnf("No route found for user %s", update.getUserId());
    //         return;
    //     }
    //
    //     // Check proximity to POIs and destination
    //     for (PointOfInterest poi : route.getPointsOfInterest()) {
    //         if (isNear(update.getLatitude(), update.getLongitude(), poi.getLatitude(), poi.getLongitude())) {
    //             notificationService.sendNotification(update.getUserId(), "You're near " + poi.getName());
    //         }
    //     }
    //
    //     // Check proximity to destination
    //     if (isNear(update.getLatitude(), update.getLongitude(), route.getDestinationLat(), route.getDestinationLon())) {
    //         notificationService.sendNotification(update.getUserId(), "You've reached your destination!");
    //     }
    }

    private boolean isNear(double lat1, double lon1, double lat2, double lon2) {
        double distance = haversine(lat1, lon1, lat2, lon2);
        return distance <= 0.5;
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
