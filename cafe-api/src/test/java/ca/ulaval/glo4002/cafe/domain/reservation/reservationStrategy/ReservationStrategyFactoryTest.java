package ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReservationStrategyFactoryTest {
    private ReservationStrategyFactory reservationStrategyFactory;

    @BeforeEach
    public void createFactory() {
        reservationStrategyFactory = new ReservationStrategyFactory();
    }

    @Test
    public void whenPassingFullCubesReservationMethod_thenCreateFullCubesStrategy() {
        ReservationStrategy fullCubesStrategy = reservationStrategyFactory.createReservationStrategy(GroupReservationStrategy.FullCubes);

        assertEquals(FullCubesStrategy.class, fullCubesStrategy.getClass());
    }

    @Test
    public void whenPassingNoLonersReservationMethod_thenCreateNoLonersStrategy() {
        ReservationStrategy noLonersStrategy = reservationStrategyFactory.createReservationStrategy(GroupReservationStrategy.NoLoners);

        assertEquals(NoLonersStrategy.class, noLonersStrategy.getClass());
    }

    @Test
    public void whenPassingDefaultReservationMethod_thenCreateOrderedStrategy() {
        ReservationStrategy orderedStrategy = reservationStrategyFactory.createReservationStrategy(GroupReservationStrategy.Default);

        assertEquals(OrderedStrategy.class, orderedStrategy.getClass());
    }
}