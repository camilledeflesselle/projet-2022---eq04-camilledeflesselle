package ca.ulaval.glo4002.cafe.domain.cube;

import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import ca.ulaval.glo4002.cafe.domain.seat.SeatStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CubeListFactoryTest {
    private static final List<String> SOME_CUBE_NAMES = new ArrayList<>(List.of("Bob", "Alice"));
    private static final List<String> EXPECTED_CUBES_ORDER = new ArrayList<>(List.of("Alice", "Bob"));

    private CubesListFactory cubesListFactory;

    @BeforeEach
    public void setup() {
        cubesListFactory = new CubesListFactory();
    }

    @Test
    public void whenCreatingCubes_allSeatsAvailable() {
        List<Cube> cubes = cubesListFactory.create(SOME_CUBE_NAMES, 1);
        Seat seat1 = cubes.get(0).getFirstFreeSeat();
        Seat seat2 = cubes.get(1).getFirstFreeSeat();

        assertEquals(SeatStatus.AVAILABLE, seat1.getStatus());
        assertEquals(SeatStatus.AVAILABLE, seat2.getStatus());
    }

    @Test
    public void whenCreatingCubes_cubesAreInAlphabeticalOrder() {
        List<Cube> cubes = cubesListFactory.create(SOME_CUBE_NAMES, 1);
        Cube cube1 = cubes.get(0);
        Cube cube2 = cubes.get(1);

        List<String> cubeNames = new ArrayList<>(List.of(cube1.getName(), cube2.getName()));

        assertEquals(EXPECTED_CUBES_ORDER, cubeNames);
    }

    @Test
    public void whenCreatingCubes_cubesHaveRightNumberOfSeats() {
        List<Cube> cubes = cubesListFactory.create(SOME_CUBE_NAMES, 2);
        Cube cube1 = cubes.get(0);
        Cube cube2 = cubes.get(1);

        assertEquals(2, cube1.getSeats().size());
        assertEquals(2, cube2.getSeats().size());
    }

    @Test
    public void whenCreatingCubes_seatsAreOrderedByNumber() {
        List<Cube> cubes = cubesListFactory.create(SOME_CUBE_NAMES, 1);
        Seat seat1 = cubes.get(0).getFirstFreeSeat();
        Seat seat2 = cubes.get(1).getFirstFreeSeat();

        assertEquals(new SeatId(1), seat1.getId());
        assertEquals(new SeatId(2), seat2.getId());
    }
}
