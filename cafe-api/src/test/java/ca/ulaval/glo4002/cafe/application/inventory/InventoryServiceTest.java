package ca.ulaval.glo4002.cafe.application.inventory;

import ca.ulaval.glo4002.cafe.domain.inventory.Ingredients;
import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import ca.ulaval.glo4002.cafe.domain.inventory.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class InventoryServiceTest {
    private InventoryRepository inventoryRepositoryMock;
    private InventoryAssembler inventoryAssembler;
    private Inventory inventoryMock;
    private Ingredients ingredientsMock;

    private InventoryService inventoryService;

    @BeforeEach
    public void before() {
        inventoryRepositoryMock = mock(InventoryRepository.class);
        inventoryAssembler = mock(InventoryAssembler.class);
        ingredientsMock = mock(Ingredients.class);
        inventoryMock = mock(Inventory.class);
        when(inventoryRepositoryMock.getInventory()).thenReturn(inventoryMock);
        inventoryService = new InventoryService(inventoryRepositoryMock, inventoryAssembler);
    }

    @Test
    public void whenAddIngredientsInInventory_thenGetCurrentInventory() {
        inventoryService.addIngredientsInInventory(ingredientsMock);
        verify(inventoryRepositoryMock).getInventory();
    }

    @Test
    public void whenAddIngredients_thenAddIngredientsInInventory() {
        inventoryService.addIngredientsInInventory(ingredientsMock);
        verify(inventoryMock).addIngredientsQuantitiesFrom(ingredientsMock);
    }

    @Test
    public void whenAddIngredients_thenSaveInventory() {
        inventoryService.addIngredientsInInventory(ingredientsMock);
        verify(inventoryRepositoryMock).save(inventoryMock);
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