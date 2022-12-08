package ca.ulaval.glo4002.cafe.domain.cube;

import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.seat.SeatStatus;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CubeTest {
    private static final String A_CUBE_NAME = "Super Cube";

    @Test
    public void whenInitializedOnlyWithName_thenCubeHasNoSeat() {
        Cube cube = new Cube(A_CUBE_NAME);

        List<Seat> actual = cube.getSeats();

        assertEquals(0, actual.size());
    }

    @Test
    public void whenInitializedWithName_thenNameIsSet() {
        Cube cube = new Cube(A_CUBE_NAME);

        String actual = cube.getName();

        assertEquals(A_CUBE_NAME, actual);
    }

    @Test
    public void whenInitializedWithListOfSeats_thenAllSeatsAreIncludedInCube() {
        ArrayList<Seat> expected = new ArrayList<>();
        expected.add(new Seat(1, SeatStatus.AVAILABLE));
        expected.add(new Seat(2, SeatStatus.AVAILABLE));
        Cube cube = new Cube(A_CUBE_NAME, expected);

        List<Seat> actual = cube.getSeats();

        assertEquals(expected, actual);
    }

    @Test
    public void whenASeatIsAdded_thenIsReturnedByGetSeats() {
        Cube cube = new Cube(A_CUBE_NAME);
        Seat seat = new Seat(1, SeatStatus.AVAILABLE);
        ArrayList<Seat> expected = new ArrayList<>(List.of(seat));

        cube.addSeat(seat.getId().getId());

        List<Seat> actual = cube.getSeats();
        assertThat(expected).usingRecursiveFieldByFieldElementComparator().isEqualTo(actual);
    }

    @Test
    public void whenInitializedWithAvailableSeats_thenHasFreeSeat() {
        ArrayList<Seat> seats = new ArrayList<>();
        seats.add(new Seat(1, SeatStatus.AVAILABLE));
        Cube cube = new Cube(A_CUBE_NAME, seats);

        assertTrue(cube.hasFreeSeat());
    }

    @Test
    public void whenInitializedWithOccupiedSeats_thenHasNoFreeSeats() {
        ArrayList<Seat> seats = new ArrayList<>();
        seats.add(new Seat(1, SeatStatus.OCCUPIED));
        seats.add(new Seat(2, SeatStatus.OCCUPIED));
        Cube cube = new Cube(A_CUBE_NAME, seats);

        assertFalse(cube.hasFreeSeat());
    }

    @Test
    public void givenACubeWithSecondSeatAvailable_whenGetFreeFirstFreeSeat_thenReturnsSecondSeat() {
        ArrayList<Seat> seats = new ArrayList<>();
        Seat firstSeat = new Seat(1, SeatStatus.OCCUPIED);
        Seat secondSeat = new Seat(2, SeatStatus.AVAILABLE);
        seats.add(firstSeat);
        seats.add(secondSeat);
        Cube cube = new Cube(A_CUBE_NAME, seats);

        Seat seat = cube.getFirstFreeSeat();

        assertEquals(secondSeat, seat);
    }

    @Test
    public void givenAllSeatsOccupied_whenGetFirstFreeSeat_thenReturnsNull() {
        ArrayList<Seat> seats = new ArrayList<>();
        Seat firstSeat = new Seat(1, SeatStatus.OCCUPIED);
        Seat secondSeat = new Seat(2, SeatStatus.OCCUPIED);
        seats.add(firstSeat);
        seats.add(secondSeat);
        Cube cube = new Cube(A_CUBE_NAME, seats);

        Seat seat = cube.getFirstFreeSeat();

        assertNull(seat);
    }
}
