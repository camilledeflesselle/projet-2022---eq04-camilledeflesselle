package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories;

import ca.ulaval.glo4002.cafe.domain.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.bill.IBillRepository;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

class BillRepositoryInMemoryTest {
    private static final CustomerId A_CUSTOMER_ID = new CustomerId("id1");
    private static final CustomerId ANOTHER_CUSTOMER_ID = new CustomerId("id2");
    private static Bill billMock;
    private static IBillRepository billRepository;

    @BeforeEach
    public void setup() {
        billRepository = new BillRepositoryInMemory();
        billMock = mock(Bill.class);
    }

    @Test
    public void whenInitialized_thenIsEmpty() {
        assertEquals(0, billRepository.getAmount());
    }

    @Test
    public void givenEmptyBillRepository_whenSavingABill_thenRepositoryHasOneElement() {
        billRepository.saveBillByCustomerId(A_CUSTOMER_ID, billMock);

        assertEquals(1, billRepository.getAmount());
    }

    @Test
    public void givenBillThatExistsInRepository_whenSearchingForBillWithCustomerId_thenReturnsBill() {
        billRepository.saveBillByCustomerId(A_CUSTOMER_ID, billMock);

        Bill returnedBill = billRepository.findBillByCustomerId(A_CUSTOMER_ID);

        assertEquals(billMock, returnedBill);
    }

    @Test
    public void givenBillThatDoesntExistInRepository_whenSearchingForBillWithCustomerId_thenNullBillIsFound() {
        assertNull(billRepository.findBillByCustomerId(A_CUSTOMER_ID));
    }

    @Test
    public void givenNonEmptyBillRepository_whenDeleteAll_thenRepositoryBecomeEmpty() {
        billRepository.saveBillByCustomerId(A_CUSTOMER_ID, billMock);
        billRepository.saveBillByCustomerId(ANOTHER_CUSTOMER_ID, billMock);

        billRepository.deleteAll();

        assertEquals(0, billRepository.getAmount());
    }
}