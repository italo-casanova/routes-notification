package pe.edu.uni.service;

public interface NotificationService {
    void saveNotification(String message);

    void sendNotification(String userId, String message);
}
