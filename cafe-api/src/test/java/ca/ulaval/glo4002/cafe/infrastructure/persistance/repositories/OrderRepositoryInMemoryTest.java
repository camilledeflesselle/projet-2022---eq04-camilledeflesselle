package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories;

import ca.ulaval.glo4002.cafe.domain.bill.Amount;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemId;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.order.OrdersFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class OrderRepositoryInMemoryTest {
    private static final CustomerId A_CUSTOMER_ID = new CustomerId("1");
    private static final OrdersFactory ordersFactory = new OrdersFactory();
    private static final MenuItem MENU_ITEM_1 = new MenuItem(new MenuItemId("cheese"), new Amount(13.25f));
    private static final Order FIRST_ORDER = ordersFactory.create(List.of(MENU_ITEM_1));

    private OrderRepositoryInMemory ordersRepositoryInMemory;

    @BeforeEach
    public void setup() {
        ordersRepositoryInMemory = new OrderRepositoryInMemory();
    }

    @Test
    public void whenSaveAnOrderOfNewCustomer_thenStorageContainsThisOrder() {
        ordersRepositoryInMemory.saveOrdersByCustomerId(A_CUSTOMER_ID, FIRST_ORDER);
        assertFalse(ordersRepositoryInMemory.findOrderByCustomerId(A_CUSTOMER_ID).getMenuItems().isEmpty());
    }

    @Test
    public void whenFindAnExistingOrder_thenThisOrderIsReturned() {
        ordersRepositoryInMemory.saveOrdersByCustomerId(A_CUSTOMER_ID, FIRST_ORDER);
        assertEquals(FIRST_ORDER, ordersRepositoryInMemory.findOrderByCustomerId(A_CUSTOMER_ID));
    }
}