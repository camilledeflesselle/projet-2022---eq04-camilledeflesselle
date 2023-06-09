package ca.ulaval.glo4002.cafe.domain.order;

import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemRepository;
import ca.ulaval.glo4002.cafe.ui.rest.assemblers.config.InvalidMenuOrderException;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class OrdersFactoryTest {

    private MenuItemRepository menuItemRepository;
    private static final String AN_ITEM_NAME = "Café";
    private static final String ANOTHER_ITEM_NAME = "Big10";
    private OrdersFactory ordersFactory;

    @BeforeEach
    public void setup() {
        menuItemRepository = mock(MenuItemRepository.class);
        ordersFactory = new OrdersFactory();
    }
    @Test
    public void whenCreateOrder_thenReturnsOrder() {
        List<MenuItem> menuItems = new ArrayList<>();
        assertEquals(Order.class, ordersFactory.create(menuItems).getClass());
    }

    @Test
    public void givenMenuItemNamesWithOneThatIsNotInRepository_whenBuildingMenuItemsList_thenThrowsInvalidMenuOrderException() {
        List<String> menuItemsStr = new ArrayList<>(List.of(AN_ITEM_NAME, ANOTHER_ITEM_NAME));
        when(menuItemRepository.findMenuItemByName(ANOTHER_ITEM_NAME)).thenThrow(NotFoundException.class);

        assertThrows(
                InvalidMenuOrderException.class,
                () -> ordersFactory.buildMenuItemListFromStr(menuItemsStr, menuItemRepository)
        );
    }

    @Test
    public void givenMenuItemNamesThatExistInRepository_whenBuildingMenuItemsList_thenEachIsSearchedInRepository() {
        List<String> menuItemsStr = new ArrayList<>(List.of(AN_ITEM_NAME, ANOTHER_ITEM_NAME));
        when(menuItemRepository.findMenuItemByName(AN_ITEM_NAME)).thenReturn(mock(MenuItem.class));
        when(menuItemRepository.findMenuItemByName(ANOTHER_ITEM_NAME)).thenReturn(mock(MenuItem.class));

        ordersFactory.buildMenuItemListFromStr(menuItemsStr, menuItemRepository);

        verify(menuItemRepository).findMenuItemByName(AN_ITEM_NAME);
        verify(menuItemRepository).findMenuItemByName(ANOTHER_ITEM_NAME);
    }
}