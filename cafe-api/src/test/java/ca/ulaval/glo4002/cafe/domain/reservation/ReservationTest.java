package ca.ulaval.glo4002.cafe.domain.reservation;

import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import ca.ulaval.glo4002.cafe.domain.seat.NoReservationSeatsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReservationTest {
    private static final String A_GROUP_NAME = "Team 4";
    private static final int A_GROUP_SIZE = 2;
    private static final SeatId ONE_SEAT_ID = new SeatId(1);
    private static final List<SeatId> EMPTY_SEAT_IDS_LIST = new ArrayList<>();
    private static final List<SeatId> ONE_SEAT_ID_LIST = new ArrayList<>(List.of(ONE_SEAT_ID));

    private Group group;
    private Reservation reservation;

    @BeforeEach
    public void setup() {
        group = new Group(A_GROUP_NAME, A_GROUP_SIZE);
    }

    @Test
    public void givenReservationWithEmptySeatIdsList_whenGetFirstReservedSeatId_thenReturnsException() {
        reservation = new Reservation(group, EMPTY_SEAT_IDS_LIST);

        assertThrows(NoReservationSeatsException.class, () -> {
                    reservation.popFirstReservedSeatId();
                }
        );
    }

    @Test
    public void givenReservationOfOneSeat_whenGetFirstReservedSeatId_thenReturnsCorrectSeat() {
        reservation = new Reservation(group, ONE_SEAT_ID_LIST);

        SeatId seatId = reservation.popFirstReservedSeatId();

        assertEquals(ONE_SEAT_ID, seatId);
    }

    @Test
    public void whenInitialized_thenReservationHasCorrectGroupName() {
        reservation = new Reservation(group, ONE_SEAT_ID_LIST);

        assertEquals(A_GROUP_NAME, reservation.getGroupName());
    }

    @Test
    public void whenInitialized_thenReservationIsEmpty() {
        reservation = new Reservation(group, ONE_SEAT_ID_LIST);

        assertTrue(reservation.isEmpty());
    }

    @Test
    public void whenAReservedSeatIdIsRemoved_thenReservationIsNotEmpty() {
        reservation = new Reservation(group, ONE_SEAT_ID_LIST);

        reservation.popFirstReservedSeatId();

        assertFalse(reservation.isEmpty());
    }

    @Test
    public void whenCheckOutCustomer_thenUnlockSeat() {
        Customer customerMock = mock(Customer.class);
        when(customerMock.getSeatId()).thenReturn(ONE_SEAT_ID);
        reservation = new Reservation(group, ONE_SEAT_ID_LIST);

        reservation.checkoutCustomer(customerMock);

        assertFalse(reservation.getLockedSeatsId().contains(ONE_SEAT_ID));
    }
}
