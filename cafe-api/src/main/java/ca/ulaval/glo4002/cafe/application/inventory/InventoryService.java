package ca.ulaval.glo4002.cafe.application.inventory;

import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import ca.ulaval.glo4002.cafe.domain.inventory.InventoryRepository;
import ca.ulaval.glo4002.cafe.ui.rest.DTO.InventoryDTO;
import jakarta.inject.Inject;

import java.util.List;

public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final InventoryAssembler inventoryAssembler;

    @Inject
    public InventoryService(InventoryRepository inventoryRepository, InventoryAssembler inventoryAssembler) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryAssembler = inventoryAssembler;
    }

    public void addIngredientsInInventory(List<Ingredient> ingredients) {
        Inventory inventory = this.inventoryRepository.getInventory();
        ingredients.forEach(inventory::addIngredient);
    }

    public InventoryDTO getInventory() {
        Inventory inventory = this.inventoryRepository.getInventory();
        return this.inventoryAssembler.toDTO(inventory);
    }

    public void reset() {
        this.inventoryRepository.getInventory().reset();
    }
}
