package ca.ulaval.glo4002.cafe.application.checkOut;

import ca.ulaval.glo4002.cafe.application.bill.BillFactory;
import ca.ulaval.glo4002.cafe.domain.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.bill.IBillRepository;
import ca.ulaval.glo4002.cafe.domain.bill.TipRate;
import ca.ulaval.glo4002.cafe.domain.config.IConfigRepository;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerDoesNotExistsException;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.customer.ICustomerRepository;
import ca.ulaval.glo4002.cafe.domain.order.IOrderRepository;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.reservation.IReservationRepository;
import ca.ulaval.glo4002.cafe.domain.reservation.NoReservationsFoundException;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import ca.ulaval.glo4002.cafe.domain.seating.SeatingOrganizer;

public class CheckOutService {
    private final ICustomerRepository customerRepository;
    private IOrderRepository orderRepository;
    private IConfigRepository configRepository;
    private BillFactory billFactory;
    private IBillRepository billRepository;
    private SeatingOrganizer seatingOrganizer;
    private IReservationRepository reservationRepository;

    public CheckOutService(ICustomerRepository customerRepository, IOrderRepository orderRepository, IConfigRepository configRepository, BillFactory billFactory, IBillRepository billRepository, SeatingOrganizer seatingOrganizer, IReservationRepository reservationRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.configRepository = configRepository;
        this.billFactory = billFactory;
        this.billRepository = billRepository;
        this.seatingOrganizer = seatingOrganizer;
        this.reservationRepository = reservationRepository;
    }

    public void checkoutCustomer(CustomerId customerId) {
        Customer customer = this.customerRepository.findCustomerByCustomerId(customerId);
        if (customer == null) {
            throw new CustomerDoesNotExistsException();
        }
        this.unassignSeatToCustomer(customer);

        this.createBill(customer);

        if (customer.hasGroup()) {
            this.removeReservationIfEmpty(customer.getGroupName());
        }
    }

    private void unassignSeatToCustomer(Customer customer) {
        if (customer.hasGroup()) {
            Reservation reservation = this.reservationRepository.findReservationByGroupName(customer.getGroupName());
            reservation.checkoutCustomer(customer);
        }
        Seat seat = this.seatingOrganizer.findSeatBySeatId(customer.getSeatId());
        seat.unassign();
        customer.unsetSeatId();
    }

    private void removeReservationIfEmpty(String groupName) {
        Reservation reservation = this.reservationRepository.findReservationByGroupName(groupName);
        if (!reservation.isEmpty()) return;

        for (SeatId seatId : reservation.getLockedSeatsId()) {
            this.seatingOrganizer.findSeatBySeatId(seatId).unassign();
        }

        this.reservationRepository.removeReservationByGroupName(groupName);
    }

    private void createBill(Customer customer) {
        Order order = this.orderRepository.findOrderByCustomerId(customer.getId());
        this.processBill(customer, order);
    }

    private void processBill(Customer customer, Order order) {
        TipRate groupTipRate;
        if (customer.hasGroup()) {
            groupTipRate = this.configRepository.findConfig().getGroupTipRate();
        } else {
            groupTipRate = null;
        }
        Bill bill = this.billFactory.createBill(order, this.configRepository.findConfig().getTaxRate(), groupTipRate);
        this.billRepository.saveBillByCustomerId(customer.getId(), bill);
    }
}
