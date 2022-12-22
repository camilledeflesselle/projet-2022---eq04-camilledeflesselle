package ca.ulaval.glo4002.cafe.domain.seating;

import ca.ulaval.glo4002.cafe.domain.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.cube.CubesListFactory;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.reservation.Group;
import ca.ulaval.glo4002.cafe.domain.reservation.IReservationRepository;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy.IGroupReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.seat.NoSeatAvailableException;
import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class SeatingOrganizerTest {
    private static final String A_GROUP_NAME = "Family S";
    private static final int A_GROUP_SIZE = 2;
    private static final List<String> A_CUBE_NAME = new ArrayList<>(List.of("Bob"));
    private static final SeatId A_SEAT_ID = new SeatId(1);
    private static final SeatId ANOTHER_SEAT_ID = new SeatId(2);
    private static final SeatId A_SEAT_ID_NOT_IN_CUBES = new SeatId(100);
    private final Customer customerWithoutGroup = givenCustomerWithoutGroup();

    private final Customer customerWithGroup = givenCustomerWithGroup();

    private Group group;
    private CubesListFactory cubesListFactory;
    private IGroupReservationStrategy groupReservationStrategyMock;
    private IReservationRepository reservationRepository;

    @BeforeEach
    public void setup() {
        group = new Group(A_GROUP_NAME, A_GROUP_SIZE);
        cubesListFactory = new CubesListFactory();
        reservationRepository = mock(IReservationRepository.class);
        groupReservationStrategyMock = mock(IGroupReservationStrategy.class);
    }

    @Test
    public void givenCubesWithoutFreeSeats_whenFindSeat_thenThrowsNoSeatAvailableException() {
        List<Cube> cubes = givenCubesWithoutFreeSeats();
        SeatingOrganizer seatingOrganizer = new SeatingOrganizer(cubes);

        assertThrows(NoSeatAvailableException.class, () -> seatingOrganizer.findSeat(customerWithoutGroup, reservationRepository));
    }

    @Test
    public void givenOneCubeWithAllSeatsFreeAndNoReservation_whenFindSeat_thenReturnsTheFirstSeat()  {
        List<Cube> cubes = cubesListFactory.create(A_CUBE_NAME, 2);
        SeatingOrganizer seatingOrganizer = new SeatingOrganizer(cubes);

        SeatId expectedSeatId = new SeatId(1);
        Seat returnedSeat = seatingOrganizer.findSeat(customerWithoutGroup, reservationRepository);

        assertEquals(expectedSeatId, returnedSeat.getId());
    }

    @Test
    public void givenOneCubeWithFirstSeatOccupied_whenFindSeat_thenReturnsTheSecondSeat() {
        List<Cube> cubes = givenCubeWithFirstSeatOccupied();
        SeatingOrganizer seatingOrganizer = new SeatingOrganizer(cubes);

        SeatId expectedSeatId = new SeatId(2);
        Seat returnedSeat = seatingOrganizer.findSeat(customerWithoutGroup, reservationRepository);

        assertEquals(expectedSeatId, returnedSeat.getId());
    }


    @Test
    public void givenCubesWithAvailableSeats_whenTryToReserveMoreSeatsThanAvailable_thenThrowsInsufficientSeatsException() {
        List<Cube> cubes = cubesListFactory.create(A_CUBE_NAME, 2);
        SeatingOrganizer seatingOrganizer = new SeatingOrganizer(cubes);

        assertThrows(NoSeatAvailableException.class, () -> seatingOrganizer.reserveSeats(3, A_GROUP_NAME, groupReservationStrategyMock)
        );
    }

    @Test
    public void givenOneCubeWithEnoughFreeSeats_whenTryToReserveSeats_thenCallReservationStrategy() {
        List<Cube> cubes = cubesListFactory.create(A_CUBE_NAME, 2);
        SeatingOrganizer seatingOrganizer = new SeatingOrganizer(cubes);

        seatingOrganizer.reserveSeats(group.getSize(), group.getName(), groupReservationStrategyMock);

        verify(groupReservationStrategyMock).getReservationSeats(cubes, group.getSize());
    }

    @Test
    public void givenACustomerWithSeatNotInCubes_whenRemoveCustomerFromSeating_thenRaiseNotFoundException() {
        givenSeatForCustomer(customerWithoutGroup, A_SEAT_ID_NOT_IN_CUBES);
        List<Cube> cubes = cubesListFactory.create(A_CUBE_NAME, 2);
        SeatingOrganizer seatingOrganizer = new SeatingOrganizer(cubes);

        assertThrows(NotFoundException.class, () -> seatingOrganizer.removeCustomerFromSeating(customerWithoutGroup, reservationRepository));
    }


    @Test
    public void givenCustomerWithoutGroup_whenRemovingFromSeat_thenDoesNotSearchForReservation() {
        givenSeatForCustomer(customerWithoutGroup, A_SEAT_ID);
        List<Cube> cubes = cubesListFactory.create(A_CUBE_NAME, 2);
        SeatingOrganizer seatingOrganizer = new SeatingOrganizer(cubes);

        seatingOrganizer.removeCustomerFromSeating(customerWithoutGroup, reservationRepository);

        verify(reservationRepository, never()).findReservationByGroupName(anyString());
    }

    @Test
    public void givenCustomerWithGroup_whenRemovingFromSeat_thenSearchForReservationWithGroupNameAndCheckoutOfReservation() {
        givenSeatForCustomer(customerWithGroup, A_SEAT_ID);
        Reservation reservation = givenReservation();
        List<Cube> cubes = cubesListFactory.create(A_CUBE_NAME, 2);
        SeatingOrganizer seatingOrganizer = new SeatingOrganizer(cubes);

        seatingOrganizer.removeCustomerFromSeating(customerWithGroup, reservationRepository);

        verify(reservationRepository, times(2)).findReservationByGroupName(A_GROUP_NAME);
        verify(reservation).checkoutCustomer(customerWithGroup);
    }


    @Test
    public void givenCustomerWithoutGroup_whenRemovingFromSeat_thenCustomerIsUnassignedFromSeat() {
        givenSeatForCustomer(customerWithoutGroup, A_SEAT_ID);
        List<Cube> cubes = cubesListFactory.create(A_CUBE_NAME, 2);
        SeatingOrganizer seatingOrganizer = new SeatingOrganizer(cubes);
        Seat seat = givenSeat(A_SEAT_ID, cubes.get(0));

        seatingOrganizer.removeCustomerFromSeating(customerWithoutGroup, reservationRepository);

        verify(seat).unassign();
        verify(customerWithoutGroup).unsetSeatId();
    }


    @Test
    public void givenCustomerWithoutGroup_whenCheckOut_thenDoesNotRemoveReservation() {
        givenSeatForCustomer(customerWithoutGroup, A_SEAT_ID);
        List<Cube> cubes = cubesListFactory.create(A_CUBE_NAME, 2);
        SeatingOrganizer seatingOrganizer = new SeatingOrganizer(cubes);

        seatingOrganizer.removeCustomerFromSeating(customerWithoutGroup, reservationRepository);

        verify(reservationRepository, never()).findReservationByGroupName(any());
        verify(reservationRepository, never()).removeReservationByGroupName(any());
    }


    @Test
    public void givenCustomerWithGroup_whenCheckOutAndReservationNotEmpty_thenReservationIsNotRemoved() {
        Reservation reservation = givenReservation();
        when(reservation.isEmpty()).thenReturn(false);
        List<Cube> cubes = cubesListFactory.create(A_CUBE_NAME, 2);
        SeatingOrganizer seatingOrganizer = new SeatingOrganizer(cubes);
        givenSeat(A_SEAT_ID, cubes.get(0));

        seatingOrganizer.removeCustomerFromSeating(customerWithGroup, reservationRepository);

        verify(reservationRepository, never()).removeReservationByGroupName(any());
    }

    @Test
    public void givenCustomerWithGroup_whenCheckOutAndReservationEmpty_thenReservationIsRemoved() {
        Reservation reservation = givenReservation();
        when(reservation.isEmpty()).thenReturn(true);
        List<Cube> cubes = cubesListFactory.create(A_CUBE_NAME, 2);
        SeatingOrganizer seatingOrganizer = new SeatingOrganizer(cubes);

        seatingOrganizer.removeCustomerFromSeating(customerWithGroup, reservationRepository);

        verify(reservationRepository).removeReservationByGroupName(A_GROUP_NAME);
    }

    @Test
    public void givenCustomerWithGroup_whenCheckOutAndReservationEmpty_thenReservationLockedSeatsAreUnassigned() {
        Reservation reservation = givenReservation();
        when(reservation.isEmpty()).thenReturn(true);
        when(reservation.getLockedSeatsId()).thenReturn(new ArrayList<>(List.of(ANOTHER_SEAT_ID)));
        List<Cube> cubes = cubesListFactory.create(A_CUBE_NAME, 2);
        SeatingOrganizer seatingOrganizer = new SeatingOrganizer(cubes);
        Seat seat = givenSeat(A_SEAT_ID, cubes.get(0));
        Seat lockedSeat = givenSeat(ANOTHER_SEAT_ID, cubes.get(0));

        seatingOrganizer.removeCustomerFromSeating(customerWithGroup, reservationRepository);

        verify(lockedSeat).unassign();
    }




    private List<Cube> givenCubesWithoutFreeSeats() {
        List<Cube> cubes = cubesListFactory.create(A_CUBE_NAME, 1);
        cubes.get(0).getFirstFreeSeat().assign();

        return cubes;
    }

    private List<Cube> givenCubeWithFirstSeatOccupied() {
        List<Cube> cubes = cubesListFactory.create(A_CUBE_NAME, 2);
        cubes.get(0).getFirstFreeSeat().assign();

        return cubes;
    }

    private Customer givenCustomerWithGroup() {
        Customer customer = mock(Customer.class);
        when(customer.hasGroup()).thenReturn(true);
        when(customer.getGroupName()).thenReturn(A_GROUP_NAME);

        return customer;
    }

    private Customer givenCustomerWithoutGroup() {
        Customer customer = mock(Customer.class);
        when(customer.hasGroup()).thenReturn(false);
        when(customer.getGroupName()).thenReturn(null);

        return customer;
    }

    private Reservation givenReservation() {
        Reservation reservation = mock(Reservation.class);
        when(reservationRepository.findReservationByGroupName(A_GROUP_NAME)).thenReturn(reservation);

        return reservation;
    }

    private Seat givenSeat(SeatId seatId, Cube cube) {
        Seat seat = new Seat(seatId.getId());
        when(seat.getId()).thenReturn(seatId);
        when(cube.findSeatById(seatId)).thenReturn(seat);

        return seat;
    }

    private void givenSeatForCustomer(Customer customer, SeatId seatId) {
        when(customer.getSeatId()).thenReturn(seatId);
    }


}
