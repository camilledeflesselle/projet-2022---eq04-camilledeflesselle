package ca.ulaval.glo4002.cafe.application.checkIn;

import ca.ulaval.glo4002.cafe.domain.customer.*;
import ca.ulaval.glo4002.cafe.domain.order.IOrderRepository;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.order.OrdersFactory;
import ca.ulaval.glo4002.cafe.domain.reservation.IReservationRepository;
import ca.ulaval.glo4002.cafe.domain.reservation.NoReservationsFoundException;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.seating.SeatingOrganizer;

import java.util.ArrayList;

public class CheckInService {
    private final ICustomerRepository customerRepository;
    private final SeatingOrganizer seatingOrganizer;
    private OrdersFactory ordersFactory;
    private IOrderRepository ordersRepository;
    private IReservationRepository reservationRepository;

    public CheckInService(ICustomerRepository customerRepository, SeatingOrganizer seatingOrganizer, OrdersFactory ordersFactory, IOrderRepository ordersRepository, IReservationRepository reservationRepository) {
        this.customerRepository = customerRepository;
        this.seatingOrganizer = seatingOrganizer;
        this.ordersFactory = ordersFactory;
        this.ordersRepository = ordersRepository;
        this.reservationRepository = reservationRepository;
    }

    public void checkIn(Customer customer) {
        if (!(this.customerRepository.findCustomerByCustomerId(customer.getId()) == null)) {
            throw new DuplicateCustomerException();
        }
        Seat seat = this.getSeatForCustomer(customer);
        seat.assign();
        customer.setSeatId(seat.getId());
        this.initOrder(customer.getId());
        this.customerRepository.saveCustomer(customer);
    }

    public void initOrder(CustomerId customerId) {
        Order order = this.ordersFactory.create(new ArrayList<>());
        this.ordersRepository.saveOrdersByCustomerId(customerId, order);
    }

    public Seat getSeatForCustomer(Customer customer) {
        if (!customer.hasGroup()) {
            return this.seatingOrganizer.getFirstFreeSeat();
        }
        Reservation reservation = this.getReservationByGroupName(customer.getGroupName());

        return this.seatingOrganizer.findSeatBySeatId(reservation.popFirstReservedSeatId());
    }

    public Reservation getReservationByGroupName(String groupName) {
        Reservation reservation = this.reservationRepository.findReservationByGroupName(groupName);
        if (reservation == null) {
            throw new NoReservationsFoundException();
        }
        return reservation;
    }

}
