package pe.edu.uni.dto;

import java.util.HashMap;
import java.util.Map;

public class GeoJsonPoint {
    private String type;
    private double[] coordinates;

    // Constructors
    public GeoJsonPoint() {
        this.type = "Point";
    }

    public GeoJsonPoint(double latitude, double longitude) {
        this();
        this.coordinates = new double[] {longitude, latitude};
    }

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double latitude, double longitude) {
        this.coordinates = new double[] {longitude, latitude};
    }

    public double getLatitude() {
        return this.coordinates[1];
    }

    public double getLongitude() {
        return this.coordinates[0];
    }

    // Helper to convert to GeoJSON Map
    public Map<String, Object> toGeoJson() {
        Map<String, Object> geoJson = new HashMap<>();
        geoJson.put("type", this.type);
        geoJson.put("coordinates", this.coordinates);
        return geoJson;
    }
}
