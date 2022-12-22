package ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy;

public class ReservationStrategyFactory {
    public ReservationStrategy createReservationStrategy(GroupReservationStrategy method) {
        switch (method) {
            case FullCubes -> {
                return new FullCubesStrategy();
            }
            case NoLoners -> {
                return new NoLonersStrategy();
            }
            default -> {
                return new OrderedStrategy();
            }
        }
    }
}
