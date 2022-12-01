package ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy;

public class ReservationStrategyFactory {
    public IGroupReservationStrategy createReservationStrategy(GroupReservationMethod method) {
        switch (method) {
            case FULL_CUBES -> {
                return new FullCubesStrategy();
            }
            case NO_LONERS -> {
                return new NoLonersStrategy();
            }
            default -> {
                return new OrderedStrategy();
            }
        }
    }
}
