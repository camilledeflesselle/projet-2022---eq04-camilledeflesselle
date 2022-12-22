package ca.ulaval.glo4002.cafe.application.customer;

import ca.ulaval.glo4002.cafe.application.cooking.CookingService;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerDoesNotExistException;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerRepository;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemRepository;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.order.OrderRepository;
import ca.ulaval.glo4002.cafe.domain.order.OrdersFactory;

import java.util.List;

public class CustomerOrderService {
    private final CookingService cookingService;
    private final CustomerRepository customerRepository;
    private final OrdersFactory ordersFactory;
    private final MenuItemRepository menuItemRepository;
    private final OrderRepository ordersRepository;

    public CustomerOrderService(CookingService cookingService, CustomerRepository customerRepository,
                                OrdersFactory ordersFactory, MenuItemRepository menuItemRepository, OrderRepository ordersRepository) {
        this.cookingService = cookingService;
        this.customerRepository = customerRepository;
        this.ordersFactory = ordersFactory;
        this.menuItemRepository = menuItemRepository;
        this.ordersRepository = ordersRepository;
    }

    public Customer findCustomer(CustomerId customerId) {
        Customer customer = this.customerRepository.findCustomerByCustomerId(customerId);
        if (customer == null) {
            throw new CustomerDoesNotExistException();
        }
        return customer;
    }

    public Order findOrder(CustomerId customerId) {
        return this.ordersRepository.findOrderByCustomerId(customerId);
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
