package ca.ulaval.glo4002.cafe.domain.seat;

import ca.ulaval.glo4002.cafe.domain.reservation.Group;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SeatTest {
    private static final String A_GROUP_NAME = "A group name";

    @Test
    public void whenAssigned_thenStatusShouldBeOccupied() {
        Seat seat = new Seat(1, SeatStatus.Available);

        seat.assign();

        assertEquals(SeatStatus.Occupied, seat.getStatus());
    }

    @Test
    public void whenReserved_thenStatusShouldBeReserved() {
        Seat seat = new Seat(1, SeatStatus.Available);
        Group group = new Group(A_GROUP_NAME, 2);

        seat.reserve(group.getName());

        assertEquals(SeatStatus.Reserved, seat.getStatus());
    }
}
