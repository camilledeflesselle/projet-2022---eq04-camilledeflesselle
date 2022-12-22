package ca.ulaval.glo4002.cafe.application.checkOut;

import ca.ulaval.glo4002.cafe.domain.bill.BillFactory;
import ca.ulaval.glo4002.cafe.domain.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.bill.IBillRepository;
import ca.ulaval.glo4002.cafe.domain.bill.TipRate;
import ca.ulaval.glo4002.cafe.domain.config.Config;
import ca.ulaval.glo4002.cafe.domain.config.IConfigRepository;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerDoesNotExistException;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.customer.ICustomerRepository;
import ca.ulaval.glo4002.cafe.domain.order.IOrderRepository;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.reservation.IReservationRepository;
import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import ca.ulaval.glo4002.cafe.domain.seating.SeatingOrganizer;
import ca.ulaval.glo4002.cafe.domain.tax.TaxRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CheckOutServiceTest {

    private static final TaxRate A_TAX_RATE = new TaxRate(0.15f);
    private static final CustomerId A_CUSTOMER_ID = new CustomerId("id");
    private static final SeatId A_SEAT_ID = new SeatId(1);
    private static final TipRate A_GROUP_TIP_RATE = new TipRate(0.15f);
    private static final boolean CUSTOMER_HAS_GROUP = true;

    private static CheckOutService checkOutService;
    private static ICustomerRepository customerRepository;
    private static IOrderRepository orderRepository;
    private BillFactory billFactory;
    private IBillRepository billRepository;
    private SeatingOrganizer seatingOrganizer;
    private IReservationRepository reservationRepository;

    @BeforeEach
    public void setup() {
        customerRepository = mock(ICustomerRepository.class);
        orderRepository = mock(IOrderRepository.class);
        IConfigRepository configRepository = mock(IConfigRepository.class);
        billFactory = mock(BillFactory.class);
        billRepository = mock(IBillRepository.class);
        seatingOrganizer = mock(SeatingOrganizer.class);
        reservationRepository = mock(IReservationRepository.class);
        Config config = mock(Config.class);
        when(configRepository.findConfig()).thenReturn(config);
        when(config.getTaxRate()).thenReturn(A_TAX_RATE);
        when(config.getGroupTipRate()).thenReturn(A_GROUP_TIP_RATE);
        checkOutService = new CheckOutService(customerRepository, orderRepository, configRepository, billFactory, billRepository, seatingOrganizer, reservationRepository);
    }

    @Test
    public void whenCheckingOutNotExistingCustomer_thenRaiseException() {
        when(customerRepository.findCustomerByCustomerId(A_CUSTOMER_ID)).thenReturn(null);
        assertThrows(CustomerDoesNotExistException.class, () -> checkOutService.checkoutCustomer(A_CUSTOMER_ID));
    }


    @Test
    public void whenCustomerCheckOut_thenSearchForCustomerByCustomerId() {
        givenCustomerWithoutGroup();
        givenSeat(A_SEAT_ID);

        checkOutService.checkoutCustomer(A_CUSTOMER_ID);

        verify(customerRepository).findCustomerByCustomerId(A_CUSTOMER_ID);
    }

    @Test
    public void whenCustomerCheckOut_thenSearchForOrderOfCustomer() {
        givenCustomerWithoutGroup();
        givenSeat(A_SEAT_ID);

        checkOutService.checkoutCustomer(A_CUSTOMER_ID);

        verify(orderRepository).findOrderByCustomerId(A_CUSTOMER_ID);
    }

    @Test
    public void whenProcessingBillForCustomer_thenSaveCreatedBillInRepository() {
        givenCustomerWithoutGroup();
        Order order = givenCustomerOrder();
        Bill bill = mock(Bill.class);
        when(billFactory.createBill(order, A_TAX_RATE, A_GROUP_TIP_RATE, !CUSTOMER_HAS_GROUP)).thenReturn(bill);

        checkOutService.checkoutCustomer(A_CUSTOMER_ID);

        verify(billFactory).createBill(order, A_TAX_RATE, A_GROUP_TIP_RATE, !CUSTOMER_HAS_GROUP);
        verify(billRepository).saveBillByCustomerId(A_CUSTOMER_ID, bill);
    }

    @Test
    public void whenCustomerCheckOut_thenRemoveCustomerFromSeat() {
        Customer customer = givenCustomerWithoutGroup();
        givenSeat(A_SEAT_ID);

        checkOutService.checkoutCustomer(A_CUSTOMER_ID);

        verify(seatingOrganizer).removeCustomerFromSeating(customer, reservationRepository);
    }

    private Customer givenCustomerWithoutGroup() {
        Customer customer = mock(Customer.class);
        when(customerRepository.findCustomerByCustomerId(A_CUSTOMER_ID)).thenReturn(customer);
        when(customer.hasGroup()).thenReturn(false);
        when(customer.getSeatId()).thenReturn(A_SEAT_ID);
        when(customer.getGroupName()).thenReturn(null);
        when(customer.getId()).thenReturn(A_CUSTOMER_ID);

        return customer;
    }

    private void givenSeat(SeatId seatId) {
        Seat seat = mock(Seat.class);
        when(seat.getId()).thenReturn(seatId);
        when(seatingOrganizer.findSeat(any(), any())).thenReturn(seat);
    }

    private Order givenCustomerOrder() {
        Order order = mock(Order.class);
        when(orderRepository.findOrderByCustomerId(A_CUSTOMER_ID)).thenReturn(order);
        return order;
    }

}
