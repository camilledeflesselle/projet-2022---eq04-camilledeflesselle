package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories;

import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.order.OrderRepository;

import java.util.HashMap;

public class OrderRepositoryInMemory implements OrderRepository {
    private final HashMap<CustomerId, Order> orders;

    public OrderRepositoryInMemory() {
        this.orders = new HashMap<>();
    }

    public void saveOrdersByCustomerId(CustomerId customerId, Order order) {
        this.orders.put(customerId, order);
    }

    public Order findOrderByCustomerId(CustomerId customerId) {
        return this.orders.get(customerId);
    }

    @Override
    public void deleteAll() {
        this.orders.clear();
    }
}
