package ca.ulaval.glo4002.cafe.application.seating;

import ca.ulaval.glo4002.cafe.domain.config.IConfigRepository;
import ca.ulaval.glo4002.cafe.domain.cube.ICubeRepository;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.reservation.*;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy.ReservationStrategyFactory;
import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import ca.ulaval.glo4002.cafe.domain.seating.SeatingOrganizer;
import ca.ulaval.glo4002.cafe.domain.seating.SeatingOrganizerFactory;

import java.util.List;

public class SeatingService {
    private final ReservationStrategyFactory reservationStrategyFactory;
    private final SeatingOrganizerFactory seatingOrganizerFactory;
    private final IReservationRepository reservationRepository;
    private final ReservationFactory reservationFactory;
    private final IConfigRepository configRepository;
    private SeatingOrganizer seatingOrganizer;

    public SeatingService(IConfigRepository configRepository, ReservationStrategyFactory reservationStrategyFactory, ReservationFactory reservationFactory, SeatingOrganizerFactory seatingOrganizerFactory, ICubeRepository cubeRepository, IReservationRepository reservationRepository) {
        this.reservationStrategyFactory = reservationStrategyFactory;
        this.reservationFactory = reservationFactory;
        this.seatingOrganizerFactory = seatingOrganizerFactory;
        this.reservationRepository = reservationRepository;
        this.configRepository = configRepository;
        this.seatingOrganizer = this.seatingOrganizerFactory.createSeatingOrganizer(cubeRepository.findAll());
    }

    public Seat getSeatForCustomer(Customer customer) {
        if (!customer.hasGroup()) {
            return this.seatingOrganizer.getFirstFreeSeat();
        }
        Reservation reservation = this.getReservationByGroupName(customer.getGroupName());

        return this.seatingOrganizer.findSeatBySeatId(reservation.popFirstReservedSeatId());
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

    public Reservation getReservationByGroupName(String groupName) {
        Reservation reservation = this.reservationRepository.findReservationByGroupName(groupName);
        if (reservation == null) {
            throw new NoReservationsFoundException();
        }
        return reservation;
    }

    public void reset() {
        this.reservationRepository.deleteAll();
    }

    public void removeReservationByGroupName(String groupName) {
        this.reservationRepository.removeReservationByGroupName(groupName);
    }

    public Seat getSeatById(SeatId seatId) {
        return this.seatingOrganizer.findSeatBySeatId(seatId);
    }
}
