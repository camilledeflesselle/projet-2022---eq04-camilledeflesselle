package ca.ulaval.glo4002.cafe.domain.order;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderTest {
    private static final String AN_ITEM_NAME = "cafe";
    private static final String ANOTHER_ITEM_NAME = "big10";

    @Test
    public void whenAppendingItemsFromOrder_thenItemsAreAddedInTheOrderThatTheyAppearInTheOrder() {
        Order order = new Order(new ArrayList<>());
        MenuItem anItem = mock(MenuItem.class);
        MenuItem anotherItem = mock(MenuItem.class);
        List<MenuItem> menuItems = new ArrayList<>(List.of(anItem, anotherItem));

        Order newOrder = new Order(menuItems);
        order.appendMenuItemsFrom(newOrder);

        assertEquals(menuItems, order.getMenuItems());
    }

    @Test
    public void whenRequestingItemsStrFromOrder_thenReturnOrderedListOfMenuItemsStr() {
        MenuItem anItem = mock(MenuItem.class);
        MenuItem anotherItem = mock(MenuItem.class);
        when(anItem.getName()).thenReturn(AN_ITEM_NAME);
        when(anotherItem.getName()).thenReturn((ANOTHER_ITEM_NAME));
        List<MenuItem> menuItems = new ArrayList<>(List.of(anItem, anotherItem));
        Order order = new Order(menuItems);

        List<String> returnedList = order.getListOfOrderedItemsStr();
        List<String> expectedList = new ArrayList<>(List.of(AN_ITEM_NAME, ANOTHER_ITEM_NAME));

        assertEquals(expectedList, returnedList);
    }
}
