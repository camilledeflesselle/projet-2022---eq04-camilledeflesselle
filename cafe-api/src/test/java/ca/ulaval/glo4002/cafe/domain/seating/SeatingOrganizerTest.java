package ca.ulaval.glo4002.cafe.domain.seating;

import ca.ulaval.glo4002.cafe.domain.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.cube.CubesListFactory;
import ca.ulaval.glo4002.cafe.domain.reservation.Group;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy.IGroupReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.seat.NoSeatAvailableException;
import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SeatingOrganizerTest {
    private static final String A_GROUP_NAME = "Family S";
    private static final int A_GROUP_SIZE = 2;
    private static final int A_CUBE_SIZE = 2;
    private static final List<String> A_CUBE_NAME = new ArrayList<>(List.of("Bob"));

    private Group group;
    private CubesListFactory cubesListFactory;
    private IGroupReservationStrategy groupReservationStrategyMock;

    @BeforeEach
    public void setup() {
        group = new Group(A_GROUP_NAME, A_GROUP_SIZE);
        cubesListFactory = new CubesListFactory();
        groupReservationStrategyMock = mock(IGroupReservationStrategy.class);
    }

    @Test
    public void givenCubesWithoutFreeSeats_whenGetFirstFreeSeat_thenThrowsNoSeatAvailableException() {
        List<Cube> cubes = givenCubesWithoutFreeSeats();
        SeatingOrganizer seatingOrganizer = new SeatingOrganizer(cubes, groupReservationStrategyMock);

        assertThrows(NoSeatAvailableException.class, seatingOrganizer::getFirstFreeSeat);
    }

    @Test
    public void whenTryToGetNotExistingSeat_thenThrowsNotFoundException() {
        List<Cube> cubes = cubesListFactory.create(A_CUBE_NAME, 1);
        SeatingOrganizer seatingOrganizer = new SeatingOrganizer(cubes, groupReservationStrategyMock);

        assertThrows(NotFoundException.class, () -> seatingOrganizer.findSeatBySeatId(new SeatId(5)));
    }

    @Test
    public void givenOneCubeWithAllSeatsFree_whenGetFirstFreeSeat_thenReturnsTheFirstSeat() throws NoSeatAvailableException, NotFoundException {
        List<Cube> cubes = cubesListFactory.create(A_CUBE_NAME, 2);
        SeatingOrganizer seatingOrganizer = new SeatingOrganizer(cubes, groupReservationStrategyMock);

        Seat expectedSeat = seatingOrganizer.findSeatBySeatId(new SeatId(1));
        Seat returnedSeat = seatingOrganizer.getFirstFreeSeat();

        assertEquals(expectedSeat, returnedSeat);
    }

    @Test
    public void givenOneCubeWithFirstSeatReserved_whenGetFirstFreeSeat_thenReturnsTheSecondSeat() throws NoSeatAvailableException, NotFoundException {
        List<Cube> cubes = givenCubeWithFirstSeatOccupied();
        SeatingOrganizer seatingOrganizer = new SeatingOrganizer(cubes, groupReservationStrategyMock);

        Seat expectedSeat = seatingOrganizer.findSeatBySeatId(new SeatId(2));
        Seat returnedSeat = seatingOrganizer.getFirstFreeSeat();

        assertEquals(expectedSeat, returnedSeat);
    }

    @Test
    public void givenOneCubeWithAllFreeSeats_whenGetFreeSeats_thenReturnsAllSeats() throws NoSeatAvailableException {
        List<Cube> cubes = cubesListFactory.create(A_CUBE_NAME, 2);
        SeatingOrganizer seatingOrganizer = new SeatingOrganizer(cubes, groupReservationStrategyMock);

        List<Seat> expectedSeats = seatingOrganizer.getCubes().get(0).getSeats();
        List<Seat> returnedSeats = seatingOrganizer.getFreeSeats();

        assertEquals(expectedSeats, returnedSeats);
    }

    @Test
    public void givenCubesWithAvailableSeats_whenTryToReserveMoreSeatsThanAvailable_thenThrowsInsufficientSeatsException() {
        List<Cube> cubes = cubesListFactory.create(A_CUBE_NAME, 2);
        SeatingOrganizer seatingOrganizer = new SeatingOrganizer(cubes, groupReservationStrategyMock);

        assertThrows(NoSeatAvailableException.class, () -> seatingOrganizer.reserveSeats(3, A_GROUP_NAME)
        );
    }

    @Test
    public void givenOneCubeWithEnoughFreeSeats_whenTryToReserveSeats_thenCallReservationStrategy() throws NoSeatAvailableException {
        List<Cube> cubes = cubesListFactory.create(A_CUBE_NAME, 2);
        SeatingOrganizer seatingOrganizer = new SeatingOrganizer(cubes, groupReservationStrategyMock);

        seatingOrganizer.reserveSeats(group.getSize(), group.getName());

        verify(groupReservationStrategyMock).getReservationSeats(seatingOrganizer, group.getSize(), A_CUBE_SIZE);
    }

    private List<Cube> givenCubesWithoutFreeSeats() {
        List<Cube> cubes = cubesListFactory.create(A_CUBE_NAME, 1);
        cubes.get(0).getFirstFreeSeat().assign();

        return cubes;
    }

    private List<Cube> givenCubeWithFirstSeatOccupied() {
        List<Cube> cubes = cubesListFactory.create(A_CUBE_NAME, 2);
        cubes.get(0).getFirstFreeSeat().assign();

        return cubes;
    }
}
