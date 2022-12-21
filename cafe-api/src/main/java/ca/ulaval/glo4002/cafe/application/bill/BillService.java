package ca.ulaval.glo4002.cafe.application.bill;

import ca.ulaval.glo4002.cafe.domain.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.bill.IBillRepository;
import ca.ulaval.glo4002.cafe.domain.bill.NoBillException;
import ca.ulaval.glo4002.cafe.domain.config.IConfigRepository;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.order.Order;

public class BillService {
    private final BillFactory billFactory;
    private final IBillRepository billRepository;
    private final IConfigRepository configRepository;

    public BillService(BillFactory billFactory, IBillRepository billRepository, IConfigRepository configRepository) {
        this.billFactory = billFactory;
        this.billRepository = billRepository;
        this.configRepository = configRepository;
    }

    public void processBillForCustomer(CustomerId customerId, Order order) {
        Bill bill = this.billFactory.createBill(order, this.configRepository.findConfig().getTaxRate(), null);
        this.billRepository.saveBillByCustomerId(customerId, bill);
    }

    public void processBillForGroup(CustomerId customerId, Order order) {
        Bill bill = this.billFactory.createBill(order, this.configRepository.findConfig().getTaxRate(), this.configRepository.findConfig().getGroupTipRate());
        this.billRepository.saveBillByCustomerId(customerId, bill);
    }

    public Bill getBillByCustomerId(CustomerId customerId) {
        Bill bill = billRepository.findBillByCustomerId(customerId);
        if (bill == null) {
            throw new NoBillException();
        }
        return bill;
    }

    public void reset() {
        this.billRepository.deleteAll();
    }
}
