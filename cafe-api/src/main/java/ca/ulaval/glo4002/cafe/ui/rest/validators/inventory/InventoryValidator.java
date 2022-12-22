package ca.ulaval.glo4002.cafe.ui.rest.validators.inventory;

import ca.ulaval.glo4002.cafe.domain.inventory.IInventoryRepository;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientId;
import ca.ulaval.glo4002.cafe.ui.rest.DTO.InventoryDTO;
import jakarta.ws.rs.BadRequestException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InventoryValidator {
    private final IInventoryRepository inventoryRepository;

    public InventoryValidator(IInventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<Ingredient> inventoryDTOToListIngredients(InventoryDTO inventoryDTO) {
        List<Ingredient> ingredients = new ArrayList<>();
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
            if (!this.inventoryRepository.getInventory().contains(ingredientName)) {
                throw new BadRequestException("Ingredient name is not valid");
            }
            IngredientId id = new IngredientId(ingredientName);
            Ingredient ingredient = new Ingredient(id, inventory.get(ingredientName));
            ingredients.add(ingredient);
        }
        return ingredients;
    }
}
