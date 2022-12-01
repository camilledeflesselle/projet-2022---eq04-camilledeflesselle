package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories;

import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerDoesNotExistsException;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.customer.ICustomerRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerRepositoryInMemory implements ICustomerRepository {
    private final Map<CustomerId, Customer> customers;

    public CustomerRepositoryInMemory() {
        this.customers = new HashMap<>();
    }

    public Customer findCustomerByCustomerId(CustomerId customerId) {
        if (!this.customers.containsKey(customerId)) {
            throw new CustomerDoesNotExistsException();
        }
        return this.customers.get(customerId);
    }

    public void saveCustomer(Customer customer) {
        this.customers.put(customer.getId(), customer);
    }

    public int getAmount() {
        return this.customers.size();
    }

    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(customers.values());
    }

    public void deleteCustomerByCustomerId(CustomerId customerId) {
        this.customers.remove(customerId);
    }

    public void deleteAll() {
        this.customers.clear();
    }
}
