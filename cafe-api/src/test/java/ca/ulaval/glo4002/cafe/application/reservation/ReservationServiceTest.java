package ca.ulaval.glo4002.cafe.application.reservation;

import ca.ulaval.glo4002.cafe.domain.config.Config;
import ca.ulaval.glo4002.cafe.domain.config.ConfigRepository;
import ca.ulaval.glo4002.cafe.domain.reservation.*;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy.GroupReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy.ReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy.ReservationStrategyFactory;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import ca.ulaval.glo4002.cafe.domain.seating.SeatingOrganizer;
import ca.ulaval.glo4002.cafe.ui.rest.assemblers.config.ReservationsAssembler;
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
    private static ReservationRepository reservationRepository;
    private static ReservationStrategy groupReservationStrategy;
    private static SeatingOrganizer seatingOrganizer;
    private static ReservationFactory reservationFactory;
    private ReservationsAssembler reservationsAssembler;

    @BeforeEach
    public void setup() {
        ReservationStrategyFactory reservationStrategyFactory = mock(ReservationStrategyFactory.class);
        reservationRepository = mock(ReservationRepository.class);
        groupReservationStrategy = mock(ReservationStrategy.class);
        seatingOrganizer = mock(SeatingOrganizer.class);
        reservationFactory = mock(ReservationFactory.class);
        ConfigRepository configRepository = mock(ConfigRepository.class);
        Config config = mock(Config.class);
        when(config.getReservationMethod()).thenReturn(GroupReservationStrategy.Default);
        when(configRepository.findConfig()).thenReturn(config);
        when(reservationStrategyFactory.createReservationStrategy(GroupReservationStrategy.Default)).thenReturn(groupReservationStrategy);
        reservationsAssembler = mock(ReservationsAssembler.class);
        reservationService = new ReservationService(configRepository, reservationStrategyFactory, reservationFactory, reservationRepository, seatingOrganizer, reservationsAssembler);
    }

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

        verify(reservationRepository).getReservations();
    }

    @Test
    public void whenRequestingReservations_thenReservationsAreSortedByGroupNameAndAssemble() {
        List<Reservation> SOME_RESERVATIONS = List.of(mock(Reservation.class));
        when(reservationRepository.getReservations()).thenReturn(SOME_RESERVATIONS);
        reservationService.getReservations();

        verify(reservationsAssembler).assembleAndSortReservation(SOME_RESERVATIONS);
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
