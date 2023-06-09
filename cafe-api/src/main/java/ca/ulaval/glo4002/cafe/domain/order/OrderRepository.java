package ca.ulaval.glo4002.cafe.domain.order;

import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;

public interface OrderRepository {
    Order findOrderByCustomerId(CustomerId customerId);

    void deleteAll();

    void saveOrdersByCustomerId(CustomerId customerId, Order order);
}
