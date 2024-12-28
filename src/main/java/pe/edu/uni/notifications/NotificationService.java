package pe.edu.uni.notifications;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.Document;

@ApplicationScoped
public class NotificationService {

    private final MongoCollection<Document> collection;

    public NotificationService(MongoClient mongoClient) {
        this.collection = mongoClient.getDatabase("notifications").getCollection("notifications");
    }

    public void saveNotification(String message) {
        Document notification = new Document()
                .append("message", message)
                .append("timestamp", System.currentTimeMillis());
        collection.insertOne(notification);
    }
}
