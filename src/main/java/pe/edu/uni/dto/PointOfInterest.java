package pe.edu.uni.dto;

import java.util.HashMap;
import java.util.Map;

public class PointOfInterest {
    private String id;
    private String name;
    private double[] coordinates;

    public PointOfInterest() {}

    public PointOfInterest(String id, String name, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.coordinates = new double[] {longitude, latitude};
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double latitude, double longitude) {
        this.coordinates = new double[] {longitude, latitude};
    }

    public Map<String, Object> toGeoJson() {
        Map<String, Object> geoJson = new HashMap<>();
        geoJson.put("type", "Point");
        geoJson.put("coordinates", this.coordinates);
        return geoJson;
    }
}
