package ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy;

import ca.ulaval.glo4002.cafe.domain.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.seat.NoSeatAvailableException;
import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.seating.SeatingOrganizer;

import java.util.ArrayList;
import java.util.List;

/**
 * Full cubes strategy, will reserve the full cubes if all seats are available in it.
 */
public class FullCubesStrategy implements IGroupReservationStrategy {
    @Override
    public List<Seat> getReservationSeats(SeatingOrganizer seatingOrganizer, int nbToReserve, int seatsPerCube) {

        List<Seat> seatsToReserve = new ArrayList<>();
        for (Cube cube : seatingOrganizer.getCubes()) {
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
