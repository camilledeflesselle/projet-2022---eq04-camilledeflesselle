package ca.ulaval.glo4002.cafe.application.bill;

import ca.ulaval.glo4002.cafe.domain.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.bill.BillRepository;
import ca.ulaval.glo4002.cafe.domain.bill.NoBillException;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;

public class BillService {
    private final BillRepository billRepository;

    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
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
