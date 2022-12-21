package ca.ulaval.glo4002.cafe.application.checkIn;

import ca.ulaval.glo4002.cafe.application.seating.SeatingService;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.DuplicateCustomerException;
import ca.ulaval.glo4002.cafe.domain.customer.ICustomerRepository;
import ca.ulaval.glo4002.cafe.domain.order.IOrderRepository;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.order.OrdersFactory;
import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CheckInServiceTest {
    ICustomerRepository customerRepository;
    SeatingService seatingService;
    CheckInService checkInService;
    Customer customer;
    Seat seat;
    private OrdersFactory ordersFactory;
    private IOrderRepository ordersRepository;

    @BeforeEach
    void setUp() {
        customerRepository = mock(ICustomerRepository.class);
        seatingService = mock(SeatingService.class);
        seat = mock(Seat.class);
        customer = mock(Customer.class);
        ordersFactory = mock(OrdersFactory.class);
        ordersRepository = mock(IOrderRepository.class);
        checkInService = new CheckInService(customerRepository, seatingService, ordersFactory, ordersRepository);
        when(seatingService.getSeatForCustomer(customer)).thenReturn(seat);
    }

    @Test
    public void whenCheckInANewCustomer_thenAssignASeat() {
        Customer newCustomer = mock(Customer.class);
        when(customerRepository.findCustomerByCustomerId(newCustomer.getId())).thenReturn(null);
        checkInService.checkIn(customer);

        verify(seat).assign();
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
    public void whenInitOrder_thenOrderIsCreatedAndSaved() {
        Order order = mock(Order.class);
        when(ordersFactory.create(any())).thenReturn(order);

        checkInService.initOrder(customer.getId());

        verify(ordersFactory).create(any());
        verify(ordersRepository).saveOrdersByCustomerId(customer.getId(), order);
    }
}