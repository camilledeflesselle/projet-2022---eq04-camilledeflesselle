package ca.ulaval.glo4002.cafe.infrastructure.rest.validators.inventory;

import ca.ulaval.glo4002.cafe.domain.inventory.IInventoryRepository;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.infrastructure.rest.DTO.InventoryDTO;
import jakarta.ws.rs.BadRequestException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryValidator {
    private final IInventoryRepository inventoryRepository;

    public InventoryValidator(IInventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public Map<Ingredient, Integer> inventoryDTOToListIngredients(InventoryDTO inventoryDTO) {
        Map<Ingredient, Integer> ingredients = new HashMap<>();
        List<String> ingredientsNames = inventoryRepository.getIngredientsNames();
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
            if (!ingredientsNames.contains(ingredientName)) {
                throw new BadRequestException("Ingredient name is not valid");
            }
            Ingredient ingredient = new Ingredient(ingredientName);
            ingredients.put(ingredient, inventory.get(ingredientName));
        }
        return ingredients;
    }
}
