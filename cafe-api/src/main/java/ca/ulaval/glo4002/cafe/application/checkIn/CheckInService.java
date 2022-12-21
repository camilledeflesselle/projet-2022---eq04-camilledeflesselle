package ca.ulaval.glo4002.cafe.application.checkIn;

import ca.ulaval.glo4002.cafe.application.seating.SeatingService;
import ca.ulaval.glo4002.cafe.domain.customer.*;
import ca.ulaval.glo4002.cafe.domain.order.IOrderRepository;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.order.OrdersFactory;
import ca.ulaval.glo4002.cafe.domain.seat.Seat;

import java.util.ArrayList;

public class CheckInService {
    private final ICustomerRepository customerRepository;
    private final SeatingService seatingService;
    private OrdersFactory ordersFactory;
    private IOrderRepository ordersRepository;

    public CheckInService(ICustomerRepository customerRepository, SeatingService seatingService, OrdersFactory ordersFactory, IOrderRepository ordersRepository) {
        this.customerRepository = customerRepository;
        this.seatingService = seatingService;
        this.ordersFactory = ordersFactory;
        this.ordersRepository = ordersRepository;
    }

    public void checkIn(Customer customer) {
        if (!(this.customerRepository.findCustomerByCustomerId(customer.getId()) == null)) {
            throw new DuplicateCustomerException();
        }
        Seat seat = this.seatingService.getSeatForCustomer(customer);
        seat.assign();
        customer.setSeatId(seat.getId());
        this.initOrder(customer.getId());
        this.customerRepository.saveCustomer(customer);
    }

    public void initOrder(CustomerId customerId) {
        Order order = this.ordersFactory.create(new ArrayList<>());
        this.ordersRepository.saveOrdersByCustomerId(customerId, order);
    }

}
