package ca.ulaval.glo4002.cafe.domain.order;

import java.util.List;

public class OrdersFactory {
    public Order create(List<MenuItem> menuItems) {
        return new Order(menuItems);
    }
}
