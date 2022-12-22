package ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy;

import ca.ulaval.glo4002.cafe.domain.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.seat.Seat;

import java.util.ArrayList;
import java.util.List;

/**
 * Ordered strategy, will reserve the first available seats.
 */
public class OrderedStrategy implements ReservationStrategy {

    @Override
    public List<Seat> getReservationSeats(List<Cube> cubes, int nbToReserve) {
        List<Seat> freeSeats = this.getFreeSeats(cubes);
        return freeSeats.subList(0, nbToReserve);
    }

    private List<Seat> getFreeSeats(List<Cube> cubes) {
        List<Seat> freeSeats = new ArrayList<>();
        for (Cube cube : cubes) {
            freeSeats.addAll(cube.getFreeSeats());
        }
        return freeSeats;
    }
}
