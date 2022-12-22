package ca.ulaval.glo4002.cafe.application.reservation;

import ca.ulaval.glo4002.cafe.domain.config.IConfigRepository;
import ca.ulaval.glo4002.cafe.domain.reservation.*;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy.ReservationStrategyFactory;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import ca.ulaval.glo4002.cafe.domain.seating.SeatingOrganizer;

import java.util.List;

public class ReservationService {
    private final ReservationStrategyFactory reservationStrategyFactory;
    private final IReservationRepository reservationRepository;
    private final ReservationFactory reservationFactory;
    private final IConfigRepository configRepository;
    private final SeatingOrganizer seatingOrganizer;

    public ReservationService(IConfigRepository configRepository, ReservationStrategyFactory reservationStrategyFactory, ReservationFactory reservationFactory, IReservationRepository reservationRepository, SeatingOrganizer seatingOrganizer) {
        this.reservationStrategyFactory = reservationStrategyFactory;
        this.reservationFactory = reservationFactory;
        this.reservationRepository = reservationRepository;
        this.configRepository = configRepository;
        this.seatingOrganizer = seatingOrganizer;
    }

    public void addReservation(Group group) {
        if (this.reservationRepository.doesReservationExistsForGroup(group.getName())) {
            throw new DuplicateGroupNameException();
        }
        List<SeatId> reservedSeats = this.seatingOrganizer.reserveSeats(
                group.getSize(),
                group.getName(),
                this.reservationStrategyFactory.createReservationStrategy(configRepository.findConfig().getReservationMethod())
        );
        Reservation reservation = this.reservationFactory.createReservation(group, reservedSeats);
        this.reservationRepository.saveReservation(reservation);
    }

    public List<Reservation> getReservations() {
        return this.reservationRepository.findReservations();
    }
    
}
