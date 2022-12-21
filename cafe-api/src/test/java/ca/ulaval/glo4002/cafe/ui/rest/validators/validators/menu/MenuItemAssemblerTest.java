package ca.ulaval.glo4002.cafe.ui.rest.validators.validators.menu;

import ca.ulaval.glo4002.cafe.application.menu.CoffeeFactory;
import ca.ulaval.glo4002.cafe.domain.bill.Amount;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientId;
import ca.ulaval.glo4002.cafe.domain.recipe.Recipe;
import ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.MenuItemRepositoryInMemory;
import ca.ulaval.glo4002.cafe.ui.rest.DTO.MenuItemDTO;
import ca.ulaval.glo4002.cafe.ui.rest.DTO.RecipeDTO;
import ca.ulaval.glo4002.cafe.ui.rest.validators.menu.MenuItemAssembler;
import jakarta.ws.rs.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MenuItemAssemblerTest {

    private static final Map<String, Integer> SOME_CORRECT_INGREDIENTS = Map.of("Espresso", 1, "Water", 0, "Chocolate", 1, "Milk", 1);
    private static final Map<String, Integer> SOME_INGREDIENTS_WITH_INCORRECT_NAMES = Map.of("Espresso", 1, "Lemon", 10,  "Sugar", 1);

    private static final Map<String, Integer> SOME_INGREDIENTS_WITHOUT_BASE_INGREDIENT= Map.of("Espresso", 1, "Water", 0, "Chocolate", 1);
    private static final Map<String, Integer> SOME_INCORRECT_WITH_NEGATIVE_QUANTITIES = Map.of("Espresso", 1, "Water", -1, "Chocolate", 1, "Milk", 1);
    private static final float SOME_PRICE = 1.5f;
    private static final String A_NAME_ALREADY_IN_MENU = "Cappuccino";
    private static final String SOME_NAME_NOT_IN_MENU = "Pumpkin Latte";

    private MenuItemAssembler menuItemAssembler;
    private MenuItemRepositoryInMemory menuItemRepository;

    @BeforeEach
    void setUpMenuItemAssembler() {
        CoffeeFactory coffeeFactory = new CoffeeFactory();
        menuItemRepository = new MenuItemRepositoryInMemory(coffeeFactory);
        menuItemAssembler = new MenuItemAssembler(menuItemRepository);
    }

    @Test
    public void givenANameOfCoffeeAlreadyInMenu_whenAssembleMenuItem_thenRaiseException() {
        MenuItemDTO menuItemDTO = new MenuItemDTO(A_NAME_ALREADY_IN_MENU, new RecipeDTO(SOME_CORRECT_INGREDIENTS), SOME_PRICE);
        assertThrows(BadRequestException.class, () -> menuItemAssembler.menuItemDTOToMenuItem(menuItemDTO));
    }

    @Test
    public void givenANegativeCost_whenAssembleMenuItem_thenRaiseException() {
        MenuItemDTO menuItemDTO = new MenuItemDTO(SOME_NAME_NOT_IN_MENU, new RecipeDTO(SOME_CORRECT_INGREDIENTS), -10);
        assertThrows(BadRequestException.class, () -> menuItemAssembler.menuItemDTOToMenuItem(menuItemDTO));
    }

    @Test
    public void givenANewNameOfItem_whenAssembleMenuItem_thenDoNotRaiseException() {
        MenuItemDTO menuItemDTO = new MenuItemDTO(SOME_NAME_NOT_IN_MENU, new RecipeDTO(SOME_CORRECT_INGREDIENTS), SOME_PRICE);
        assertDoesNotThrow(() -> menuItemAssembler.menuItemDTOToMenuItem(menuItemDTO));
    }

    @Test
    public void givenAnIngredientWithNegativeQuantity_whenAssembleRecipe_thenRaiseException() {
        MenuItemDTO menuItemDTO = new MenuItemDTO(SOME_NAME_NOT_IN_MENU, new RecipeDTO(SOME_INCORRECT_WITH_NEGATIVE_QUANTITIES), SOME_PRICE);
        assertThrows(BadRequestException.class, () -> menuItemAssembler.menuItemDTOToRecipe(menuItemDTO));
    }

    @Test
    public void givenAnIngredientWithIncorrectName_whenAssembleRecipe_thenRaiseException() {
        MenuItemDTO menuItemDTO = new MenuItemDTO(SOME_NAME_NOT_IN_MENU, new RecipeDTO(SOME_INGREDIENTS_WITH_INCORRECT_NAMES), SOME_PRICE);
        assertThrows(BadRequestException.class, () -> menuItemAssembler.menuItemDTOToRecipe(menuItemDTO));
    }

    @Test
    public void givenAnIngredientWithoutBaseIngredient_whenAssembleRecipe_thenRaiseException() {
        MenuItemDTO menuItemDTO = new MenuItemDTO(SOME_NAME_NOT_IN_MENU, new RecipeDTO(SOME_INGREDIENTS_WITHOUT_BASE_INGREDIENT), SOME_PRICE);
        assertThrows(BadRequestException.class, () -> menuItemAssembler.menuItemDTOToRecipe(menuItemDTO));
    }

    @Test
    public void givenAValidMenuItemDTO_whenAssembleMenuItem_thenReturnMenuItem() {
        MenuItemDTO menuItemDTO = new MenuItemDTO(SOME_NAME_NOT_IN_MENU, new RecipeDTO(SOME_CORRECT_INGREDIENTS), SOME_PRICE);
        assertNotNull(menuItemAssembler.menuItemDTOToMenuItem(menuItemDTO));
    }

    @Test
    public void givenAValidMenuItemDTO_whenAssembleMenuItem_thenMenuItemHasCorrectName() {
        MenuItemDTO menuItemDTO = new MenuItemDTO(SOME_NAME_NOT_IN_MENU, new RecipeDTO(SOME_CORRECT_INGREDIENTS), SOME_PRICE);
        assertEquals(SOME_NAME_NOT_IN_MENU, menuItemAssembler.menuItemDTOToMenuItem(menuItemDTO).getName());
    }

    @Test
    public void givenAValidMenuItemDTO_whenAssembleMenuItem_thenMenuItemHasCorrectPrice() {
        MenuItemDTO menuItemDTO = new MenuItemDTO(SOME_NAME_NOT_IN_MENU, new RecipeDTO(SOME_CORRECT_INGREDIENTS), SOME_PRICE);
        assertEquals(new Amount(SOME_PRICE), menuItemAssembler.menuItemDTOToMenuItem(menuItemDTO).getPrice());
    }

    @Test
    public void givenAValidMenuItemDTO_whenAssembleRecipe_thenThereIs4Ingredients() {
        MenuItemDTO menuItemDTO = new MenuItemDTO(SOME_NAME_NOT_IN_MENU, new RecipeDTO(SOME_CORRECT_INGREDIENTS), SOME_PRICE);

        assertEquals(4, menuItemAssembler.menuItemDTOToRecipe(menuItemDTO).getIngredients().size());
    }

    @Test
    public void givenAValidMenuItemDTO_whenAssembleRecipe_thenThereIsCorrectIngredients() {
        MenuItemDTO menuItemDTO = new MenuItemDTO(SOME_NAME_NOT_IN_MENU, new RecipeDTO(SOME_CORRECT_INGREDIENTS), SOME_PRICE);

        Recipe recipe = menuItemAssembler.menuItemDTOToRecipe(menuItemDTO);

        SOME_CORRECT_INGREDIENTS.forEach((name, quantity) -> {
            assertTrue(recipe.contains(new IngredientId(name), quantity));
        });
    }

}