package pe.edu.uni.resource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.jboss.logging.Logger;

import pe.edu.uni.dto.LocationUpdate;
import pe.edu.uni.notification.LocationConsumer;

@ApplicationScoped
@Path("/locations")
public class LocationResource {

    private static final Logger LOG = Logger.getLogger(LocationResource.class);

    @Inject
    LocationConsumer locationConsumer;

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON) // Specify the accepted media type
    public Response updateLocation(LocationUpdate update) {
        LOG.infof("Received location update via REST: %s", update);

        try {
            locationConsumer.processLocationUpdate(update);
            return Response.ok("Location processed successfully.").build();
        } catch (Exception e) {
            LOG.error("Error processing location update", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Error processing location update.").build();
        }
    }
}
