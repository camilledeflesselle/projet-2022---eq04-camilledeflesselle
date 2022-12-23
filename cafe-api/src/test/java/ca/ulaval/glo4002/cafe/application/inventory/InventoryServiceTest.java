package ca.ulaval.glo4002.cafe.application.inventory;

import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import ca.ulaval.glo4002.cafe.domain.inventory.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class InventoryServiceTest {
    private InventoryRepository inventoryRepositoryMock;
    private InventoryAssembler inventoryAssembler;
    private Inventory inventoryMock;
    private List<Ingredient> ingredientList;

    private InventoryService inventoryService;

    @BeforeEach
    public void before() {
        inventoryRepositoryMock = mock(InventoryRepository.class);
        inventoryAssembler = mock(InventoryAssembler.class);
        ingredientList =List.of(mock(Ingredient.class));
        inventoryMock = mock(Inventory.class);
        when(inventoryRepositoryMock.getInventory()).thenReturn(inventoryMock);
        inventoryService = new InventoryService(inventoryRepositoryMock, inventoryAssembler);
    }

    @Test
    public void whenAddIngredientsInInventory_thenGetCurrentInventory() {
        inventoryService.addIngredientsInInventory(ingredientList);
        verify(inventoryRepositoryMock).getInventory();
    }

    @Test
    public void whenAddIngredientsInInventory_thenAddIngredientsInInventory() {
        inventoryService.addIngredientsInInventory(ingredientList);
        verify(inventoryMock).addIngredient(ingredientList.get(0));
    }

    @Test
    public void whenGetInventory_thenGetCurrentInventory() {
        inventoryService.getInventory();
        verify(inventoryRepositoryMock).getInventory();
    }

    @Test
    public void whenGetInventory_thenAssembleInventoryToDTO() {
        inventoryService.getInventory();
        verify(inventoryAssembler).assembleToDTO(inventoryMock);
    }
}