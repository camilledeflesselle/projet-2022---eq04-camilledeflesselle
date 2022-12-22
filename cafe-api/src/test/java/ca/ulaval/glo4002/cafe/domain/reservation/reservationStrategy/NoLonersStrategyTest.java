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

import static org.junit.jupiter.api.Assertions.*;

class NoLonersStrategyTest {

    private static final List<String> A_LIST_OF_TWO_NAMES = Arrays.asList("name_1", "name_2");
    private static final int A_CUBE_SIZE = 3;

    private NoLonersStrategy noLonersStrategy;
    private CubesListFactory cubesListFactory;
    private List<Cube> cubes;

    @BeforeEach
    public void setup() {
        cubesListFactory = new CubesListFactory();
        noLonersStrategy = new NoLonersStrategy();
    }

    @Test
    public void givenAvailableSeats_whenReserveNoSeat_thenReturnEmptyList() {
        cubes = givenEmptyCubes(A_CUBE_SIZE);

        List<Seat> seatsToReserve = noLonersStrategy.getReservationSeats(cubes, 0);

        assertTrue(seatsToReserve.isEmpty());
    }

    @Test
    public void givenTwoCubesWithOneSeatAvailableInTheFirstOne_whenReserveThreeSeats_thenReturnTheSecondCubesSeats() {
        cubes = givenTwoCubesOfThreeSeatsWithOneSeatAvailableInTheFirstOne();

        List<Seat> seatsToReserve = noLonersStrategy.getReservationSeats(cubes, 3);

        assertEquals(3, seatsToReserve.size());
        assertEquals(new SeatId(4), seatsToReserve.get(0).getId());
        assertEquals(new SeatId(5), seatsToReserve.get(1).getId());
        assertEquals(new SeatId(6), seatsToReserve.get(2).getId());
    }

    @Test
    public void givenTwoCubesWithTwoSeatAvailableInTheFirstOne_whenReserveThreeSeats_thenReturnTheSecondCubeSeats() {
        cubes = givenTwoCubesOfThreeSeatsWithTwoSeatsAvailableInTheFirstOne();

        List<Seat> seatsToReserve = noLonersStrategy.getReservationSeats(cubes, 3);

        assertEquals(3, seatsToReserve.size());
        assertEquals(new SeatId(4), seatsToReserve.get(0).getId());
        assertEquals(new SeatId(5), seatsToReserve.get(1).getId());
        assertEquals(new SeatId(6), seatsToReserve.get(2).getId());
    }

    @Test
    public void givenTwoEmptyCubesOfThreeSeats_whenReserveThreeSeats_thenReturnTheFirstCubeSeats() {
        cubes = givenEmptyCubes(3);

        List<Seat> seatsToReserve = noLonersStrategy.getReservationSeats(cubes, 3);

        assertEquals(3, seatsToReserve.size());
        assertEquals(new SeatId(1), seatsToReserve.get(0).getId());
        assertEquals(new SeatId(2), seatsToReserve.get(1).getId());
        assertEquals(new SeatId(3), seatsToReserve.get(2).getId());
    }

    @Test
    public void givenTwoCubesWithTwoSeatsAvailableInEach_whenReserveFourSeats_thenReturnTheTwoSeatsAvailableInEachOne() {
        cubes = givenTwoCubesOfThreeSeatsWithTwoSeatsAvailableInEach();

        List<Seat> seatsToReserve = noLonersStrategy.getReservationSeats(cubes, 4);

        assertEquals(4, seatsToReserve.size());
        assertEquals(new SeatId(2), seatsToReserve.get(0).getId());
        assertEquals(new SeatId(3), seatsToReserve.get(1).getId());
        assertEquals(new SeatId(5), seatsToReserve.get(2).getId());
        assertEquals(new SeatId(6), seatsToReserve.get(3).getId());
    }

    @Test
    public void givenTwoCubesOfFourSeatsWithFirstSeatOccupiedInEach_whenReserveFourSeats_thenReturnSecondAndThirdSeatsOfEachCube() {
        cubes = givenTwoCubesOfFourSeatsWithFirstSeatOccupiedInEach();

        List<Seat> seatsToReserve = noLonersStrategy.getReservationSeats(cubes, 4);

        assertEquals(4, seatsToReserve.size());
        assertEquals(new SeatId(2), seatsToReserve.get(0).getId());
        assertEquals(new SeatId(3), seatsToReserve.get(1).getId());
        assertEquals(new SeatId(6), seatsToReserve.get(2).getId());
        assertEquals(new SeatId(7), seatsToReserve.get(3).getId());
    }

    @Test
    public void givenTwoCubesOfTwoSeats_whenReserveThreeSeats_thenNotAbleToReserve() {
        cubes = givenEmptyCubes(2);

        assertThrows(
                NoSeatAvailableException.class,
                () -> noLonersStrategy.getReservationSeats(cubes, 3)
        );
    }

    @Test
    public void givenTwoCubesOfThreeSeatsWithOneSeatAvailableInTheFirstOne_whenReserveFourSeat_thenNotAbleToReserve() {
        cubes = givenTwoCubesOfThreeSeatsWithOneSeatAvailableInTheFirstOne();

        assertThrows(
                NoSeatAvailableException.class,
                () -> noLonersStrategy.getReservationSeats(cubes, 4)
        );
    }

    private List<Cube> givenEmptyCubes(int seatsPerCube) {
        return cubesListFactory.create(A_LIST_OF_TWO_NAMES, seatsPerCube);
    }

    private List<Cube> givenTwoCubesOfThreeSeatsWithOneSeatAvailableInTheFirstOne() {
        List<Cube> cubes = cubesListFactory.create(A_LIST_OF_TWO_NAMES, 3);
        cubes.get(0).getSeats().get(0).assign();
        cubes.get(0).getSeats().get(1).assign();
        return cubes;
    }

    private List<Cube> givenTwoCubesOfThreeSeatsWithTwoSeatsAvailableInTheFirstOne() {
        List<Cube> cubes = cubesListFactory.create(A_LIST_OF_TWO_NAMES, 3);
        cubes.get(0).getSeats().get(0).assign();
        return cubes;
    }

    private List<Cube> givenTwoCubesOfThreeSeatsWithTwoSeatsAvailableInEach() {
        List<Cube> cubes = cubesListFactory.create(A_LIST_OF_TWO_NAMES, 3);
        cubes.get(0).getSeats().get(0).assign();
        cubes.get(1).getSeats().get(0).assign();
        return cubes;
    }

    private List<Cube> givenTwoCubesOfFourSeatsWithFirstSeatOccupiedInEach() {
        List<Cube> cubes = cubesListFactory.create(A_LIST_OF_TWO_NAMES, 4);
        cubes.get(0).getSeats().get(0).assign();
        cubes.get(1).getSeats().get(0).assign();
        return cubes;
    }
}
