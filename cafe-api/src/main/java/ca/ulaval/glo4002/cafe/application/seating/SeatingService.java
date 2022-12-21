package ca.ulaval.glo4002.cafe.application.seating;

import ca.ulaval.glo4002.cafe.domain.config.IConfigRepository;
import ca.ulaval.glo4002.cafe.domain.cube.ICubeRepository;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.reservation.DuplicateGroupNameException;
import ca.ulaval.glo4002.cafe.domain.reservation.Group;
import ca.ulaval.glo4002.cafe.domain.reservation.IReservationRepository;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationFactory;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy.IGroupReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy.ReservationStrategyFactory;
import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import ca.ulaval.glo4002.cafe.domain.seating.SeatingOrganizer;
import ca.ulaval.glo4002.cafe.domain.seating.SeatingOrganizerFactory;

import java.util.List;

public class SeatingService {
    private final ReservationStrategyFactory reservationStrategyFactory;
    private final SeatingOrganizerFactory seatingOrganizerFactory;
    private final ICubeRepository cubeRepository;
    private final IReservationRepository reservationRepository;
    private final ReservationFactory reservationFactory;
    private final IConfigRepository configRepository;
    private IGroupReservationStrategy groupReservationStrategy;
    private SeatingOrganizer seatingOrganizer;

    public SeatingService(IConfigRepository configRepository, ReservationStrategyFactory reservationStrategyFactory, ReservationFactory reservationFactory, SeatingOrganizerFactory seatingOrganizerFactory, ICubeRepository cubeRepository, IReservationRepository reservationRepository) {
        this.reservationStrategyFactory = reservationStrategyFactory;
        this.reservationFactory = reservationFactory;
        this.seatingOrganizerFactory = seatingOrganizerFactory;
        this.cubeRepository = cubeRepository;
        this.reservationRepository = reservationRepository;
        this.configRepository = configRepository;
        this.groupReservationStrategy = this.reservationStrategyFactory.createReservationStrategy(configRepository.findConfig().getReservationMethod());
        this.seatingOrganizer = this.seatingOrganizerFactory.createSeatingOrganizer(cubeRepository.findAll());
    }

    public Seat getSeatForCustomer(Customer customer) {
        if (!customer.hasGroup()) {
            return this.seatingOrganizer.getFirstFreeSeat();
        }
        Reservation reservation = this.reservationRepository.findReservationByGroupName(customer.getGroupName());
        return this.seatingOrganizer.findSeatBySeatId(reservation.popFirstReservedSeatId());
    }

    public void addReservation(Group group) {
        if (this.reservationRepository.doesReservationExistsForGroup(group.getName())) {
            throw new DuplicateGroupNameException();
        }
        List<SeatId> reservedSeats = this.seatingOrganizer.reserveSeats(
                group.getSize(),
                group.getName(),
                this.groupReservationStrategy
        );
        Reservation reservation = this.reservationFactory.createReservation(group, reservedSeats);
        this.reservationRepository.saveReservation(reservation);
    }

    public List<Reservation> getReservations() {
        return this.reservationRepository.findReservations();
    }

    public Reservation getReservationByGroupName(String groupName) {
        return this.reservationRepository.findReservationByGroupName(groupName);
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

    public IGroupReservationStrategy getGroupReservationStrategy() {
        return this.groupReservationStrategy;
    }

    public SeatingOrganizer getSeatingOrganizer() {
        return this.seatingOrganizer;
    }
}
