package ca.ulaval.glo4002.cafe.application.checkIn;

import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerRepository;
import ca.ulaval.glo4002.cafe.domain.customer.DuplicateCustomerException;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.order.OrderRepository;
import ca.ulaval.glo4002.cafe.domain.order.OrdersFactory;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationRepository;
import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.seating.SeatingOrganizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CheckInServiceTest {
    private CustomerRepository customerRepository;
    private Customer customer;
    private Seat seat;
    private OrdersFactory ordersFactory;
    private OrderRepository ordersRepository;
    private ReservationRepository reservationRepository;
    private SeatingOrganizer seatingOrganizer;

    private CheckInService checkInService;

    @BeforeEach
    void setUp() {
        customerRepository = mock(CustomerRepository.class);
        seat = mock(Seat.class);
        customer = mock(Customer.class);
        ordersFactory = mock(OrdersFactory.class);
        ordersRepository = mock(OrderRepository.class);
        seatingOrganizer = mock(SeatingOrganizer.class);
        reservationRepository = mock(ReservationRepository.class);
        checkInService = new CheckInService(customerRepository, seatingOrganizer, ordersFactory, ordersRepository, reservationRepository);
        when(seatingOrganizer.findSeat(customer, reservationRepository)).thenReturn(seat);
    }

    @Test
    public void whenCheckInANewCustomer_thenAssignASeat() {
        Customer newCustomer = mock(Customer.class);
        when(customerRepository.findCustomerByCustomerId(newCustomer.getId())).thenReturn(null);
        checkInService.checkIn(customer);

        verify(seat).assign();
    }

    @Test
    public void givenReservationRepository_whenCheckInANewCustomer_thenSeatingOrganizerSearchASeat() {
        checkInService.checkIn(customer);

        verify(seatingOrganizer).findSeat(customer, reservationRepository);
    }


    @Test
    public void whenCheckInAnExistingCustomer_thenDuplicateCustomerException() {
        when(customerRepository.findCustomerByCustomerId(customer.getId())).thenReturn(customer);

        assertThrows(DuplicateCustomerException.class, () -> checkInService.checkIn(customer)
        );
    }

    @Test
    public void whenCheckInANewCustomer_thenAttributeSeatIdToCustomer() {
        checkInService.checkIn(customer);

        verify(customer).setSeatId(seat.getId());
    }

    @Test
    public void whenCheckInANewCustomer_thenSaveCustomerInStorage() {
        checkInService.checkIn(customer);

        verify(customerRepository).saveCustomer(customer);
    }

    @Test
    public void whenCheckInANewCustomer_thenCreateEmptyOrderAndSavedThisOrder() {
        Order order = mock(Order.class);
        when(ordersFactory.create(any())).thenReturn(order);

        checkInService.checkIn(customer);

        verify(ordersFactory).create(any());
        verify(ordersRepository).saveOrdersByCustomerId(customer.getId(), order);
    }
}