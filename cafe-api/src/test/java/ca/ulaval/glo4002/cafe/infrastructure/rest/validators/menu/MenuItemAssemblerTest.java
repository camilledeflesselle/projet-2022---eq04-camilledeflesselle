package ca.ulaval.glo4002.cafe.infrastructure.rest.validators.menu;

import ca.ulaval.glo4002.cafe.application.menu.CoffeeFactory;
import ca.ulaval.glo4002.cafe.domain.bill.Amount;
import ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.MenuItemRepositoryInMemory;
import ca.ulaval.glo4002.cafe.ui.rest.DTO.MenuItemDTO;
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
    public void givenANameOfCoffeeAlreadyInMenu_whenValidate_thenRaiseException() {
        MenuItemDTO menuItemDTO = new MenuItemDTO(A_NAME_ALREADY_IN_MENU, SOME_CORRECT_INGREDIENTS, SOME_PRICE);
        assertThrows(BadRequestException.class, () -> menuItemAssembler.validate(menuItemDTO));
    }

    @Test
    public void givenANegativeCost_whenValidate_thenRaiseException() {
        MenuItemDTO menuItemDTO = new MenuItemDTO(SOME_NAME_NOT_IN_MENU, SOME_CORRECT_INGREDIENTS, -10);
        assertThrows(BadRequestException.class, () -> menuItemAssembler.validate(menuItemDTO));
    }

    @Test
    public void givenANewNameOfItem_whenValidate_thenDoNotRaiseException() {
        MenuItemDTO menuItemDTO = new MenuItemDTO(SOME_NAME_NOT_IN_MENU, SOME_CORRECT_INGREDIENTS, SOME_PRICE);
        assertDoesNotThrow(() -> menuItemAssembler.validate(menuItemDTO));
    }

    @Test
    public void givenAnIngredientWithNegativeQuantity_whenValidate_thenRaiseException() {
        MenuItemDTO menuItemDTO = new MenuItemDTO(SOME_NAME_NOT_IN_MENU, SOME_INCORRECT_WITH_NEGATIVE_QUANTITIES, SOME_PRICE);
        assertThrows(BadRequestException.class, () -> menuItemAssembler.validate(menuItemDTO));
    }

    @Test
    public void givenAnIngredientWithIncorrectName_whenValidate_thenRaiseException() {
        MenuItemDTO menuItemDTO = new MenuItemDTO(SOME_NAME_NOT_IN_MENU, SOME_INGREDIENTS_WITH_INCORRECT_NAMES, SOME_PRICE);
        assertThrows(BadRequestException.class, () -> menuItemAssembler.validate(menuItemDTO));
    }

    @Test
    public void givenAnIngredientWithoutBaseIngredient_whenValidate_thenRaiseException() {
        MenuItemDTO menuItemDTO = new MenuItemDTO(SOME_NAME_NOT_IN_MENU, SOME_INGREDIENTS_WITHOUT_BASE_INGREDIENT, SOME_PRICE);
        assertThrows(BadRequestException.class, () -> menuItemAssembler.validate(menuItemDTO));
    }

    @Test
    public void givenAValidMenuItemDTO_whenAssemble_thenReturnMenuItem() {
        MenuItemDTO menuItemDTO = new MenuItemDTO(SOME_NAME_NOT_IN_MENU, SOME_CORRECT_INGREDIENTS, SOME_PRICE);
        menuItemAssembler.validate(menuItemDTO);
        assertNotNull(menuItemAssembler.menuItemDTOToMenuItem(menuItemDTO));
    }

    @Test
    public void givenAValidMenuItemDTO_whenAssemble_thenMenuItemHasCorrectName() {
        MenuItemDTO menuItemDTO = new MenuItemDTO(SOME_NAME_NOT_IN_MENU, SOME_CORRECT_INGREDIENTS, SOME_PRICE);
        menuItemAssembler.validate(menuItemDTO);
        assertEquals(SOME_NAME_NOT_IN_MENU, menuItemAssembler.menuItemDTOToMenuItem(menuItemDTO).getName());
    }

    @Test
    public void givenAValidMenuItemDTO_whenAssemble_thenMenuItemHasCorrectPrice() {
        MenuItemDTO menuItemDTO = new MenuItemDTO(SOME_NAME_NOT_IN_MENU, SOME_CORRECT_INGREDIENTS, SOME_PRICE);
        menuItemAssembler.validate(menuItemDTO);
        assertEquals(new Amount(SOME_PRICE), menuItemAssembler.menuItemDTOToMenuItem(menuItemDTO).getPrice());
    }

}