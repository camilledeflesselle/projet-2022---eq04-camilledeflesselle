package ca.ulaval.glo4002.cafe.application.bill;

import ca.ulaval.glo4002.cafe.domain.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.bill.BillRepository;
import ca.ulaval.glo4002.cafe.domain.bill.NoBillException;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BillServiceTest {
    private static final CustomerId A_CUSTOMER_ID = new CustomerId("1");
    private BillRepository billRepository;
    private BillService billService;

    @BeforeEach
    public void before() {
        billRepository = mock(BillRepository.class);
        billService = new BillService(billRepository);
    }

    @Test
    public void whenGettingBillByCustomerIdAndNoBillExists_thenSearchBillInRepository() {
        Bill bill = mock(Bill.class);
        when(billRepository.findBillByCustomerId(A_CUSTOMER_ID)).thenReturn(bill);
        billService.getBillByCustomerId(A_CUSTOMER_ID);
        verify(billRepository).findBillByCustomerId(A_CUSTOMER_ID);
    }


    @Test
    public void givenNoBillCreatedForCustomer_whenGetBillByCustomerId_thenRaiseNoBillException() {
        when(billRepository.findBillByCustomerId(A_CUSTOMER_ID)).thenReturn(null);
        assertThrows(NoBillException.class, () -> billService.getBillByCustomerId(A_CUSTOMER_ID));
        verify(billRepository).findBillByCustomerId(A_CUSTOMER_ID);
    }
}