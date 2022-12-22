package ca.ulaval.glo4002.cafe.domain.customer;

import java.util.List;

public interface CustomerRepository {
    Customer findCustomerByCustomerId(CustomerId customerId);

    void saveCustomer(Customer customer);

    int getAmount();

    List<Customer> findAll();

    void deleteAll();

    void deleteCustomerByCustomerId(CustomerId customerId);
}
