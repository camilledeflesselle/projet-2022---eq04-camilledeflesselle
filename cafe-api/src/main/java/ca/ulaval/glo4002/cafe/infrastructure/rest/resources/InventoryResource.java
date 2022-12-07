package ca.ulaval.glo4002.cafe.infrastructure.rest.resources;

import ca.ulaval.glo4002.cafe.application.inventory.InventoryService;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.infrastructure.rest.DTO.InventoryDTO;
import ca.ulaval.glo4002.cafe.infrastructure.rest.validators.inventory.InventoryValidator;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;

@Path("/inventory")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class InventoryResource {
    private final InventoryValidator inventoryValidator;
    private final InventoryService inventoryService;

    @Inject
    public InventoryResource(InventoryService inventoryService, InventoryValidator inventoryValidator) {
        this.inventoryService = inventoryService;
        this.inventoryValidator = inventoryValidator;
    }

    @GET
    public Response getInventory() {
        Map<String, Integer> inventory = this.inventoryService.getInventoryStringify();
        InventoryDTO inventoryDTO = new InventoryDTO(inventory);
        return Response
                .ok()
                .entity(inventoryDTO.getInventory())
                .build();
    }

    @PUT
    public Response putInventory(InventoryDTO inventoryDTO) {
        List<Ingredient> inventory = inventoryValidator.inventoryDTOToListIngredients(inventoryDTO);
        this.inventoryService.addIngredientsInInventory(inventory);
        return Response
                .ok()
                .build();
    }
}
