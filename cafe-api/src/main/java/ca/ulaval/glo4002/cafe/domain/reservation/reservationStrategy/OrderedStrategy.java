package ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy;

import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.seating.SeatingOrganizer;
import java.util.List;

/**
 * Ordered strategy, will reserve the first available seats.
 */
public class OrderedStrategy implements IGroupReservationStrategy {
    @Override
    public List<Seat> getReservationSeats(SeatingOrganizer seatingOrganizer, int nbToReserve) {
        List<Seat> freeSeats = seatingOrganizer.getFreeSeats();
        return freeSeats.subList(0, nbToReserve);
    }
}
