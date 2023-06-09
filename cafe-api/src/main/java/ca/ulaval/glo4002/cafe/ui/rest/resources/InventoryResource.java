package ca.ulaval.glo4002.cafe.ui.rest.resources;

import ca.ulaval.glo4002.cafe.application.inventory.InventoryService;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredients;
import ca.ulaval.glo4002.cafe.ui.rest.DTO.InventoryDTO;
import ca.ulaval.glo4002.cafe.ui.rest.assemblers.inventory.IngredientsAssembler;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/inventory")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InventoryResource {
    private final IngredientsAssembler ingredientsAssembler;
    private final InventoryService inventoryService;

    @Inject
    public InventoryResource(InventoryService inventoryService, IngredientsAssembler ingredientsAssembler) {
        this.inventoryService = inventoryService;
        this.ingredientsAssembler = ingredientsAssembler;
    }

    @GET
    public Response getInventory() {
        InventoryDTO inventoryDTO = this.inventoryService.getInventory();
        return Response
                .ok()
                .entity(inventoryDTO.getInventory())
                .build();
    }

    @PUT
    public Response putInventory(InventoryDTO inventoryDTO) {
        Ingredients ingredients = ingredientsAssembler.assembleFromDTO(inventoryDTO);
        this.inventoryService.addIngredientsInInventory(ingredients);
        return Response
                .ok()
                .build();
    }
}
