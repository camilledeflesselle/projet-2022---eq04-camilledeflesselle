package ca.ulaval.glo4002.cafe.infrastructure.rest.validators.inventory;

import ca.ulaval.glo4002.cafe.domain.inventory.IInventoryRepository;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientId;
import ca.ulaval.glo4002.cafe.infrastructure.rest.DTO.InventoryDTO;
import jakarta.ws.rs.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class InventoryValidatorTest {
    private IInventoryRepository inventoryRepository;
    private InventoryValidator inventoryValidator;

    private static final IngredientId AN_INGREDIENT_NAME = new IngredientId("ingredient");

    @BeforeEach
    public void before() {
        this.inventoryRepository = mock(IInventoryRepository.class);
        this.inventoryValidator = new InventoryValidator(inventoryRepository);
    }

    @Test
    public void givenAnEmptyInventoryDTO_whenValidatingInventory_thenThrowBadRequestException() {
        Map<String, Integer> emptyInventoryDTO = new HashMap<>();
        InventoryDTO inventoryDTO = new InventoryDTO(emptyInventoryDTO);
        List<String> ingredientsNames = new ArrayList<>();
        ingredientsNames.add(AN_INGREDIENT_NAME.getName());
        //when(inventoryRepository.getIngredientsNames()).thenReturn(ingredientsNames);


        assertThrows(BadRequestException.class,
            () -> inventoryValidator.inventoryDTOToListIngredients(inventoryDTO));
    }

    @Test
    public void givenAnInventoryDTOWithANullIngredientName_whenValidatingInventory_thenThrowBadRequestException() {
        Map<String, Integer> inventoryDTOWithNullIngredientName = new HashMap<>();
        inventoryDTOWithNullIngredientName.put(null, 1);
        InventoryDTO inventoryDTO = new InventoryDTO(inventoryDTOWithNullIngredientName);
        List<String> ingredientsNames = new ArrayList<>();
        ingredientsNames.add(AN_INGREDIENT_NAME.getName());
        //when(inventoryRepository.getIngredientsNames()).thenReturn(ingredientsNames);

        assertThrows(BadRequestException.class,
            () -> inventoryValidator.inventoryDTOToListIngredients(inventoryDTO));
    }

    @Test
    public void givenAnInventoryDTOWithAnEmptyIngredientName_whenValidatingInventory_thenThrowBadRequestException() {
        Map<String, Integer> inventoryDTOWithEmptyIngredientName = new HashMap<>();
        inventoryDTOWithEmptyIngredientName.put("", 1);
        InventoryDTO inventoryDTO = new InventoryDTO(inventoryDTOWithEmptyIngredientName);
        List<String> ingredientsNames = new ArrayList<>();
        ingredientsNames.add(AN_INGREDIENT_NAME.getName());
        //when(inventoryRepository.getIngredientsNames()).thenReturn(ingredientsNames);

        assertThrows(BadRequestException.class,
            () -> inventoryValidator.inventoryDTOToListIngredients(inventoryDTO));
    }

    @Test
    public void givenAnInventoryDTOWithANegativeIngredientQuantity_whenValidatingInventory_thenThrowBadRequestException() {
        Map<String, Integer> inventoryDTOWithNegativeIngredientQuantity = new HashMap<>();
        inventoryDTOWithNegativeIngredientQuantity.put(AN_INGREDIENT_NAME.getName(), -2);
        InventoryDTO inventoryDTO = new InventoryDTO(inventoryDTOWithNegativeIngredientQuantity);
        List<String> ingredientsNames = new ArrayList<>();
        ingredientsNames.add(AN_INGREDIENT_NAME.getName());
        //when(inventoryRepository.getIngredientsNames()).thenReturn(ingredientsNames);

        assertThrows(BadRequestException.class,
            () -> inventoryValidator.inventoryDTOToListIngredients(inventoryDTO));
    }

    @Test
    public void givenAnInventoryDTOWithAnIngredientNameThatDoesNotExist_whenValidatingInventory_thenThrowBadRequestException() {
        Map<String, Integer> inventoryDTOWithIngredientNameThatDoesNotExist = new HashMap<>();
        inventoryDTOWithIngredientNameThatDoesNotExist.put("ingredient2", 2);
        InventoryDTO inventoryDTO = new InventoryDTO(inventoryDTOWithIngredientNameThatDoesNotExist);
        List<String> ingredientsNames = new ArrayList<>();
        ingredientsNames.add("ingredient1");
        //when(inventoryRepository.getIngredientsNames()).thenReturn(ingredientsNames);

        assertThrows(BadRequestException.class,
            () -> inventoryValidator.inventoryDTOToListIngredients(inventoryDTO));
    }

    @Test
    public void givenAnInventoryDTOWithAnIngredientNameThatDoesExist_whenValidatingInventory_() {
        Ingredient ingredient = new Ingredient(AN_INGREDIENT_NAME, 0);
        Map<String, Integer> inventoryDTOWithIngredientNameThatDoesExist = new HashMap<>();
        inventoryDTOWithIngredientNameThatDoesExist.put(AN_INGREDIENT_NAME.getName(), 2);
        InventoryDTO inventoryDTO = new InventoryDTO(inventoryDTOWithIngredientNameThatDoesExist);
        List<String> ingredientsNames = new ArrayList<>();
        ingredientsNames.add(AN_INGREDIENT_NAME.getName());
        //when(inventoryRepository.getIngredientsNames()).thenReturn(ingredientsNames);

        List<Ingredient> result = inventoryValidator.inventoryDTOToListIngredients(inventoryDTO);

        assertEquals(ingredientsNames.size(), result.size());
        assertTrue(result.contains(ingredient));
        assertEquals(2, result.get(0).getQuantity());
    }
}