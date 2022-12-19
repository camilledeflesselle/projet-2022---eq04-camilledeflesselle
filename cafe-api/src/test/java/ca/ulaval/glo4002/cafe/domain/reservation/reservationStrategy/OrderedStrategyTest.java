package ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy;

import ca.ulaval.glo4002.cafe.domain.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.cube.CubesListFactory;
import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class OrderedStrategyTest {

    private static final List<String> A_LIST_OF_NAMES = new ArrayList<>(List.of("name_1"));
    private OrderedStrategy orderedStrategy;
    private List<Cube> cubes;
    private CubesListFactory cubesListFactory;

    @BeforeEach
    public void setup() {
        orderedStrategy = new OrderedStrategy();
        cubesListFactory = new CubesListFactory();
        cubes = List.of(mock(Cube.class));
    }

    @Test
    public void givenAvailableSeats_whenReserveNoSeat_thenReturnEmptyList() {
        cubes = givenEmptyCubes(3);

        List<Seat> indexesToReserve = orderedStrategy.getReservationSeats(cubes, 0);

        assertTrue(indexesToReserve.isEmpty());
    }

    @Test
    public void givenANumberOfAvailableSeats_whenReserveALowerNumberOfSeats_thenReturnSeatsWithLowerIds() {
        cubes = givenEmptyCubes(3);

        List<Seat> indexesToReserve = orderedStrategy.getReservationSeats(cubes, 2);

        assertEquals(2, indexesToReserve.size());
        assertEquals(new SeatId(1), indexesToReserve.get(0).getId());
        assertEquals(new SeatId(2), indexesToReserve.get(1).getId());
    }

    @Test
    public void givenANumberOfAvailableSeats_whenReserveTheSameNumberOfSeats_thenReturnAllAvailableSeats() {
        cubes = givenEmptyCubes(2);

        List<Seat> indexesToReserve = orderedStrategy.getReservationSeats(cubes, 2);

        assertEquals(2, indexesToReserve.size());
        assertEquals(new SeatId(1), indexesToReserve.get(0).getId());
        assertEquals(new SeatId(2), indexesToReserve.get(1).getId());
    }

    private List<Cube> givenEmptyCubes(int seatsPerCube) {
        return cubesListFactory.create(A_LIST_OF_NAMES, seatsPerCube);
    }
}
