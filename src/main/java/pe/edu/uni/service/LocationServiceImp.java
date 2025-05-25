package pe.edu.uni.service;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import pe.edu.uni.dto.LocationUpdate;
import pe.edu.uni.dto.PointOfInterest;

@ApplicationScoped
public class LocationServiceImp implements LocationService {

    @Inject
    PoiService poiService;

    @Override
    public List<String> getClosestPois(LocationUpdate locationUpdate) {
        double latitude = locationUpdate.getLatitude();
        double longitude = locationUpdate.getLongitude();
        double radiusInMeters = 500;

        return poiService.getAllPois().stream()
                .filter(poi -> isNear(latitude, longitude, poi.getCoordinates()[0], poi.getCoordinates()[1], radiusInMeters))
                .map(PointOfInterest::getName)
                .collect(Collectors.toList());
    }

    @Override
	public boolean isNear(double lat1, double lon1, double lat2, double lon2, double radiusInMeters) {
        double distance = haversine(lat1, lon1, lat2, lon2);
        return distance <= (radiusInMeters / 1000.0); // Convert meters to kilometers
    }

    @Override
	public double haversine(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
