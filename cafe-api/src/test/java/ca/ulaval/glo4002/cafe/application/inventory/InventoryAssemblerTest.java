package ca.ulaval.glo4002.cafe.application.inventory;

import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import ca.ulaval.glo4002.cafe.ui.rest.DTO.InventoryDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class InventoryAssemblerTest {

    @Test
    void givenAnInventory_whenAssembleInventory_thenInventoryDTOHasAllIngredients() {
        InventoryAssembler inventoryAssembler = new InventoryAssembler();
        Inventory inventory = mock(Inventory.class);

        InventoryDTO inventoryDTO = inventoryAssembler.assembleToDTO(inventory);

        assertEquals(inventoryDTO.getInventory().size(), inventory.getInventory().size());
    }
}