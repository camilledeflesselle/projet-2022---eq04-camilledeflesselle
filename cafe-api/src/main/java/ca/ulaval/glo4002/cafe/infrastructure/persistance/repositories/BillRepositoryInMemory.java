package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories;

import ca.ulaval.glo4002.cafe.domain.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.bill.IBillRepository;
import ca.ulaval.glo4002.cafe.domain.bill.NoBillException;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;

import java.util.HashMap;

public class BillRepositoryInMemory implements IBillRepository {
    private final HashMap<CustomerId, Bill> bills;

    public BillRepositoryInMemory() {
        this.bills = new HashMap<>();
    }

    public void saveBillByCustomerId(CustomerId customerId, Bill bill) {
        bills.put(customerId, bill);
    }

    public Bill findBillByCustomerId(CustomerId customerId) {
        if (!this.bills.containsKey(customerId)) {
            throw new NoBillException();
        }
        return this.bills.get(customerId);
    }

    public int getAmount() {
        return bills.size();
    }

    public void deleteAll() {
        this.bills.clear();
    }
}
