package ca.ulaval.glo4002.cafe.application.inventory;

import ca.ulaval.glo4002.cafe.domain.inventory.Ingredients;
import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import ca.ulaval.glo4002.cafe.domain.inventory.InventoryRepository;
import ca.ulaval.glo4002.cafe.ui.rest.DTO.InventoryDTO;
import jakarta.inject.Inject;

public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final InventoryAssembler inventoryAssembler;

    @Inject
    public InventoryService(InventoryRepository inventoryRepository, InventoryAssembler inventoryAssembler) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryAssembler = inventoryAssembler;
    }

    public void addIngredientsInInventory(Ingredients ingredients) {
        Inventory inventory = this.inventoryRepository.getInventory();
        inventory.addIngredientsQuantitiesFrom(ingredients);
        this.inventoryRepository.save(inventory);
    }

    public InventoryDTO getInventory() {
        Inventory inventory = this.inventoryRepository.getInventory();
        return this.inventoryAssembler.assembleToDTO(inventory);
    }
}
