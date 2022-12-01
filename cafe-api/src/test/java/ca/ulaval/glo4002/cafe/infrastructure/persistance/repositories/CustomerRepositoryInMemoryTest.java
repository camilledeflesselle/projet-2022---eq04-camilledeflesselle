package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories;

import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerDoesNotExistsException;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomerRepositoryInMemoryTest {
    private static final String A_CUSTOMER_NAME = "Keanu Reeves";
    private static final CustomerId A_CUSTOMER_ID = new CustomerId("1");
    private static final CustomerId ANOTHER_CUSTOMER_ID = new CustomerId("2");
    private static final Customer A_CUSTOMER = new Customer(A_CUSTOMER_ID, A_CUSTOMER_NAME);
    private static final Customer ANOTHER_CUSTOMER = new Customer(ANOTHER_CUSTOMER_ID, A_CUSTOMER_NAME);

    private CustomerRepositoryInMemory customerRepositoryInMemory;

    @BeforeEach
    public void setup() {
        customerRepositoryInMemory = new CustomerRepositoryInMemory();
    }

    @Test
    public void whenInitialized_thenIsEmpty() {
        assertEquals(0, customerRepositoryInMemory.getAmount());
    }

    @Test
    public void givenEmptyCustomerRepository_whenSavingACustomer_thenRepositoryHasOneElement() {
        customerRepositoryInMemory.saveCustomer(A_CUSTOMER);

        assertEquals(1, customerRepositoryInMemory.getAmount());
    }

    @Test
    public void givenCustomerThatExistsInRepository_whenSearchingForCustomerWithId_thenReturnsCustomer() {
        customerRepositoryInMemory.saveCustomer(A_CUSTOMER);

        Customer returnedCustomer = customerRepositoryInMemory.findCustomerByCustomerId(A_CUSTOMER.getId());

        assertEquals(A_CUSTOMER, returnedCustomer);
    }

    @Test
    public void givenCustomerThatDoesntExistInRepository_whenSearchingForCustomer_thenNoCustomerIsFound() {
        assertThrows(CustomerDoesNotExistsException.class, () -> {
                    customerRepositoryInMemory.findCustomerByCustomerId(A_CUSTOMER_ID);
                }
        );
    }

    @Test
    public void givenCustomerRepositoryWithMoreThanOneCustomer_whenSearchingAllCustomers_thenReturnsAllCustomer() {
        customerRepositoryInMemory.saveCustomer(A_CUSTOMER);
        customerRepositoryInMemory.saveCustomer(ANOTHER_CUSTOMER);
        List<Customer> expectedCustomers = new ArrayList<>(List.of(A_CUSTOMER, ANOTHER_CUSTOMER));

        List<Customer> returnedCustomers = customerRepositoryInMemory.findAll();

        assertEquals(expectedCustomers, returnedCustomers);
    }

    @Test
    public void givenNonEmptyCustomerRepository_whenDeletingACustomer_thenCustomerIsDeletedFromRepository() {
        customerRepositoryInMemory.saveCustomer(A_CUSTOMER);
        customerRepositoryInMemory.saveCustomer(ANOTHER_CUSTOMER);

        customerRepositoryInMemory.deleteCustomerByCustomerId(A_CUSTOMER.getId());

        assertEquals(1, customerRepositoryInMemory.getAmount());
        assertThrows(CustomerDoesNotExistsException.class, () -> {
                    customerRepositoryInMemory.findCustomerByCustomerId(A_CUSTOMER_ID);
                }
        );
    }

    @Test
    public void givenNonEmptyCustomerRepository_whenDeletingAllCustomers_thenRepositoryBecomeEmpty() {
        customerRepositoryInMemory.saveCustomer(A_CUSTOMER);
        customerRepositoryInMemory.saveCustomer(ANOTHER_CUSTOMER);

        customerRepositoryInMemory.deleteAll();

        assertEquals(0, customerRepositoryInMemory.getAmount());
    }
}
