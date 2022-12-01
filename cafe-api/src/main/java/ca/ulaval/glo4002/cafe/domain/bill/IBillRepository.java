package ca.ulaval.glo4002.cafe.domain.bill;

import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;

public interface IBillRepository {
    void saveBillByCustomerId(CustomerId customerId, Bill bill);

    Bill findBillByCustomerId(CustomerId customerId);

    int getAmount();

    void deleteAll();
}
