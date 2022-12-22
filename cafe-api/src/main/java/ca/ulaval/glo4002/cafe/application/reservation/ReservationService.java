package ca.ulaval.glo4002.cafe.application.reservation;

import ca.ulaval.glo4002.cafe.domain.config.ConfigRepository;
import ca.ulaval.glo4002.cafe.domain.reservation.*;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy.ReservationStrategyFactory;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import ca.ulaval.glo4002.cafe.domain.seating.SeatingOrganizer;
import ca.ulaval.glo4002.cafe.ui.rest.DTO.ReservationDTO;
import ca.ulaval.glo4002.cafe.ui.rest.assemblers.config.ReservationsAssembler;

import java.util.List;

public class ReservationService {
    private final ReservationStrategyFactory reservationStrategyFactory;
    private final ReservationRepository reservationRepository;
    private final ReservationFactory reservationFactory;
    private final ConfigRepository configRepository;
    private final SeatingOrganizer seatingOrganizer;
    private final ReservationsAssembler reservationsAssembler;

    public ReservationService(ConfigRepository configRepository, ReservationStrategyFactory reservationStrategyFactory, ReservationFactory reservationFactory, ReservationRepository reservationRepository, SeatingOrganizer seatingOrganizer, ReservationsAssembler reservationsAssembler) {
        this.reservationStrategyFactory = reservationStrategyFactory;
        this.reservationFactory = reservationFactory;
        this.reservationRepository = reservationRepository;
        this.configRepository = configRepository;
        this.seatingOrganizer = seatingOrganizer;
        this.reservationsAssembler = reservationsAssembler;
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

    public List<ReservationDTO> getReservations() {
        List<Reservation> reservations = this.reservationRepository.getReservations();
        return this.reservationsAssembler.assembleAndSortReservation(reservations);
    }
    
}
