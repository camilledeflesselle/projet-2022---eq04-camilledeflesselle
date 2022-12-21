package ca.ulaval.glo4002.cafe.application.seating;

import ca.ulaval.glo4002.cafe.domain.config.Config;
import ca.ulaval.glo4002.cafe.domain.config.IConfigRepository;
import ca.ulaval.glo4002.cafe.domain.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.reservation.DuplicateGroupNameException;
import ca.ulaval.glo4002.cafe.domain.reservation.Group;
import ca.ulaval.glo4002.cafe.domain.reservation.IReservationRepository;
import ca.ulaval.glo4002.cafe.domain.reservation.NoReservationsFoundException;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationFactory;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy.GroupReservationMethod;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy.IGroupReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy.ReservationStrategyFactory;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import ca.ulaval.glo4002.cafe.domain.seating.SeatingOrganizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ReservationServiceTest {
    private static final String A_GROUP_NAME = "name";
    private static final int A_GROUP_SIZE = 2;

    private static ReservationService reservationService;
    private static ReservationStrategyFactory reservationStrategyFactory;
    private static IReservationRepository reservationRepository;
    private static IGroupReservationStrategy groupReservationStrategy;
    private static SeatingOrganizer seatingOrganizer;
    private static ReservationFactory reservationFactory;
    private static List<Cube> cubes;

    @BeforeEach
    public void setup() {
        reservationStrategyFactory = mock(ReservationStrategyFactory.class);
        reservationRepository = mock(IReservationRepository.class);
        groupReservationStrategy = mock(IGroupReservationStrategy.class);
        seatingOrganizer = mock(SeatingOrganizer.class);
        reservationFactory = mock(ReservationFactory.class);
        cubes = new ArrayList<>(List.of(mock(Cube.class)));
        IConfigRepository configRepository = mock(IConfigRepository.class);
        Config config = mock(Config.class);
        when(config.getReservationMethod()).thenReturn(GroupReservationMethod.DEFAULT);
        when(configRepository.findConfig()).thenReturn(config);
        when(reservationStrategyFactory.createReservationStrategy(GroupReservationMethod.DEFAULT)).thenReturn(groupReservationStrategy);
        reservationService = new ReservationService(configRepository, reservationStrategyFactory, reservationFactory, reservationRepository, seatingOrganizer);
    }

    /*
    @Test
    public void whenUpdateConfig_thenGroupReservationStrategyIsCreatedFromNewReservationMethod() {
        wh/en    (reservat   ionStrategyFactory.createReservationStrategy(GroupReservationMethod.DEFAULT)).thenReturn(groupReservationStrategy);

        seatingService.updateConfig(GroupReservationMethod.DEFAULT);

        assertEquals(groupReservationStrategy, seatingService.getGroupReservationStrategy());
    }

    @Test
    public void whenUpdateConfig_thenSeatingOrganizerIsCreatedFromCubesAndReservationStrategy() {
        when(seatingOrganizerFactory.createSeatingOrganizer(any())).thenReturn(seatingOrganizer);

        seatingService.updateConfig(GroupReservationMethod.DEFAULT);

        assertEquals(seatingOrganizer, seatingService.getSeatingOrganizer());
    }

    @Test
    public void givenCustomerWithoutGroup_whenSearchingSeat_thenGetFirstFreeSeatFromSeatingOrganizer() {
        Customer customer = givenCustomerWithoutGroup();

        reservationService.getSeatForCustomer(customer);

        verify(seatingOrganizer).getFirstFreeSeat();
    }

    @Test
    public void givenCustomerWithGroup_whenSearchingSeat_thenSearchReservationInRepositoryWithGroupName() {
        Customer customer = givenCustomerWithGroup();
        Reservation reservation = mock(Reservation.class);
        when(reservationRepository.findReservationByGroupName(A_GROUP_NAME)).thenReturn(reservation);

        reservationService.getSeatForCustomer(customer);

        verify(reservationRepository).findReservationByGroupName(customer.getGroupName());
    }

    @Test
    public void givenCustomerWithGroup_whenSearchingSeat_thenSearchSeatFromSeatingOrganizerWithReservationSeatId() {
        Customer customer = givenCustomerWithGroup();
        Reservation reservation = mock(Reservation.class);
        SeatId seatId = mock(SeatId.class);
        when(reservationRepository.findReservationByGroupName(any())).thenReturn(reservation);
        when(reservation.popFirstReservedSeatId()).thenReturn(seatId);

        reservationService.getSeatForCustomer(customer);

        verify(seatingOrganizer).findSeatBySeatId(seatId);
    }*/

    @Test
    public void givenGroupWithNoReservation_whenAddingReservation_thenSeatingOrganizerReservesSeatsWithGroupNameAndGroupSize() {
        Group group = givenGroupWithoutReservation();

        reservationService.addReservation(group);

        verify(seatingOrganizer).reserveSeats(group.getSize(), group.getName(), groupReservationStrategy);
    }

    @Test
    public void givenGroupWithNoReservation_whenAddingReservation_thenReservationIsCreatedByFactory() {
        Group group = givenGroupWithoutReservation();
        List<SeatId> seatIds = new ArrayList<>(List.of(mock(SeatId.class)));
        when(seatingOrganizer.reserveSeats(group.getSize(), group.getName(), groupReservationStrategy)).thenReturn(seatIds);

        reservationService.addReservation(group);

        verify(reservationFactory).createReservation(group, seatIds);
    }

    @Test
    public void givenGroupWithNoReservation_whenAddingReservation_thenCreatedReservationIsSavedInRepository() {
        Group group = givenGroupWithoutReservation();
        Reservation reservation = mock(Reservation.class);
        List<SeatId> seatIds = new ArrayList<>(List.of(mock(SeatId.class)));
        when(seatingOrganizer.reserveSeats(group.getSize(), group.getName(), groupReservationStrategy)).thenReturn(seatIds);
        when(reservationFactory.createReservation(group, seatIds)).thenReturn(reservation);

        reservationService.addReservation(group);

        verify(reservationRepository).saveReservation(reservation);
    }

    @Test
    public void givenGroupWithReservationAlready_whenAddingReservation_thenThrowsDuplicateGroupNameException() {
        Group group = givenGroupWithReservationAlready();

        assertThrows(DuplicateGroupNameException.class, () -> reservationService.addReservation(group));
    }

    @Test
    public void whenRequestingReservations_thenReservationsAreSearchedFromRepository() {
        reservationService.getReservations();

        verify(reservationRepository).findReservations();
    }
    /*
    @Test
    public void whenNoReservationFound_thenRaiseNoReservationsFoundException() {
        when(reservationRepository.findReservationByGroupName(A_GROUP_NAME)).thenReturn(null);

        assertThrows(NoReservationsFoundException.class, () -> reservationService.getReservationByGroupName(A_GROUP_NAME));

        verify(reservationRepository).findReservationByGroupName(A_GROUP_NAME);

    }

    @Test
    public void whenRemovingReservationFromGroupName_thenRepositoryRemovesReservationFromGroupName() {
        reservationService.removeReservationByGroupName(A_GROUP_NAME);

        verify(reservationRepository).removeReservationByGroupName(A_GROUP_NAME);
    }

    @Test
    public void whenRequestingSeatFromSeatId_thenSeatingOrganizerSearchesSeatFromSeatId() {
        SeatId seatId = mock(SeatId.class);

        reservationService.getSeatById(seatId);

        verify(seatingOrganizer).findSeatBySeatId(seatId);
    }*/


    private Customer givenCustomerWithGroup() {
        Customer customer = mock(Customer.class);
        when(customer.hasGroup()).thenReturn(true);
        when(customer.getGroupName()).thenReturn(A_GROUP_NAME);

        return customer;
    }

    private Customer givenCustomerWithoutGroup() {
        Customer customer = mock(Customer.class);
        when(customer.hasGroup()).thenReturn(false);

        return customer;
    }

    private Group givenGroupWithoutReservation() {
        Group group = mock(Group.class);
        when(group.getName()).thenReturn(A_GROUP_NAME);
        when(group.getSize()).thenReturn(A_GROUP_SIZE);
        when(reservationRepository.doesReservationExistsForGroup(A_GROUP_NAME)).thenReturn(false);

        return group;
    }

    private Group givenGroupWithReservationAlready() {
        Group group = mock(Group.class);
        when(group.getName()).thenReturn(A_GROUP_NAME);
        when(reservationRepository.doesReservationExistsForGroup(A_GROUP_NAME)).thenReturn(true);

        return group;
    }
}
