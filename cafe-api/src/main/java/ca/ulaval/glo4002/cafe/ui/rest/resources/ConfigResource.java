package ca.ulaval.glo4002.cafe.ui.rest.resources;

import ca.ulaval.glo4002.cafe.application.close.CloseService;
import ca.ulaval.glo4002.cafe.domain.config.Config;
import ca.ulaval.glo4002.cafe.ui.rest.DTO.ConfigDTO;
import ca.ulaval.glo4002.cafe.ui.rest.assemblers.config.ConfigAssembler;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/config")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ConfigResource {

    private final CloseService closeService;
    private final ConfigAssembler configValidator;

    @Inject
    public ConfigResource(CloseService closeService, ConfigAssembler configValidator) {
        this.closeService = closeService;
        this.configValidator = configValidator;
    }

    @POST
    public Response config(ConfigDTO configDTO) {
        Config config = configValidator.assembleToConfig(configDTO);
        this.closeService.updateConfig(config);
        return Response
                .ok()
                .build();
    }
}
