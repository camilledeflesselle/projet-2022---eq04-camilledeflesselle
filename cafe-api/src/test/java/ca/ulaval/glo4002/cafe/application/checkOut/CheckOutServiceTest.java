package ca.ulaval.glo4002.cafe.application.checkOut;

import ca.ulaval.glo4002.cafe.application.bill.BillFactory;
import ca.ulaval.glo4002.cafe.application.reservation.ReservationService;
import ca.ulaval.glo4002.cafe.domain.bill.Amount;
import ca.ulaval.glo4002.cafe.domain.bill.IBillRepository;
import ca.ulaval.glo4002.cafe.domain.config.IConfigRepository;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.customer.ICustomerRepository;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemId;
import ca.ulaval.glo4002.cafe.domain.order.IOrderRepository;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.reservation.IReservationRepository;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import ca.ulaval.glo4002.cafe.domain.seating.SeatingOrganizer;
import ca.ulaval.glo4002.cafe.domain.tax.TaxRate;
import org.junit.jupiter.api.BeforeEach;

import java.util.Arrays;

import static org.mockito.Mockito.*;

public class CheckOutServiceTest {
    private static final CustomerId A_CUSTOMER_ID = new CustomerId("id");
    private static final SeatId A_SEAT_ID = new SeatId(1);
    private static final SeatId ANOTHER_SEAT_ID = new SeatId(2);
    private static final String A_GROUP_NAME = "group";
    private static final Order SOME_CUSTOMER_ORDER = new Order(Arrays.asList(new MenuItem(new MenuItemId("Chocolate"), new Amount(13.25f)), new MenuItem(new MenuItemId("Coca"), new Amount(12.5f))));
    private static final TaxRate A_TAX_RATE = new TaxRate(0.15f);

    private static CheckOutService checkOutService;
    private static ICustomerRepository customerRepository;
    private static ReservationService reservationService;
    private static IOrderRepository orderRepository;
    private IConfigRepository configRepository;
    private BillFactory billFactory;
    private IBillRepository billRepository;
    private SeatingOrganizer seatingOrganizer;
    private IReservationRepository reservationRepository;

    @BeforeEach
    public void setup() {
        customerRepository = mock(ICustomerRepository.class);
        orderRepository = mock(IOrderRepository.class);
        reservationService = mock(ReservationService.class);
        configRepository = mock(IConfigRepository.class);
        billFactory = mock(BillFactory.class);
        billRepository = mock(IBillRepository.class);
        seatingOrganizer = mock(SeatingOrganizer.class);
        reservationRepository = mock(IReservationRepository.class);
        checkOutService = new CheckOutService(customerRepository, orderRepository, configRepository, billFactory, billRepository, seatingOrganizer, reservationRepository);
    }
    /*
    @Test
    public void whenCustomerCheckOut_thenSearchForCustomerByCustomerId() {
        givenCustomerWithoutGroup();
        givenSeat(A_SEAT_ID);

        checkOutService.checkoutCustomer(A_CUSTOMER_ID);

        verify(customerRepository).findCustomerByCustomerId(A_CUSTOMER_ID);
    }

    @Test
    public void givenCustomerWithoutGroup_whenCheckOut_thenDoesNotSearchForReservation() {
        givenCustomerWithoutGroup();
        givenSeat(A_SEAT_ID);

        checkOutService.checkoutCustomer(A_CUSTOMER_ID);

        verify(seatingService, never()).getReservationByGroupName(anyString());
    }

    @Test
    public void givenCustomerWithGroup_whenCheckOut_thenSearchForReservationWithGroupNameAndCheckoutOfReservation() {
        Customer customer = givenCustomerWithGroup();
        Reservation reservation = givenReservation();
        givenSeat(A_SEAT_ID);

        checkOutService.checkoutCustomer(A_CUSTOMER_ID);

        verify(seatingService, times(2)).getReservationByGroupName(A_GROUP_NAME);
        verify(reservation).checkoutCustomer(customer);
    }

    @Test
    public void whenCustomerCheckOut_thenCustomerIsUnassignedFromSeat() {
        Customer customer = givenCustomerWithoutGroup();
        Seat seat = givenSeat(A_SEAT_ID);

        checkOutService.checkoutCustomer(A_CUSTOMER_ID);

        verify(seatingService).getSeatById(A_SEAT_ID);
        verify(seat).unassign();
        verify(customer).unsetSeatId();
    }

    @Test
    public void givenCustomerWithoutGroup_whenCheckOut_thenCreateBillForCustomerAndDoesNotRemoveReservation() {
        givenCustomerWithoutGroup();
        givenSeat(A_SEAT_ID);
        Order order = givenCustomerOrder();

        checkOutService.checkoutCustomer(A_CUSTOMER_ID);

        verify(checkOutService).processBill(A_CUSTOMER_ID, order);
        verify(seatingService, never()).getReservationByGroupName(any());
        verify(seatingService, never()).removeReservationByGroupName(any());
    }

    @Test
    public void givenCustomerWithGroup_whenCheckOut_thenCreateBillForGroup() {
        givenCustomerWithGroup();
        givenSeat(A_SEAT_ID);
        givenReservation();
        Order order = givenCustomerOrder();

        checkOutService.checkoutCustomer(A_CUSTOMER_ID);

        verify(checkOutService).processBill(, order);
    }

    @Test
    public void givenCustomerWithGroup_whenCheckOutAndReservationNotEmpty_thenReservationIsNotRemoved() {
        givenCustomerWithGroup();
        givenSeat(A_SEAT_ID);
        Reservation reservation = givenReservation();
        when(reservation.isEmpty()).thenReturn(false);

        checkOutService.checkoutCustomer(A_CUSTOMER_ID);

        verify(seatingService, never()).removeReservationByGroupName(any());
    }

    @Test
    public void givenCustomerWithGroup_whenCheckOutAndReservationEmpty_thenReservationIsRemoved() {
        givenCustomerWithGroup();
        givenSeat(A_SEAT_ID);
        Reservation reservation = givenReservation();
        when(reservation.isEmpty()).thenReturn(true);

        checkOutService.checkoutCustomer(A_CUSTOMER_ID);

        verify(seatingService).removeReservationByGroupName(A_GROUP_NAME);
    }

    @Test
    public void givenCustomerWithGroup_whenCheckOutAndReservationEmpty_thenReservationLockedSeatsAreUnassigned() {
        givenCustomerWithGroup();
        givenSeat(A_SEAT_ID);
        Reservation reservation = givenReservation();
        when(reservation.isEmpty()).thenReturn(true);
        Seat lockedSeat = givenSeat(ANOTHER_SEAT_ID);
        when(reservation.getLockedSeatsId()).thenReturn(new ArrayList<>(List.of(ANOTHER_SEAT_ID)));

        checkOutService.checkoutCustomer(A_CUSTOMER_ID);

        verify(lockedSeat).unassign();
    }

    private Customer givenCustomerWithGroup() {
        Customer customer = mock(Customer.class);
        when(customerRepository.findCustomerByCustomerId(CheckOutServiceTest.A_CUSTOMER_ID)).thenReturn(customer);
        when(customer.hasGroup()).thenReturn(true);
        when(customer.getSeatId()).thenReturn(CheckOutServiceTest.A_SEAT_ID);
        when(customer.getGroupName()).thenReturn(CheckOutServiceTest.A_GROUP_NAME);

        return customer;
    }

    private Customer givenCustomerWithoutGroup() {
        Customer customer = mock(Customer.class);
        when(customerRepository.findCustomerByCustomerId(CheckOutServiceTest.A_CUSTOMER_ID)).thenReturn(customer);
        when(customer.hasGroup()).thenReturn(false);
        when(customer.getSeatId()).thenReturn(CheckOutServiceTest.A_SEAT_ID);
        when(customer.getGroupName()).thenReturn(null);

        return customer;
    }

    public Seat givenSeat(SeatId seatId) {
        Seat seat = mock(Seat.class);
        when(seat.getId()).thenReturn(seatId);
        when(seatingService.getSeatById(seatId)).thenReturn(seat);

        return seat;
    }

    @Test
    public void whenProcessingBillForCustomer_thenCreateBillWithCustomerOrdersAndTaxRate() {
        Config config = new Config();
        config.setTaxRate(A_TAX_RATE);
        when(configRepository.findConfig()).thenReturn(config);
        checkOutService.processBill(A_CUSTOMER_ID, SOME_CUSTOMER_ORDER);
        verify(billFactory).createBill(SOME_CUSTOMER_ORDER, A_TAX_RATE, null);
    }

    @Test
    public void whenProcessingBillForCustomer_thenSaveCreatedBillInRepository() {
        Bill bill = mock(Bill.class);
        Config config = new Config();
        when(configRepository.findConfig()).thenReturn(config);
        when(billFactory.createBill(any(), any(), any())).thenReturn(bill);

        checkOutService.processBill(A_CUSTOMER_ID, SOME_CUSTOMER_ORDER);

        verify(billRepository).saveBillByCustomerId(A_CUSTOMER_ID, bill);
    }

    @Test
    public void whenProcessingBillForCustomerWithGroup_thenCreateBillWithCustomerOrdersAndTaxRateAndTipRate() {Bill bill = mock(Bill.class);
        when(billFactory.createBill(any(), any(), any())).thenReturn(bill);
        Config config = new Config();
        when(configRepository.findConfig()).thenReturn(config);

        checkOutService.processBillForGroup(A_CUSTOMER_ID, SOME_CUSTOMER_ORDER);

        verify(billFactory).createBill(SOME_CUSTOMER_ORDER, configRepository.findConfig().getTaxRate(), configRepository.findConfig().getGroupTipRate());

    }

    @Test
    public void whenProcessingBillForCustomerWithGroup_thenSaveCreatedBillInRepository() {
        Bill bill = mock(Bill.class);
        Config config = new Config();
        when(configRepository.findConfig()).thenReturn(config);
        when(billFactory.createBill(any(), any(), any())).thenReturn(bill);

        checkOutService.processBill(A_CUSTOMER_ID, SOME_CUSTOMER_ORDER);

        verify(billRepository).saveBillByCustomerId(A_CUSTOMER_ID, bill);
    }

    private Reservation givenReservation() {
        Reservation reservation = mock(Reservation.class);
        when(seatingService.getReservationByGroupName(CheckOutServiceTest.A_GROUP_NAME)).thenReturn(reservation);

        return reservation;
    }

    private Order givenCustomerOrder() {
        Order order = mock(Order.class);
        when(orderRepository.findOrderByCustomerId(CheckOutServiceTest.A_CUSTOMER_ID)).thenReturn(order);

        return order;
    }*/
}
