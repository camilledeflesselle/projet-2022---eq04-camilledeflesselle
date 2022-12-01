package ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy;

import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.seating.SeatingOrganizer;
import java.util.ArrayList;
import java.util.List;

/**
 * Ordered strategy, will reserve the first available seat.
 */
public class OrderedStrategy implements IGroupReservationStrategy {
    @Override
    public List<Seat> getReservationSeats(SeatingOrganizer seatingOrganizer, int nbToReserve, int seatsPerCube) {
        List<Seat> freeSeats = seatingOrganizer.getFreeSeats();
        List<Seat> seatToReserve = new ArrayList<>();
        for (int i = 0; i < nbToReserve; i++) {
            Seat seat = freeSeats.get(i);
            seatToReserve.add(seat);
        }
        return seatToReserve;
    }
}
