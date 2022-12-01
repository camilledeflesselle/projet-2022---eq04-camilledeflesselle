package ca.ulaval.glo4002.cafe.application.checkIn;

import ca.ulaval.glo4002.cafe.application.customer.CustomerService;
import ca.ulaval.glo4002.cafe.application.seating.SeatingService;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.DuplicateCustomerException;
import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CheckInServiceTest {
    CustomerService customerService;
    SeatingService seatingService;
    CheckInService checkInService;
    Customer customer;
    Seat seat;

    @BeforeEach
    void setUp() {
        customerService = mock(CustomerService.class);
        seatingService = mock(SeatingService.class);
        seat = mock(Seat.class);
        customer = mock(Customer.class);
        checkInService = new CheckInService(customerService, seatingService);
        when(seatingService.getSeatForCustomer(customer)).thenReturn(seat);
    }

    @Test
    public void whenCheckInANewCustomer_thenAssignASeat() {
        checkInService.checkIn(customer);

        verify(seat).assign();
    }

    @Test
    public void whenCheckInAnExistingCustomer_thenDuplicateCustomerException() {
        when(customerService.hasAlreadyVisited(customer)).thenReturn(true);

        assertThrows(DuplicateCustomerException.class, () -> checkInService.checkIn(customer)
        );
    }

    @Test
    public void whenCheckInANewCustomer_thenAttributeSeatIdToCustomer() {
        checkInService.checkIn(customer);

        verify(customer).setSeatId(seat.getId());
    }

    @Test
    public void whenCheckInANewCustomer_thenSaveCustomer() {
        checkInService.checkIn(customer);

        verify(customerService).saveCustomer(customer);
    }
}