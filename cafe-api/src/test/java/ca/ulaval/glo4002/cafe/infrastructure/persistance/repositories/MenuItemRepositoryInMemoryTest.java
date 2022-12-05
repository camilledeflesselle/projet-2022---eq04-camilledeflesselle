package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories;

import ca.ulaval.glo4002.cafe.application.menu.CoffeeFactory;
import ca.ulaval.glo4002.cafe.domain.bill.Amount;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class MenuItemRepositoryInMemoryTest {
    private static final MenuItem AN_EXISTING_MENU_ITEM = new MenuItem("Americano", new Amount(2.25f));
    private static final String AN_EXISTING_MENU_ITEM_NAME = "Americano";

    private MenuItemRepositoryInMemory menuItemRepositoryInMemory;
    private CoffeeFactory coffeeFactory;

    @BeforeEach
    public void initializeRepository() {
        coffeeFactory = mock(CoffeeFactory.class);
        menuItemRepositoryInMemory = new MenuItemRepositoryInMemory(coffeeFactory);
    }

    @Test
    void whenCreated_initMenu() {
        verify(coffeeFactory).createCoffeesInLes4fees();
    }

    @Test
    void whenFindExistingMenuItemByName_thenReturnsTheMenuItemWithCorrectNameAndPrice() {
        assertEquals(AN_EXISTING_MENU_ITEM.getPrice(), menuItemRepositoryInMemory.findMenuItemById(AN_EXISTING_MENU_ITEM_NAME).getPrice());
        assertEquals(AN_EXISTING_MENU_ITEM.getName(), menuItemRepositoryInMemory.findMenuItemById(AN_EXISTING_MENU_ITEM_NAME).getName());
    }

    @Test
    void whenInitialized_thenAllItemsOfMenuOfLes4FeesAreInStorage() {
        assertNotNull(menuItemRepositoryInMemory.findMenuItemById("Americano"));
        assertNotNull(menuItemRepositoryInMemory.findMenuItemById("Dark Roast"));
        assertNotNull(menuItemRepositoryInMemory.findMenuItemById("Cappuccino"));
        assertNotNull(menuItemRepositoryInMemory.findMenuItemById("Espresso"));
        assertNotNull(menuItemRepositoryInMemory.findMenuItemById("Flat White"));
        assertNotNull(menuItemRepositoryInMemory.findMenuItemById("Latte"));
        assertNotNull(menuItemRepositoryInMemory.findMenuItemById("Macchiato"));
        assertNotNull(menuItemRepositoryInMemory.findMenuItemById("Mocha"));
    }

    @Test
    void whenInitialized_thenAllItemsOfMenuOfLes4FeesHaveCorrectPrices() {
        assertEquals(new Amount(2.25f), menuItemRepositoryInMemory.findMenuItemById("Americano").getPrice());
        assertEquals(new Amount(2.1f), menuItemRepositoryInMemory.findMenuItemById("Dark Roast").getPrice());
        assertEquals(new Amount(3.29f), menuItemRepositoryInMemory.findMenuItemById("Cappuccino").getPrice());
        assertEquals(new Amount(2.95f), menuItemRepositoryInMemory.findMenuItemById("Espresso").getPrice());
        assertEquals(new Amount(3.75f), menuItemRepositoryInMemory.findMenuItemById("Flat White").getPrice());
        assertEquals(new Amount(2.95f), menuItemRepositoryInMemory.findMenuItemById("Latte").getPrice());
        assertEquals(new Amount(4.75f), menuItemRepositoryInMemory.findMenuItemById("Macchiato").getPrice());
        assertEquals(new Amount(4.15f), menuItemRepositoryInMemory.findMenuItemById("Mocha").getPrice());
    }

    @Test
    void whenTryToFindAnItemNotInStorage_thenRaiseNotFoundException() {
        assertThrows(NotFoundException.class, () -> menuItemRepositoryInMemory.findMenuItemById("Wrong Name"));
    }
}