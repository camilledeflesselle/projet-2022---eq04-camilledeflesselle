package ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy;

import ca.ulaval.glo4002.cafe.domain.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.seat.NoSeatAvailableException;
import ca.ulaval.glo4002.cafe.domain.seat.Seat;

import java.util.ArrayList;
import java.util.List;

/**
 * Full cubes strategy, will reserve the full cubes if all seats are available in it.
 */
public class FullCubesStrategy implements ReservationStrategy {
    @Override
    public List<Seat> getReservationSeats(List<Cube> cubes, int nbToReserve) {

        List<Seat> seatsToReserve = new ArrayList<>();
        for (Cube cube : cubes) {
            if (cube.isEmpty() & seatsToReserve.size() < nbToReserve) {
                seatsToReserve.addAll(cube.getSeats());
            }
        }
        if (seatsToReserve.size() >= nbToReserve) {
            return seatsToReserve;
        }
        throw new NoSeatAvailableException();
    }
}
