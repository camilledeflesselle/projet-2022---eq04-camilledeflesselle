package ca.ulaval.glo4002.cafe.ui.rest.assemblers.validators.inventory;

import ca.ulaval.glo4002.cafe.domain.inventory.IInventoryRepository;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientId;
import ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.InventoryRepositoryInMemory;
import ca.ulaval.glo4002.cafe.ui.rest.DTO.InventoryDTO;
import ca.ulaval.glo4002.cafe.ui.rest.assemblers.inventory.InventoryAssembler;
import jakarta.ws.rs.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InventoryAssemblerTest {
    private InventoryAssembler inventoryAssembler;

    private static final IngredientId AN_INGREDIENT_NAME = new IngredientId("ingredient");

    @BeforeEach
    public void before() {
        IInventoryRepository inventoryRepository = new InventoryRepositoryInMemory();
        this.inventoryAssembler = new InventoryAssembler(inventoryRepository);
    }

    @Test
    public void givenAnEmptyInventoryDTO_whenValidatingInventory_thenThrowBadRequestException() {
        Map<String, Integer> emptyInventoryDTO = new HashMap<>();
        InventoryDTO inventoryDTO = new InventoryDTO(emptyInventoryDTO);

        assertThrows(BadRequestException.class,
            () -> inventoryAssembler.assembleFromDTO(inventoryDTO));
    }

    @Test
    public void givenAnInventoryDTOWithANullIngredientName_whenValidatingInventory_thenThrowBadRequestException() {
        Map<String, Integer> inventoryDTOWithNullIngredientName = new HashMap<>();
        inventoryDTOWithNullIngredientName.put(null, 1);
        InventoryDTO inventoryDTO = new InventoryDTO(inventoryDTOWithNullIngredientName);

        assertThrows(BadRequestException.class,
            () -> inventoryAssembler.assembleFromDTO(inventoryDTO));
    }

    @Test
    public void givenAnInventoryDTOWithAnEmptyIngredientName_whenValidatingInventory_thenThrowBadRequestException() {
        Map<String, Integer> inventoryDTOWithEmptyIngredientName = new HashMap<>();
        inventoryDTOWithEmptyIngredientName.put("", 1);
        InventoryDTO inventoryDTO = new InventoryDTO(inventoryDTOWithEmptyIngredientName);

        assertThrows(BadRequestException.class,
            () -> inventoryAssembler.assembleFromDTO(inventoryDTO));
    }

    @Test
    public void givenAnInventoryDTOWithANegativeIngredientQuantity_whenValidatingInventory_thenThrowBadRequestException() {
        Map<String, Integer> inventoryDTOWithNegativeIngredientQuantity = new HashMap<>();
        inventoryDTOWithNegativeIngredientQuantity.put(AN_INGREDIENT_NAME.getName(), -2);
        InventoryDTO inventoryDTO = new InventoryDTO(inventoryDTOWithNegativeIngredientQuantity);

        assertThrows(BadRequestException.class,
            () -> inventoryAssembler.assembleFromDTO(inventoryDTO));
    }


    @Test
    public void givenAnInventoryDTOWithAnIngredientNameThatDoesExist_whenAssembleFromDTO_thenNotAddIngredientToList() {
        Ingredient ingredient = new Ingredient(AN_INGREDIENT_NAME, 0);
        Map<String, Integer> inventoryDTOWithIngredientNameThatDoesExist = new HashMap<>();
        inventoryDTOWithIngredientNameThatDoesExist.put(AN_INGREDIENT_NAME.getName(), 2);
        InventoryDTO inventoryDTO = new InventoryDTO(inventoryDTOWithIngredientNameThatDoesExist);

        List<Ingredient> result = inventoryAssembler.assembleFromDTO(inventoryDTO);

        assertFalse(result.contains(ingredient));
    }
}