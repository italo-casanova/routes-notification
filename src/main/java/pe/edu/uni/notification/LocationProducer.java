package pe.edu.uni.notification;

import jakarta.enterprise.context.ApplicationScoped;
import pe.edu.uni.dto.LocationUpdate;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

@ApplicationScoped
public class LocationProducer {

    private static final Logger LOG = Logger.getLogger(LocationProducer.class);

    @Channel("location-updates-out")
    Emitter<LocationUpdate> emitter;

    public void sendLocationUpdate(LocationUpdate update) {
        emitter.send(update)
                .whenComplete((success, failure) -> {
                    if (failure != null) {
                        LOG.errorf("Failed to send location update: %s", failure.getMessage());
                    } else {
                        LOG.infof("Location update sent: %s", update);
                    }
                });
    }
}
