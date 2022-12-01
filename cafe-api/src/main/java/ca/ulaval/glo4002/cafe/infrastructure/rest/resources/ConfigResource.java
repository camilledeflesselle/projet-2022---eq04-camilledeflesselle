package ca.ulaval.glo4002.cafe.infrastructure.rest.resources;

import ca.ulaval.glo4002.cafe.application.bill.BillService;
import ca.ulaval.glo4002.cafe.application.close.CloseService;
import ca.ulaval.glo4002.cafe.application.layout.LayoutService;
import ca.ulaval.glo4002.cafe.application.seating.SeatingService;
import ca.ulaval.glo4002.cafe.domain.bill.TipRate;
import ca.ulaval.glo4002.cafe.infrastructure.rest.DTO.ConfigDTO;
import ca.ulaval.glo4002.cafe.infrastructure.rest.validators.config.ConfigValidator;
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
    private final SeatingService seatingService;
    private final BillService billService;
    private final LayoutService layoutService;
    private final CloseService closeService;
    private final ConfigValidator configValidator;

    @Inject
    public ConfigResource(SeatingService seatingService, BillService billService, ConfigValidator configValidator, CloseService closeService, LayoutService layoutService) {
        this.seatingService = seatingService;
        this.billService = billService;
        this.configValidator = configValidator;
        this.layoutService = layoutService;
        this.closeService = closeService;
    }

    @POST
    public Response config(ConfigDTO configDTO) {
        this.configValidator.validateConfig(configDTO);
        this.layoutService.updateConfig(configDTO.getOrganizationName(), configDTO.getCubeSize());
        this.billService.updateConfig(configDTO.getCountry(), configDTO.getProvince(), configDTO.getState(), new TipRate(configDTO.getGroupTipRate().doubleValue()));
        this.seatingService.updateConfig(this.configValidator.getGroupReservationMethod());
        this.closeService.closeCafe();
        return Response
                .ok()
                .build();
    }
}
