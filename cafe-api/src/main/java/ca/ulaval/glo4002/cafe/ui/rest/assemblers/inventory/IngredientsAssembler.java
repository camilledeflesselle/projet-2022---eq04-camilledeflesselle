package ca.ulaval.glo4002.cafe.ui.rest.assemblers.inventory;

import ca.ulaval.glo4002.cafe.domain.inventory.IngredientId;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredients;
import ca.ulaval.glo4002.cafe.domain.inventory.InventoryRepository;
import ca.ulaval.glo4002.cafe.ui.rest.DTO.InventoryDTO;
import jakarta.ws.rs.BadRequestException;

import java.util.Map;

public class IngredientsAssembler {
    private final InventoryRepository inventoryRepository;

    public IngredientsAssembler(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public Ingredients assembleFromDTO(InventoryDTO inventoryDTO) {
        Ingredients newIngredients = new Ingredients();
        Map<String, Integer> inventory = inventoryDTO.getInventory();

        if (inventory.isEmpty()) {
            throw new BadRequestException("Inventory is empty");
        }

        for (String ingredientName : inventory.keySet()) {
            if (ingredientName == null || ingredientName.isEmpty()) {
                throw new BadRequestException("Ingredient name cannot be empty");
            }
            if (inventory.get(ingredientName) < 0) {
                throw new BadRequestException("Ingredient quantity cannot be negative");
            }
            if (this.inventoryRepository.getInventory().contains(new IngredientId(ingredientName))) {
                IngredientId id = new IngredientId(ingredientName);
                newIngredients.addIngredient(id, inventory.get(ingredientName));
            }
        }
        return newIngredients;
    }
}
