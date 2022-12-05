package ca.ulaval.glo4002.cafe.application.checkIn;

import ca.ulaval.glo4002.cafe.application.customer.CustomerService;
import ca.ulaval.glo4002.cafe.application.seating.SeatingService;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.DuplicateCustomerException;
import ca.ulaval.glo4002.cafe.domain.seat.Seat;

public class CheckInService {
    private final CustomerService customerService;
    private final SeatingService seatingService;

    public CheckInService(CustomerService customerService, SeatingService seatingService) {
        this.customerService = customerService;
        this.seatingService = seatingService;
    }

    public void checkIn(Customer customer) {
        if (this.customerService.hasAlreadyVisited(customer)) {
            throw new DuplicateCustomerException();
        }
        Seat seat = this.seatingService.getSeatForCustomer(customer);
        seat.assign();
        customer.setSeatId(seat.getId());
        this.customerService.initOrder(customer);
        this.customerService.saveCustomer(customer);
    }
}
