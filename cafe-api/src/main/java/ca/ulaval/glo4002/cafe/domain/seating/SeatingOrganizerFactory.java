package ca.ulaval.glo4002.cafe.domain.seating;

import ca.ulaval.glo4002.cafe.domain.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy.IGroupReservationStrategy;
import java.util.List;

public class SeatingOrganizerFactory {
    public SeatingOrganizer createSeatingOrganizer(List<Cube> cubes, IGroupReservationStrategy groupReservationStrategy) {
        return new SeatingOrganizer(cubes, groupReservationStrategy);
    }
}
