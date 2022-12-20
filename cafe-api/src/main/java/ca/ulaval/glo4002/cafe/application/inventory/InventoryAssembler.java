package ca.ulaval.glo4002.cafe.application.inventory;

import ca.ulaval.glo4002.cafe.domain.inventory.IngredientId;
import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import ca.ulaval.glo4002.cafe.infrastructure.rest.DTO.InventoryDTO;
import java.util.HashMap;
import java.util.Map;

public class InventoryAssembler {

    public InventoryDTO toDTO(Inventory inventory) {
        return new InventoryDTO(this.getInventoryStringify(inventory));
    }

    private Map<String, Integer> getInventoryStringify(Inventory inventory) {
        Map<String, Integer> stringInventory = new HashMap<>();

        for (IngredientId ingredientId : inventory.getInventory().keySet()) {
            stringInventory.put(ingredientId.getName(), inventory.findIngredientQuantity(ingredientId));
        }
        return stringInventory;
    }
}
