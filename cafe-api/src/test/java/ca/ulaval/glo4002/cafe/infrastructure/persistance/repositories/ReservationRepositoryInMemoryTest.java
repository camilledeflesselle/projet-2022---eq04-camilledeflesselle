package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories;

import ca.ulaval.glo4002.cafe.domain.reservation.Group;
import ca.ulaval.glo4002.cafe.domain.reservation.NoReservationsFoundException;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationRepositoryInMemoryTest {
    private static final Integer A_GROUP_SIZE = 2;
    private static final String A_GROUP_NAME = "Team 4";
    private static final String ANOTHER_GROUP_NAME = "Team 5";
    private static final List<SeatId> SOME_SEAT_IDS = new ArrayList<>();
    private static final Group A_GROUP = new Group(A_GROUP_NAME, A_GROUP_SIZE);
    private static final Group ANOTHER_GROUP = new Group(ANOTHER_GROUP_NAME, A_GROUP_SIZE);
    private static final Reservation A_RESERVATION = new Reservation(A_GROUP, SOME_SEAT_IDS);
    private static final Reservation ANOTHER_RESERVATION = new Reservation(ANOTHER_GROUP, SOME_SEAT_IDS);
    private ReservationRepositoryInMemory reservationRepositoryInMemory;

    @BeforeEach
    public void setup() {
        reservationRepositoryInMemory = new ReservationRepositoryInMemory();
    }

    @Test
    public void whenInitialized_thenIsEmpty() {
        assertEquals(0, reservationRepositoryInMemory.getAmount());
    }

    @Test
    public void givenAnEmptyReservationList_whenSearchingForReservation_thenReservationDoesntExist() {
        assertFalse(reservationRepositoryInMemory.doesReservationExistsForGroup(A_GROUP_NAME));
    }

    @Test
    public void givenEmptyReservationRepository_whenSavingAReservation_thenRepositoryHasOneElement() {
        reservationRepositoryInMemory.saveReservation(A_RESERVATION);

        assertEquals(1, reservationRepositoryInMemory.getAmount());
    }

    @Test
    public void whenSavingAReservation_thenReservationExists() {
        reservationRepositoryInMemory.saveReservation(A_RESERVATION);

        assertTrue(reservationRepositoryInMemory.doesReservationExistsForGroup(A_GROUP_NAME));
    }

    @Test
    public void givenGroupWithNoReservation_whenSearchingReservationByGroupName_thenNoReservationIsFound() {
        assertThrows(NoReservationsFoundException.class, () -> {
                    reservationRepositoryInMemory.findReservationByGroupName(A_GROUP_NAME);
                }
        );
    }

    @Test
    public void givenGroupThatMakesAReservation_whenSearchingReservationByGroupName_thenReturnsTheCorrectReservation() {
        reservationRepositoryInMemory.saveReservation(A_RESERVATION);

        Reservation reservation = reservationRepositoryInMemory.findReservationByGroupName(A_GROUP_NAME);

        assertEquals(A_RESERVATION, reservation);
    }

    @Test
    public void givenRepositoryWithMultipleReservations_whenSearchingAllReservations_thenReturnAllReservationsSorted() {
        reservationRepositoryInMemory.saveReservation(A_RESERVATION);
        reservationRepositoryInMemory.saveReservation(ANOTHER_RESERVATION);

        List<Reservation> returnedReservations = reservationRepositoryInMemory.findReservations();
        List<Reservation> expectedReservations = new ArrayList<>(List.of(A_RESERVATION, ANOTHER_RESERVATION));

        assertEquals(expectedReservations, returnedReservations);
    }

    @Test
    public void whenRemoveReservation_thenRepositoryDoesNotContainsReservation() {
        reservationRepositoryInMemory.saveReservation(A_RESERVATION);

        reservationRepositoryInMemory.removeReservationByGroupName(A_GROUP_NAME);

        assertFalse(reservationRepositoryInMemory.doesReservationExistsForGroup(A_GROUP_NAME));
    }

    @Test
    public void whenRemoveReservationNotExisting_thenNoReservationFoundException() {
        assertThrows(NoReservationsFoundException.class, () -> {
                    reservationRepositoryInMemory.removeReservationByGroupName(A_GROUP_NAME);
                }
        );
    }

    @Test
    public void givenRepositoryWithMultipleReservations_whenDeleteAll_thenRepositoryIsEmpty() {
        reservationRepositoryInMemory.saveReservation(A_RESERVATION);
        reservationRepositoryInMemory.saveReservation(ANOTHER_RESERVATION);

        reservationRepositoryInMemory.deleteAll();

        assertEquals(0, reservationRepositoryInMemory.getAmount());
    }
}
