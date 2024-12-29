package pe.edu.uni.dto;

import jakarta.json.bind.annotation.JsonbProperty;

public class LocationUpdate {
    @JsonbProperty("userId")
    private String userId;

    @JsonbProperty("latitude")
    private double latitude;

    @JsonbProperty("longitude")
    private double longitude;

    // Constructors
    public LocationUpdate() {}

    public LocationUpdate(String userId, double latitude, double longitude) {
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "LocationUpdate{" +
                "userId='" + userId + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
