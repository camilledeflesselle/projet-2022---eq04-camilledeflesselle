package ca.ulaval.glo4002.cafe.domain.seating;

import ca.ulaval.glo4002.cafe.domain.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy.IGroupReservationStrategy;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SeatingOrganizerFactoryTest {
    private static final String A_CUBE_NAME = "bob";
    private static final List<Cube> SOME_CUBES = new ArrayList<>(List.of(new Cube(A_CUBE_NAME)));

    @Test
    public void whenCreateSeatingOrganizer_thenReturnsSeatingOrganizerObject() {
        SeatingOrganizerFactory seatingOrganizerFactory = new SeatingOrganizerFactory();
        IGroupReservationStrategy groupReservationStrategy = givenGroupReservationStrategy();

        assertEquals(SeatingOrganizer.class, seatingOrganizerFactory.createSeatingOrganizer(SOME_CUBES, groupReservationStrategy).getClass());
    }

    private IGroupReservationStrategy givenGroupReservationStrategy() {
        return (seatingOrganizer, nbToReserve, seatsPerCube) -> null;
    }
}