package ca.ulaval.glo4002.cafe.domain.order;

import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;

import java.util.List;

public class OrdersFactory {
    public Order create(List<MenuItem> menuItems) {
        return new Order(menuItems);
    }
}
