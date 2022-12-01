package ca.ulaval.glo4002.cafe.application.checkOut;

import ca.ulaval.glo4002.cafe.application.bill.BillService;
import ca.ulaval.glo4002.cafe.application.customer.CustomerService;
import ca.ulaval.glo4002.cafe.application.seating.SeatingService;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;

public class CheckOutService {
    private final CustomerService customerService;
    private final SeatingService seatingService;
    private final BillService billService;

    public CheckOutService(CustomerService customerService, SeatingService seatingService, BillService billService) {
        this.customerService = customerService;
        this.billService = billService;
        this.seatingService = seatingService;
    }

    public void checkoutCustomer(CustomerId customerId) {
        Customer customer = this.customerService.findCustomer(customerId);
        this.unassignSeatToCustomer(customer);

        if (customer.hasGroup()) {
            this.createBillForGroup(customerId);
            this.removeReservationIfEmpty(customer.getGroupName());
        } else {
            this.createBill(customerId);
        }
    }

    private void unassignSeatToCustomer(Customer customer) {
        if (customer.hasGroup()) {
            Reservation reservation = this.seatingService.getReservationByGroupName(customer.getGroupName());
            reservation.checkoutCustomer(customer);
        }
        Seat seat = this.seatingService.getSeatById(customer.getSeatId());
        seat.unassign();
        customer.unsetSeatId();
    }

    private void removeReservationIfEmpty(String groupName) {
        Reservation reservation = this.seatingService.getReservationByGroupName(groupName);
        if (!reservation.isEmpty()) return;

        for (SeatId seatId : reservation.getLockedSeatsId()) {
            this.seatingService.getSeatById(seatId).unassign();
        }

        this.seatingService.removeReservationByGroupName(groupName);
    }

    public void createBill(CustomerId customerId) {
        Order order = this.customerService.findOrCreateEmptyOrder(customerId);
        this.billService.processBillForCustomer(customerId, order);
    }

    public void createBillForGroup(CustomerId customerId) {
        Order order = this.customerService.findOrCreateEmptyOrder(customerId);
        this.billService.processBillForGroup(customerId, order);
    }
}
