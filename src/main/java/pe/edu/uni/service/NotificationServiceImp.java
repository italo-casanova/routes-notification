package pe.edu.uni.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.Document;

@ApplicationScoped
public class NotificationServiceImp implements NotificationService {

    private final MongoCollection<Document> collection;

    public NotificationServiceImp(MongoClient mongoClient) {
        this.collection = mongoClient.getDatabase("notifications").getCollection("notifications");
    }

    @Override
    public void saveNotification(String message) {
        Document notification = new Document()
                .append("message", message)
                .append("timestamp", System.currentTimeMillis());
        collection.insertOne(notification);
    }

    @Override
    public void sendNotification(String userId, String message) {
        // Save notification in the database
        saveNotification(String.format("User: %s, Message: %s", userId, message));
        // Simulate sending the notification
        System.out.printf("Notification sent to User: %s - Message: %s%n", userId, message);
    }
}
