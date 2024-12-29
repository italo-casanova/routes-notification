package pe.edu.uni.notification;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

@ApplicationScoped
public class NotificationProducer {

    private static final Logger LOG = Logger.getLogger(NotificationProducer.class);

    @Channel("notifications-out")
    Emitter<String> emitter;

    /**
     * Sends a notification to the Kafka topic.
     *
     * @param message The message to be sent.
     */
    public void sendNotification(String message) {
        emitter.send(message)
                .whenComplete((success, failure) -> {
                    if (failure != null) {
                        LOG.errorf("Failed to send message: %s", failure.getMessage());
                    } else {
                        LOG.infof("Message sent successfully: %s", message);
                    }
                });
    }
}
