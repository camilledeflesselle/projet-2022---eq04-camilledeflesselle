package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories;

import ca.ulaval.glo4002.cafe.domain.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.bill.BillRepository;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;

import java.util.HashMap;

public class BillRepositoryInMemory implements BillRepository {
    private final HashMap<CustomerId, Bill> bills;

    public BillRepositoryInMemory() {
        this.bills = new HashMap<>();
    }

    public void saveBillByCustomerId(CustomerId customerId, Bill bill) {
        bills.put(customerId, bill);
    }

    public Bill findBillByCustomerId(CustomerId customerId) {
        return this.bills.get(customerId);
    }

    public int getNumberOfBills() {
        return bills.size();
    }

    public void deleteAll() {
        this.bills.clear();
    }
}
