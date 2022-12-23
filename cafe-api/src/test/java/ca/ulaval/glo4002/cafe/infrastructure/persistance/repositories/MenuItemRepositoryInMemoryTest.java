package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories;

import ca.ulaval.glo4002.cafe.application.menu.CoffeeType;
import ca.ulaval.glo4002.cafe.domain.bill.Amount;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemId;
import ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.menu.CoffeeFactory;
import ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.menu.MenuItemRepositoryInMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MenuItemRepositoryInMemoryTest {
    private static final String AN_EXISTING_MENU_ITEM_NAME = CoffeeType.Americano.getId().getName();
    private static final MenuItem AN_EXISTING_MENU_ITEM = new MenuItem(CoffeeType.Americano.getId(), new Amount(2.25f));

    private static final boolean IS_CUSTOM = true;
    private static final boolean IS_NOT_CUSTOM = false;
    private MenuItemRepositoryInMemory menuItemRepositoryInMemory;

    @BeforeEach
    public void initializeRepository() {
        CoffeeFactory coffeeFactory = new CoffeeFactory();
        menuItemRepositoryInMemory = new MenuItemRepositoryInMemory(coffeeFactory);
    }

    @Test
    public void whenFindExistingMenuItemByName_thenReturnsTheMenuItemWithCorrectNameAndPrice() {
        assertEquals(AN_EXISTING_MENU_ITEM, menuItemRepositoryInMemory.findMenuItemByName(AN_EXISTING_MENU_ITEM_NAME));
    }

    @Test
    public void whenInitialized_thenAllItemsOfMenuOfLes4FeesAreInStorage() {
        Arrays.stream(CoffeeType.values()).forEach(id -> assertNotNull(menuItemRepositoryInMemory.findMenuItemByName(id.getId().getName())));
    }

    @Test
    public void whenInitialized_thenAllItemsOfMenuOfLes4FeesHaveCorrectPrices() {
        assertEquals(new Amount(2.25f), menuItemRepositoryInMemory.findMenuItemByName("Americano").getPrice());
        assertEquals(new Amount(2.1f), menuItemRepositoryInMemory.findMenuItemByName("Dark Roast").getPrice());
        assertEquals(new Amount(3.29f), menuItemRepositoryInMemory.findMenuItemByName("Cappuccino").getPrice());
        assertEquals(new Amount(2.95f), menuItemRepositoryInMemory.findMenuItemByName("Espresso").getPrice());
        assertEquals(new Amount(3.75f), menuItemRepositoryInMemory.findMenuItemByName("Flat White").getPrice());
        assertEquals(new Amount(2.95f), menuItemRepositoryInMemory.findMenuItemByName("Latte").getPrice());
        assertEquals(new Amount(4.75f), menuItemRepositoryInMemory.findMenuItemByName("Macchiato").getPrice());
        assertEquals(new Amount(4.15f), menuItemRepositoryInMemory.findMenuItemByName("Mocha").getPrice());
      }

    @Test
    public void whenTryToFindAnItemNotInStorage_thenReturnsNull() {
        assertNull(menuItemRepositoryInMemory.findMenuItemByName("Not in storage"));
    }

    @Test
    public void whenAddCustomMenuItem_thenMenuItemIsAddedToStorage() {
        MenuItem customMenuItem = new MenuItem(new MenuItemId("Custom", IS_CUSTOM), new Amount(2.25f));
        menuItemRepositoryInMemory.save(customMenuItem);
        assertEquals(customMenuItem, menuItemRepositoryInMemory.findMenuItemByName("Custom"));
    }

    @Test
    public void givenACustomMenuItemInStorage_whenDeleteAllCustomMenuItems_thenMenuItemIsDeletedFromStorage() {
        MenuItem customMenuItem = new MenuItem(new MenuItemId("Custom", IS_CUSTOM), new Amount(2.25f));
        menuItemRepositoryInMemory.save(customMenuItem);
        menuItemRepositoryInMemory.deleteAllCustom();
        assertNull(menuItemRepositoryInMemory.findMenuItemByName("Custom"));
    }

    @Test
    public void whenDeleteAllCustomMenuItems_thenNonCustomMenuItemIsNotDeletedFromStorage() {
        MenuItem customMenuItem = new MenuItem(new MenuItemId("Not Custom", IS_NOT_CUSTOM), new Amount(2.25f));
        menuItemRepositoryInMemory.save(customMenuItem);
        menuItemRepositoryInMemory.deleteAllCustom();
        assertNotNull(menuItemRepositoryInMemory.findMenuItemByName("Not Custom"));
    }
}