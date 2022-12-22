package ca.ulaval.glo4002.cafe.application.checkOut;

import ca.ulaval.glo4002.cafe.domain.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.bill.BillFactory;
import ca.ulaval.glo4002.cafe.domain.bill.BillRepository;
import ca.ulaval.glo4002.cafe.domain.config.ConfigRepository;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerDoesNotExistException;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerRepository;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.order.OrderRepository;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationRepository;
import ca.ulaval.glo4002.cafe.domain.seating.SeatingOrganizer;

public class CheckOutService {
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final ConfigRepository configRepository;
    private final BillFactory billFactory;
    private final BillRepository billRepository;
    private final SeatingOrganizer seatingOrganizer;
    private final ReservationRepository reservationRepository;

    public CheckOutService(CustomerRepository customerRepository, OrderRepository orderRepository, ConfigRepository configRepository, BillFactory billFactory, BillRepository billRepository, SeatingOrganizer seatingOrganizer, ReservationRepository reservationRepository) {
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
            throw new CustomerDoesNotExistException();
        }

        this.createBill(customer);

        this.seatingOrganizer.removeCustomerFromSeating(customer, this.reservationRepository);
    }

    private void createBill(Customer customer) {
        Order order = this.orderRepository.findOrderByCustomerId(customer.getId());
        this.processBill(customer, order);
    }

    private void processBill(Customer customer, Order order) {
        Bill bill = this.billFactory.createBill(order, this.configRepository.findConfig().getTaxRate(), this.configRepository.findConfig().getGroupTipRate(), customer.hasGroup());
        this.billRepository.saveBillByCustomerId(customer.getId(), bill);
    }
}
