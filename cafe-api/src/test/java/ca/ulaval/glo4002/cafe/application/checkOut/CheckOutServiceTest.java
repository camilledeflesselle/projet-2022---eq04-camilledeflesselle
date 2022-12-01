package ca.ulaval.glo4002.cafe.application.checkOut;

import ca.ulaval.glo4002.cafe.application.bill.BillService;
import ca.ulaval.glo4002.cafe.application.customer.CustomerService;
import ca.ulaval.glo4002.cafe.application.seating.SeatingService;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class CheckOutServiceTest {
    private static final CustomerId A_CUSTOMER_ID = new CustomerId("id");
    private static final SeatId A_SEAT_ID = new SeatId(1);
    private static final SeatId ANOTHER_SEAT_ID = new SeatId(2);
    private static final String A_GROUP_NAME = "group";

    private static CheckOutService checkOutService;
    private static CustomerService customerService;
    private static SeatingService seatingService;
    private static BillService billService;

    @BeforeEach
    public void setup() {
        customerService = mock(CustomerService.class);
        seatingService = mock(SeatingService.class);
        billService = mock(BillService.class);
        checkOutService = new CheckOutService(customerService, seatingService, billService);
    }

    @Test
    public void whenCustomerCheckOut_thenSearchForCustomerByCustomerId() {
        givenCustomerWithoutGroup();
        givenSeat(A_SEAT_ID);

        checkOutService.checkoutCustomer(A_CUSTOMER_ID);

        verify(customerService).findCustomer(A_CUSTOMER_ID);
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

        verify(billService).processBillForCustomer(A_CUSTOMER_ID, order);
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

        verify(billService).processBillForGroup(A_CUSTOMER_ID, order);
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
        when(customerService.findCustomer(CheckOutServiceTest.A_CUSTOMER_ID)).thenReturn(customer);
        when(customer.hasGroup()).thenReturn(true);
        when(customer.getSeatId()).thenReturn(CheckOutServiceTest.A_SEAT_ID);
        when(customer.getGroupName()).thenReturn(CheckOutServiceTest.A_GROUP_NAME);

        return customer;
    }

    private Customer givenCustomerWithoutGroup() {
        Customer customer = mock(Customer.class);
        when(customerService.findCustomer(CheckOutServiceTest.A_CUSTOMER_ID)).thenReturn(customer);
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

    private Reservation givenReservation() {
        Reservation reservation = mock(Reservation.class);
        when(seatingService.getReservationByGroupName(CheckOutServiceTest.A_GROUP_NAME)).thenReturn(reservation);

        return reservation;
    }

    private Order givenCustomerOrder() {
        Order order = mock(Order.class);
        when(customerService.findOrCreateEmptyOrder(CheckOutServiceTest.A_CUSTOMER_ID)).thenReturn(order);

        return order;
    }
}
