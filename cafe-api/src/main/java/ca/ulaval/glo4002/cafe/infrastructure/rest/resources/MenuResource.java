package ca.ulaval.glo4002.cafe.infrastructure.rest.resources;

import ca.ulaval.glo4002.cafe.application.menu.MenuService;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.infrastructure.rest.DTO.CheckInDTO;
import ca.ulaval.glo4002.cafe.infrastructure.rest.DTO.MenuItemDTO;
import ca.ulaval.glo4002.cafe.infrastructure.rest.validators.menu.MenuItemAssembler;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/check-in")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class MenuResource {
    private final MenuService menuService;
    private final MenuItemAssembler menuItemAssembler;

    @Inject
    public MenuResource(MenuService menuService, MenuItemAssembler menuItemAssembler) {
        this.menuService = menuService;
        this.menuItemAssembler = menuItemAssembler;
    }

    @POST
    public Response addMenuItem(MenuItemDTO menuItemDTO) {
        this.menuItemAssembler.validate(menuItemDTO);
        this.menuService.addMenuItem(this.menuItemAssembler.menuItemDTOToMenuItem(menuItemDTO));
        return Response
                .ok()
                .build();
    }
}
