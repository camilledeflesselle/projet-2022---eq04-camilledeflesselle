package ca.ulaval.glo4002.cafe.ui.rest.resources;

import ca.ulaval.glo4002.cafe.application.menu.MenuService;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.recipe.Recipe;
import ca.ulaval.glo4002.cafe.ui.rest.DTO.MenuItemDTO;
import ca.ulaval.glo4002.cafe.ui.rest.assemblers.menu.MenuItemAssembler;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/menu")
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
        MenuItem newMenuItem = this.menuItemAssembler.menuItemDTOToMenuItem(menuItemDTO);
        Recipe newRecipe = this.menuItemAssembler.menuItemDTOToRecipe(menuItemDTO);
        this.menuService.addMenuItem(newMenuItem, newRecipe);
        return Response
                .ok()
                .build();
    }
}
