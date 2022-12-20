package ca.ulaval.glo4002.cafe.ui.rest.resources;

import ca.ulaval.glo4002.cafe.application.close.CloseService;
import ca.ulaval.glo4002.cafe.application.config.ConfigService;
import ca.ulaval.glo4002.cafe.domain.config.Config;
import ca.ulaval.glo4002.cafe.ui.rest.DTO.ConfigDTO;
import ca.ulaval.glo4002.cafe.ui.rest.validators.config.ConfigValidator;
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

    private final ConfigService configService;
    private final ConfigValidator configValidator;
    private final CloseService closeService;

    @Inject
    public ConfigResource(ConfigService configService, ConfigValidator configValidator, CloseService closeService) {
        this.configService = configService;
        this.configValidator = configValidator;
        this.closeService = closeService;
    }

    @POST
    public Response config(ConfigDTO configDTO) {
        Config config = configValidator.toConfig(configDTO);
        this.configService.updateConfig(config);
        this.closeService.closeCafe();
        return Response
                .ok()
                .build();
    }
}
