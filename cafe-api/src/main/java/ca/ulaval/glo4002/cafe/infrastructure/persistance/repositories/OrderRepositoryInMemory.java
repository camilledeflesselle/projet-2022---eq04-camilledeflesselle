package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories;

import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.order.IOrderRepository;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import jakarta.ws.rs.NotFoundException;

import java.util.HashMap;

public class OrderRepositoryInMemory implements IOrderRepository {
    private final HashMap<CustomerId, Order> orders;

    public OrderRepositoryInMemory() {
        this.orders = new HashMap<>();
    }

    public void saveOrdersByCustomerId(CustomerId customerId, Order order) {
        this.orders.put(customerId, order);
    }

    public Order findOrderByCustomerId(CustomerId customerId) {
        if (!this.orders.containsKey(customerId)) {
            throw new NotFoundException();
        }
        return this.orders.get(customerId);
    }

    @Override
    public void deleteAll() {
        this.orders.clear();
    }
}
