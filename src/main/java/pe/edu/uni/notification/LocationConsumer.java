package pe.edu.uni.notification;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pe.edu.uni.dto.GeoJsonPoint;
import pe.edu.uni.dto.LocationUpdate;
import pe.edu.uni.service.NotificationService;
import pe.edu.uni.service.PoiService;
import pe.edu.uni.service.RouteService;
import pe.edu.uni.dto.Route;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;


@ApplicationScoped
public class LocationConsumer {

    private static final Logger LOG = Logger.getLogger(LocationConsumer.class);

    @Inject
    NotificationService notificationService;

    @Inject
    RouteService routeService;

    @Inject
    PoiService poiService;

    @Incoming("location-updates")
    public void processLocationUpdate(LocationUpdate update) {
        LOG.infof("Received location update: %s", update);

        notifyIfNearPois(update);

        Route route = routeService.getRouteForUser(update.getUserId());
        if (route == null) {
            LOG.warnf("No route found for user %s", update.getUserId());
            return;
        }

        notifyIfNearIntermediateSpots(update, route);

        notifyIfNearDestination(update, route);
    }

    private void notifyIfNearPois(LocationUpdate update) {
        poiService.getAllPois().stream()
                .filter(poi -> isNear(update.getLatitude(), update.getLongitude(), poi.getCoordinates()[1], poi.getCoordinates()[0]))
                .forEach(poi -> notificationService.sendNotification(update.getUserId(), "You're near " + poi.getName()));
    }

    private void notifyIfNearIntermediateSpots(LocationUpdate update, Route route) {
        route.getIntermediateSpots().stream()
                .flatMap(map -> map.values().stream())
                .filter(spot -> isNear(update.getLatitude(), update.getLongitude(), spot.getLatitude(), spot.getLongitude())) // GeoJsonPoint: Y = latitude, X = longitude
                .forEach(spot -> notificationService.sendNotification(update.getUserId(), "You're near an intermediate spot!"));
    }

    private void notifyIfNearDestination(LocationUpdate update, Route route) {
        GeoJsonPoint destination = route.getDestination();
        if (destination != null && isNear(update.getLatitude(), update.getLongitude(), destination.getLatitude(), destination.getLongitude())) {
            notificationService.sendNotification(update.getUserId(), "You've reached your destination!");
        }
    }

    private boolean isNear(double lat1, double lon1, double lat2, double lon2) {
        double distance = haversine(lat1, lon1, lat2, lon2);
        return distance <= 0.5; // Within 500 meters
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
