package ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy;

import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import ca.ulaval.glo4002.cafe.domain.seat.SeatStatus;
import ca.ulaval.glo4002.cafe.domain.seating.SeatingOrganizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderedStrategyTest {
    private OrderedStrategy orderedStrategy;
    private SeatingOrganizer seatingOrganizer;

    @BeforeEach
    public void setup() {
        orderedStrategy = new OrderedStrategy();
        seatingOrganizer = mock(SeatingOrganizer.class);
    }

    @Test
    public void givenAvailableSeats_whenReserveNoSeat_thenReturnEmptyList() {
        List<Seat> availableSeats = new ArrayList<>(List.of(new Seat(1, SeatStatus.AVAILABLE)));
        when(seatingOrganizer.getFreeSeats()).thenReturn(availableSeats);

        List<Seat> indexesToReserve = orderedStrategy.getReservationSeats(seatingOrganizer, 0);

        assertTrue(indexesToReserve.isEmpty());
    }

    @Test
    public void givenANumberOfAvailableSeats_whenReserveALowerNumberOfSeats_thenReturnSeatsWithLowerIds() {
        List<Seat> availableSeats = new ArrayList<>(List.of(new Seat(1, SeatStatus.AVAILABLE),
                new Seat(2, SeatStatus.AVAILABLE),
                new Seat(3, SeatStatus.AVAILABLE)));
        when(seatingOrganizer.getFreeSeats()).thenReturn(availableSeats);

        List<Seat> indexesToReserve = orderedStrategy.getReservationSeats(seatingOrganizer, 2);

        assertEquals(2, indexesToReserve.size());
        assertEquals(new SeatId(1), indexesToReserve.get(0).getId());
        assertEquals(new SeatId(2), indexesToReserve.get(1).getId());
    }

    @Test
    public void givenANumberOfAvailableSeats_whenReserveTheSameNumberOfSeats_thenReturnAllAvailableSeats() {
        List<Seat> availableSeats = new ArrayList<>(List.of(new Seat(1, SeatStatus.AVAILABLE),
                new Seat(2, SeatStatus.AVAILABLE)));
        when(seatingOrganizer.getFreeSeats()).thenReturn(availableSeats);

        List<Seat> indexesToReserve = orderedStrategy.getReservationSeats(seatingOrganizer, 2);

        assertEquals(2, indexesToReserve.size());
        assertEquals(new SeatId(1), indexesToReserve.get(0).getId());
        assertEquals(new SeatId(2), indexesToReserve.get(1).getId());
    }
}
