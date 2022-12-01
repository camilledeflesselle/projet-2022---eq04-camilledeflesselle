package ca.ulaval.glo4002.cafe.domain.reservation;

import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import java.util.List;

public class ReservationFactory {
    public Reservation createReservation(Group group, List<SeatId> reservedSeatIds) {
        return new Reservation(group, reservedSeatIds);
    }
}
