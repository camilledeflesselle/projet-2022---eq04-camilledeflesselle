package ca.ulaval.glo4002.cafe.application.checkOut;

import ca.ulaval.glo4002.cafe.domain.bill.BillFactory;
import ca.ulaval.glo4002.cafe.domain.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.bill.IBillRepository;
import ca.ulaval.glo4002.cafe.domain.config.IConfigRepository;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerDoesNotExistsException;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.customer.ICustomerRepository;
import ca.ulaval.glo4002.cafe.domain.order.IOrderRepository;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.reservation.IReservationRepository;
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
