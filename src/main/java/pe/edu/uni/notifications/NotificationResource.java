package pe.edu.uni.notifications;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/notifications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotificationResource {

    @Inject
    NotificationProducer producer;

    @POST
    public Response sendNotification(String message) {
        producer.sendNotification(message);
        return Response.accepted().build();
    }
}
