package ca.ulaval.glo4002.cafe.application.customer;

import ca.ulaval.glo4002.cafe.application.cooking.CookingService;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerDoesNotExistsException;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.customer.ICustomerRepository;
import ca.ulaval.glo4002.cafe.domain.menu.IMenuItemRepository;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.order.IOrderRepository;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.order.OrdersFactory;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private final CookingService cookingService;
    private final ICustomerRepository customerRepository;
    private final OrdersFactory ordersFactory;
    private final IMenuItemRepository menuItemRepository;
    private final IOrderRepository ordersRepository;

    public CustomerService(CookingService cookingService, ICustomerRepository customerRepository,
                           OrdersFactory ordersFactory, IMenuItemRepository menuItemRepository, IOrderRepository ordersRepository) {
        this.cookingService = cookingService;
        this.customerRepository = customerRepository;
        this.ordersFactory = ordersFactory;
        this.menuItemRepository = menuItemRepository;
        this.ordersRepository = ordersRepository;
    }

    public Customer findCustomer(CustomerId customerId) {
        Customer customer = this.customerRepository.findCustomerByCustomerId(customerId);
        if (customer == null) {
            throw new CustomerDoesNotExistsException();
        }
        return customer;
    }

    public Order findOrder(CustomerId customerId) {
        Order order = this.ordersRepository.findOrderByCustomerId(customerId);
        if (order == null) {
            throw new NotFoundException();
        }
        return order;
    }

    public void updateOrdersOfCustomer(CustomerId customerId, List<String> menuItemsStrList) {
        List<MenuItem> menuItems = this.ordersFactory.buildMenuItemListFromStr(menuItemsStrList, this.menuItemRepository);
        Order newOrder = this.ordersFactory.create(menuItems);
        this.cookingService.cookOrder(newOrder);
        Order order = this.findOrder(customerId);
        Order concatenateOrder = order.appendMenuItemsFrom(newOrder);
        this.ordersRepository.saveOrdersByCustomerId(customerId, concatenateOrder);
    }
}
