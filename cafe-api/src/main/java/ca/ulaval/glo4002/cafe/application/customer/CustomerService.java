package ca.ulaval.glo4002.cafe.application.customer;

import ca.ulaval.glo4002.cafe.application.bill.BillService;
import ca.ulaval.glo4002.cafe.application.cooking.CookingService;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.customer.ICustomerRepository;
import ca.ulaval.glo4002.cafe.domain.order.*;
import jakarta.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerDoesNotExistsException;

public class CustomerService {
    private final BillService billService;
    private final CookingService cookingService;
    private final ICustomerRepository customerRepository;
    private final IOrderRepository orderRepository;
    private final OrdersFactory ordersFactory;

    public CustomerService(BillService billService, CookingService cookingService, ICustomerRepository customerRepository, IOrderRepository orderRepository, OrdersFactory ordersFactory) {
        this.billService = billService;
        this.cookingService = cookingService;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.ordersFactory = ordersFactory;
    }

    public Customer findCustomer(CustomerId customerId) {
        return this.customerRepository.findCustomerByCustomerId(customerId);
    }

    public void saveCustomer(Customer customer) {
        this.customerRepository.saveCustomer(customer);
    }

    public Order findOrCreateEmptyOrder(CustomerId customerId) {
        if (!this.hasOrders(customerId)) {
            Order order = this.ordersFactory.create(new ArrayList<>());
            this.orderRepository.saveOrdersByCustomerId(customerId, order);
            return order;
        }
        return this.orderRepository.findOrderByCustomerId(customerId);
    }

    public void updateOrdersOfCustomer(CustomerId customerId, List<String> menuItemsStrList) {
        List<MenuItem> menuItems = this.billService.buildMenuItemListFromStr(menuItemsStrList);
        Order newOrder = this.ordersFactory.create(menuItems);
        this.cookingService.cookOrder(newOrder);
        if (this.hasOrders(customerId)) {
            Order customerOrder = this.orderRepository.findOrderByCustomerId(customerId);
            customerOrder.appendMenuItemsFrom(newOrder);
            this.orderRepository.saveOrdersByCustomerId(customerId, customerOrder);
        } else {
            this.orderRepository.saveOrdersByCustomerId(customerId, newOrder);
        }
    }

    public void reset() {
        this.customerRepository.deleteAll();
        this.orderRepository.deleteAll();
    }

    public boolean hasOrders(CustomerId customerId) {
        try {
            this.orderRepository.findOrderByCustomerId(customerId);
            return true;
        } catch (NotFoundException e) {
            return false;
        }
    }

    public boolean hasAlreadyVisited(Customer customer) {
        try {
            this.customerRepository.findCustomerByCustomerId(customer.getId());
            return true;
        } catch (CustomerDoesNotExistsException e) {
            return false;
        }
    }
}
