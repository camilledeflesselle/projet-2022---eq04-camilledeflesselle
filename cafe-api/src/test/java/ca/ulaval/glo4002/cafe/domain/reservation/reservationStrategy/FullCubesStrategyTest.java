package ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy;

import ca.ulaval.glo4002.cafe.domain.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.cube.CubesListFactory;
import ca.ulaval.glo4002.cafe.domain.seat.NoSeatAvailableException;
import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FullCubesStrategyTest {
    private static final List<String> A_LIST_OF_NAMES = Arrays.asList("name_1", "name_2");
    private static final int A_CUBE_SIZE = 3;

    private FullCubesStrategy fullCubesStrategy;
    private CubesListFactory cubesListFactory;
    private List<Cube> cubes;

    @BeforeEach
    public void setup() {
        cubesListFactory = new CubesListFactory();
        fullCubesStrategy = new FullCubesStrategy();
    }

    @Test
    public void givenAvailableSeats_whenReserveNoSeat_thenReturnEmptyList() {
        cubes = givenEmptyCubes(A_CUBE_SIZE);

        List<Seat> seatsToReserve = fullCubesStrategy.getReservationSeats(cubes, 0);

        assertTrue(seatsToReserve.isEmpty());
    }

    @Test
    public void givenAListOfEmptyCubes_whenReserveOneSeat_thenReturnFirstCubeSeats() {
        cubes = givenEmptyCubes(2);

        List<Seat> seatsToReserve = fullCubesStrategy.getReservationSeats(
                cubes, 1);

        assertEquals(2, seatsToReserve.size());
        assertEquals(new SeatId(1), seatsToReserve.get(0).getId());
        assertEquals(new SeatId(2), seatsToReserve.get(1).getId());
    }

    @Test
    public void givenAListOfTwoEmptyCubes_whenReserveSizeMoreThanOneCube_thenReturnTheTwoCubesSeats() {
         cubes = givenEmptyCubes(2);

        List<Seat> seatsToReserve = fullCubesStrategy.getReservationSeats(
                cubes, 3);

        assertEquals(4, seatsToReserve.size());
        assertEquals(new SeatId(1), seatsToReserve.get(0).getId());
        assertEquals(new SeatId(2), seatsToReserve.get(1).getId());
        assertEquals(new SeatId(3), seatsToReserve.get(2).getId());
        assertEquals(new SeatId(4), seatsToReserve.get(3).getId());
    }

    @Test
    public void givenAListOfOnlyLastCubeAvailable_whenReserveOneSeat_thenReturnLastCubeSeats() {
        cubes = givenCubesWithOnlyLastOneEmpty(2);

        List<Seat> seatsToReserve = fullCubesStrategy.getReservationSeats(
                cubes, 1);

        assertEquals(2, seatsToReserve.size());
        assertEquals(new SeatId(3), seatsToReserve.get(0).getId());
        assertEquals(new SeatId(4), seatsToReserve.get(1).getId());
    }

    @Test
    public void givenAListOfNonEmptyCubes_whenTryingToReserveSeats_thenNotAbleToReserve() {
        cubes = givenNonEmptyCubes();

        assertThrows(
                NoSeatAvailableException.class,
                () -> fullCubesStrategy.getReservationSeats(cubes, 2)
        );
    }

    private List<Cube> givenEmptyCubes(int seatsPerCube) {
        return cubesListFactory.create(A_LIST_OF_NAMES, seatsPerCube);
    }

    private List<Cube> givenNonEmptyCubes() {
        List<Cube> cubes = cubesListFactory.create(A_LIST_OF_NAMES, FullCubesStrategyTest.A_CUBE_SIZE);
        cubes.get(0).getFirstFreeSeat().assign();
        cubes.get(1).getFirstFreeSeat().assign();
        return cubes;
    }

    private List<Cube> givenCubesWithOnlyLastOneEmpty(int seatsPerCube) {
        List<Cube> cubes = cubesListFactory.create(A_LIST_OF_NAMES, seatsPerCube);
        cubes.get(0).getFirstFreeSeat().assign();
        return cubes;
    }
}
