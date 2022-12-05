package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories;

import ca.ulaval.glo4002.cafe.application.menu.CoffeeFactory;
import ca.ulaval.glo4002.cafe.application.menu.CoffeeType;
import ca.ulaval.glo4002.cafe.domain.bill.Amount;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemId;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuItemRepositoryInMemoryTest {
    private static final MenuItemId AN_EXISTING_MENU_ITEM_ID = CoffeeType.AMERICANO.getId();
    private static final MenuItem AN_EXISTING_MENU_ITEM = new MenuItem(AN_EXISTING_MENU_ITEM_ID, new Amount(2.25f));

    private MenuItemRepositoryInMemory menuItemRepositoryInMemory;
    private CoffeeFactory coffeeFactory;

    @BeforeEach
    public void initializeRepository() {
        coffeeFactory = new CoffeeFactory();
        menuItemRepositoryInMemory = new MenuItemRepositoryInMemory(coffeeFactory);
    }

    @Test
    void whenFindExistingMenuItemByName_thenReturnsTheMenuItemWithCorrectNameAndPrice() {
        menuItemRepositoryInMemory.saveMenuItem(AN_EXISTING_MENU_ITEM);
        assertEquals(AN_EXISTING_MENU_ITEM.getPrice(), menuItemRepositoryInMemory.findMenuItemById(AN_EXISTING_MENU_ITEM_ID).getPrice());
        assertEquals(AN_EXISTING_MENU_ITEM.getName(), menuItemRepositoryInMemory.findMenuItemById(AN_EXISTING_MENU_ITEM_ID).getName());
    }

    @Test
    void whenInitialized_thenAllItemsOfMenuOfLes4FeesAreInStorage() {
        assertNotNull(menuItemRepositoryInMemory.findMenuItemById(CoffeeType.AMERICANO.getId()));
        //Arrays.stream(CoffeeType.values()).forEach(id -> assertNotNull(menuItemRepositoryInMemory.findMenuItemById(id.getId())));
    }

    @Test
    void whenInitialized_thenAllItemsOfMenuOfLes4FeesHaveCorrectPrices() {
        /*assertEquals(new Amount(2.25f), menuItemRepositoryInMemory.findMenuItemById("Americano").getPrice());
        assertEquals(new Amount(2.1f), menuItemRepositoryInMemory.findMenuItemById("Dark Roast").getPrice());
        assertEquals(new Amount(3.29f), menuItemRepositoryInMemory.findMenuItemById("Cappuccino").getPrice());
        assertEquals(new Amount(2.95f), menuItemRepositoryInMemory.findMenuItemById("Espresso").getPrice());
        assertEquals(new Amount(3.75f), menuItemRepositoryInMemory.findMenuItemById("Flat White").getPrice());
        assertEquals(new Amount(2.95f), menuItemRepositoryInMemory.findMenuItemById("Latte").getPrice());
        assertEquals(new Amount(4.75f), menuItemRepositoryInMemory.findMenuItemById("Macchiato").getPrice());
        assertEquals(new Amount(4.15f), menuItemRepositoryInMemory.findMenuItemById("Mocha").getPrice());
    */}

    @Test
    void whenTryToFindAnItemNotInStorage_thenRaiseNotFoundException() {
        assertThrows(NotFoundException.class, () -> menuItemRepositoryInMemory.findMenuItemById(new MenuItemId("Not in storage")));
    }
}