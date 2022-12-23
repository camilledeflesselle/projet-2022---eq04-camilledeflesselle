package ca.ulaval.glo4002.cafe.ui.rest.assemblers.validators.inventory;

import ca.ulaval.glo4002.cafe.domain.inventory.IngredientId;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredients;
import ca.ulaval.glo4002.cafe.domain.inventory.InventoryRepository;
import ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.InventoryRepositoryInMemory;
import ca.ulaval.glo4002.cafe.ui.rest.DTO.InventoryDTO;
import ca.ulaval.glo4002.cafe.ui.rest.assemblers.inventory.IngredientsAssembler;
import jakarta.ws.rs.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IngredientsAssemblerTest {
    private IngredientsAssembler ingredientsAssembler;

    private static final IngredientId AN_INGREDIENT_NAME = new IngredientId("ingredient");

    @BeforeEach
    public void before() {
        InventoryRepository inventoryRepository = new InventoryRepositoryInMemory();
        this.ingredientsAssembler = new IngredientsAssembler(inventoryRepository);
    }

    @Test
    public void givenAnEmptyInventoryDTO_whenValidatingInventory_thenThrowBadRequestException() {
        Map<String, Integer> emptyInventoryDTO = new HashMap<>();
        InventoryDTO inventoryDTO = new InventoryDTO(emptyInventoryDTO);

        assertThrows(BadRequestException.class,
            () -> ingredientsAssembler.assembleFromDTO(inventoryDTO));
    }

    @Test
    public void givenAnInventoryDTOWithANullIngredientName_whenValidatingInventory_thenThrowBadRequestException() {
        Map<String, Integer> inventoryDTOWithNullIngredientName = new HashMap<>();
        inventoryDTOWithNullIngredientName.put(null, 1);
        InventoryDTO inventoryDTO = new InventoryDTO(inventoryDTOWithNullIngredientName);

        assertThrows(BadRequestException.class,
            () -> ingredientsAssembler.assembleFromDTO(inventoryDTO));
    }

    @Test
    public void givenAnInventoryDTOWithAnEmptyIngredientName_whenValidatingInventory_thenThrowBadRequestException() {
        Map<String, Integer> inventoryDTOWithEmptyIngredientName = new HashMap<>();
        inventoryDTOWithEmptyIngredientName.put("", 1);
        InventoryDTO inventoryDTO = new InventoryDTO(inventoryDTOWithEmptyIngredientName);

        assertThrows(BadRequestException.class,
            () -> ingredientsAssembler.assembleFromDTO(inventoryDTO));
    }

    @Test
    public void givenAnInventoryDTOWithANegativeIngredientQuantity_whenValidatingInventory_thenThrowBadRequestException() {
        Map<String, Integer> inventoryDTOWithNegativeIngredientQuantity = new HashMap<>();
        inventoryDTOWithNegativeIngredientQuantity.put(AN_INGREDIENT_NAME.getName(), -2);
        InventoryDTO inventoryDTO = new InventoryDTO(inventoryDTOWithNegativeIngredientQuantity);

        assertThrows(BadRequestException.class,
            () -> ingredientsAssembler.assembleFromDTO(inventoryDTO));
    }


    @Test
    public void givenAnInventoryDTOWithAnIngredientNameThatDoesExist_whenAssembleFromDTO_thenNotAddIngredientToList() {
        Map<String, Integer> inventoryDTOWithIngredientNameThatDoesExist = new HashMap<>();
        inventoryDTOWithIngredientNameThatDoesExist.put(AN_INGREDIENT_NAME.getName(), 2);
        InventoryDTO inventoryDTO = new InventoryDTO(inventoryDTOWithIngredientNameThatDoesExist);

        Ingredients result = ingredientsAssembler.assembleFromDTO(inventoryDTO);

        assertFalse(result.contains(AN_INGREDIENT_NAME));
    }
}