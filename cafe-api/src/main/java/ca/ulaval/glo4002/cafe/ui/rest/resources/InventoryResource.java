package ca.ulaval.glo4002.cafe.ui.rest.resources;

import ca.ulaval.glo4002.cafe.application.inventory.InventoryService;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.ui.rest.DTO.InventoryDTO;
import ca.ulaval.glo4002.cafe.ui.rest.assemblers.inventory.InventoryAssembler;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/inventory")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InventoryResource {
    private final InventoryAssembler inventoryAssembler;
    private final InventoryService inventoryService;

    @Inject
    public InventoryResource(InventoryService inventoryService, InventoryAssembler inventoryAssembler) {
        this.inventoryService = inventoryService;
        this.inventoryAssembler = inventoryAssembler;
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
        List<Ingredient> inventory = inventoryAssembler.assembleFromDTO(inventoryDTO);
        this.inventoryService.addIngredientsInInventory(inventory);
        return Response
                .ok()
                .build();
    }
}
