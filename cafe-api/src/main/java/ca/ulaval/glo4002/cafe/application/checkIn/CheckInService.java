package ca.ulaval.glo4002.cafe.application.checkIn;

import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.customer.DuplicateCustomerException;
import ca.ulaval.glo4002.cafe.domain.customer.ICustomerRepository;
import ca.ulaval.glo4002.cafe.domain.order.IOrderRepository;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.order.OrdersFactory;
import ca.ulaval.glo4002.cafe.domain.reservation.IReservationRepository;
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
        Seat seat = this.seatingOrganizer.findSeat(customer, this.reservationRepository);
        seat.assign();
        customer.setSeatId(seat.getId());
        this.customerRepository.saveCustomer(customer);
        this.initOrder(customer.getId());
    }

    private void initOrder(CustomerId customerId) {
        Order order = this.ordersFactory.create(new ArrayList<>());
        this.ordersRepository.saveOrdersByCustomerId(customerId, order);
    }

}
