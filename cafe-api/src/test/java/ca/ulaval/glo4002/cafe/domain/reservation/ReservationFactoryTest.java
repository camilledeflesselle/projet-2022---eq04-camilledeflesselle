package ca.ulaval.glo4002.cafe.domain.reservation;

import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReservationFactoryTest {
    @Test
    public void whenCreateReservation_thenReturnsReservationObject() {
        Group group = new Group("name", 3);
        List<SeatId> reservedSeatIds = new ArrayList<>();
        ReservationFactory reservationFactory = new ReservationFactory();
        assertEquals(Reservation.class, reservationFactory.createReservation(group, reservedSeatIds).getClass());
    }
}