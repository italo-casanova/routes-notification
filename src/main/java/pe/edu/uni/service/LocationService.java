package pe.edu.uni.service;

import pe.edu.uni.dto.LocationUpdate;

import java.util.List;

public interface LocationService {
    List<String> getClosestPois(LocationUpdate locationUpdate);
    boolean isNear(double lat1, double lon1, double lat2, double lon2, double radiusInMeters);
    double haversine(double lat1, double lon1, double lat2, double lon2);

}
