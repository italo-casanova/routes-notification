package pe.edu.uni.notification;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import pe.edu.uni.service.NotificationService;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

@ApplicationScoped
public class NotificationConsumer {

    private static final Logger LOG = Logger.getLogger(NotificationConsumer.class);

    @Inject
    NotificationService notificationService;

    /**
     * Processes a notification received from the Kafka topic.
     *
     * @param message The message received from Kafka.
     */
    @Incoming("notifications")
    public void processNotification(String message) {
        try {
            LOG.infof("Received message from Kafka: %s", message);
            notificationService.saveNotification(message);
            LOG.infof("Message successfully saved to MongoDB: %s", message);
        } catch (Exception e) {
            LOG.errorf("Error processing message: %s", e.getMessage());
        }
    }
}
