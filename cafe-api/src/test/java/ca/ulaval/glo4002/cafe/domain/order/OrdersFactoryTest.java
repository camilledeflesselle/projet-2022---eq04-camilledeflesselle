package ca.ulaval.glo4002.cafe.domain.order;

import ca.ulaval.glo4002.cafe.domain.menu.IMenuItemRepository;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemId;
import ca.ulaval.glo4002.cafe.infrastructure.rest.validators.config.InvalidMenuOrderException;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrdersFactoryTest {

    private IMenuItemRepository menuItemRepository;
    private static final MenuItemId AN_ITEM_NAME = new MenuItemId("Café");
    private static final MenuItemId ANOTHER_ITEM_NAME = new MenuItemId("Big10");
    private OrdersFactory ordersFactory;

    @BeforeEach
    public void setup() {
        menuItemRepository = mock(IMenuItemRepository.class);
        ordersFactory = new OrdersFactory();
    }
    @Test
    public void whenCreateOrder_thenReturnsOrder() {
        List<MenuItem> menuItems = new ArrayList<>();
        assertEquals(Order.class, ordersFactory.create(menuItems).getClass());
    }

    @Test
    public void givenMenuItemNamesWithOneThatIsNotInRepository_whenBuildingMenuItemsList_thenThrowsInvalidMenuOrderException() {
        List<String> menuItemsStr = new ArrayList<>(List.of(AN_ITEM_NAME.getName(), ANOTHER_ITEM_NAME.getName()));
        when(menuItemRepository.findMenuItemById(ANOTHER_ITEM_NAME)).thenThrow(NotFoundException.class);

        assertThrows(
                InvalidMenuOrderException.class,
                () -> ordersFactory.buildMenuItemListFromStr(menuItemsStr, menuItemRepository)
        );
    }

    @Test
    public void givenMenuItemNamesThatExistInRepository_whenBuildingMenuItemsList_thenEachIsSearchedInRepository() {
        List<String> menuItemsStr = new ArrayList<>(List.of(AN_ITEM_NAME.getName(), ANOTHER_ITEM_NAME.getName()));
        when(menuItemRepository.findMenuItemById(AN_ITEM_NAME)).thenReturn(mock(MenuItem.class));
        when(menuItemRepository.findMenuItemById(ANOTHER_ITEM_NAME)).thenReturn(mock(MenuItem.class));

        ordersFactory.buildMenuItemListFromStr(menuItemsStr, menuItemRepository);

        verify(menuItemRepository).findMenuItemById(AN_ITEM_NAME);
        verify(menuItemRepository).findMenuItemById(ANOTHER_ITEM_NAME);
    }
}