package ca.ulaval.glo4002.cafe.domain.bill;

import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;

public interface BillRepository {
    void saveBillByCustomerId(CustomerId customerId, Bill bill);

    Bill findBillByCustomerId(CustomerId customerId);

    void deleteAll();

    int getNumberOfBills();
}
