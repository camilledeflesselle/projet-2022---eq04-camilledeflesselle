package ca.ulaval.glo4002.cafe.ui.rest.resources;

import ca.ulaval.glo4002.cafe.application.close.CloseService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/close")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class CloseResource {
    private final CloseService closeService;

    @Inject
    public CloseResource(CloseService closeService) {
        this.closeService = closeService;
    }

    @POST
    public Response close() {
        this.closeService.closeCafe();
        return Response
                .ok()
                .build();
    }
}
