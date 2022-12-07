package ca.ulaval.glo4002.cafe.infrastructure.rest.resources;

import ca.ulaval.glo4002.cafe.application.layout.LayoutService;
import ca.ulaval.glo4002.cafe.infrastructure.rest.DTO.LayoutDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/layout")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class LayoutResource {
    private final LayoutService layoutService;

    @Inject
    public LayoutResource(LayoutService layoutService) {
        this.layoutService = layoutService;
    }

    @GET
    public LayoutDTO layout() {
        return this.layoutService.getLayout();
    }
}
