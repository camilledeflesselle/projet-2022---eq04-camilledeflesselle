package ca.ulaval.glo4002.cafe.application.customer;

import ca.ulaval.glo4002.cafe.application.bill.BillService;
import ca.ulaval.glo4002.cafe.application.cooking.CookingService;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerDoesNotExistsException;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.customer.ICustomerRepository;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.order.IOrderRepository;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.order.OrdersFactory;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private final BillService billService;
    private final CookingService cookingService;
    private final ICustomerRepository customerRepository;
    private final OrdersFactory ordersFactory;

    public CustomerService(BillService billService, CookingService cookingService, ICustomerRepository customerRepository, OrdersFactory ordersFactory) {
        this.billService = billService;
        this.cookingService = cookingService;
        this.customerRepository = customerRepository;
        this.ordersFactory = ordersFactory;
    }

    public Customer findCustomer(CustomerId customerId) {
        return this.customerRepository.findCustomerByCustomerId(customerId);
    }


    public void saveCustomer(Customer customer) {
        this.initOrder(customer);
        this.customerRepository.saveCustomer(customer);
    }

    private void initOrder(Customer customer) {
        Order order = this.ordersFactory.create(new ArrayList<>());
        customer.setOrder(order);
    }

    public Order findOrCreateEmptyOrder(CustomerId customerId) {
        Customer customer = this.findCustomer(customerId);
        return customer.getOrder();
    }

    public void updateOrdersOfCustomer(CustomerId customerId, List<String> menuItemsStrList) {
        List<MenuItem> menuItems = this.billService.buildMenuItemListFromStr(menuItemsStrList);
        Order newOrder = this.ordersFactory.create(menuItems);
        this.cookingService.cookOrder(newOrder);
        Customer customer = this.findCustomer(customerId);
        customer.updateOrder(newOrder);
        this.customerRepository.saveCustomer(customer);
    }

    public void reset() {
        this.customerRepository.deleteAll();
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
