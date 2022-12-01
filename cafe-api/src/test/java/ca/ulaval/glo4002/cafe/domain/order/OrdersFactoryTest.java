package ca.ulaval.glo4002.cafe.domain.order;

import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrdersFactoryTest {
    @Test
    public void whenCreateOrder_thenReturnsOrder() {
        List<MenuItem> menuItems = new ArrayList<>();
        OrdersFactory ordersFactory = new OrdersFactory();
        assertEquals(Order.class, ordersFactory.create(menuItems).getClass());
    }
}