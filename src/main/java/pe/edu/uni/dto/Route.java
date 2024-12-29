package pe.edu.uni.dto;

import java.util.HashMap;
import java.util.List;

public class Route {
    private String id;
    private String userId;
    private GeoJsonPoint source;
    private GeoJsonPoint destination;
    private List<HashMap<String, GeoJsonPoint>> intermediateSpots;

    public Route() {
    }

    public Route(String id, String userId, GeoJsonPoint source, GeoJsonPoint destination,
            List<HashMap<String, GeoJsonPoint>> intermediateSpots) {
        this.id = id;
        this.userId = userId;
        this.source = source;
        this.destination = destination;
        this.intermediateSpots = intermediateSpots;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public GeoJsonPoint getSource() {
        return this.source;
    }

    public void setSource(GeoJsonPoint source) {
        this.source = source;
    }

    public GeoJsonPoint getDestination() {
        return destination;
    }

    public void setDestination(GeoJsonPoint destination) {
        this.destination = destination;
    }

    public List<HashMap<String, GeoJsonPoint>> getIntermediateSpots() {
        return intermediateSpots;
    }

    public void setIntermediateSpots(List<HashMap<String, GeoJsonPoint>> intermediateSpots) {
        this.intermediateSpots = intermediateSpots;
    }
}
