package ca.ulaval.glo4002.cafe.application.bill;

import ca.ulaval.glo4002.cafe.domain.bill.IBillRepository;
import ca.ulaval.glo4002.cafe.domain.bill.NoBillException;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BillServiceTest {
    private static final CustomerId A_CUSTOMER_ID = new CustomerId("1");
    private IBillRepository billRepository;
    private BillService billService;

    @BeforeEach
    public void before() {
        billRepository = mock(IBillRepository.class);
        billService = new BillService(billRepository);
    }


    @Test
    public void givenNoBillCreatedForCustomer_whenGetBillByCustomerId_thenRaiseNoBillException() {
        when(billRepository.findBillByCustomerId(A_CUSTOMER_ID)).thenReturn(null);
        assertThrows(NoBillException.class, () -> billService.getBillByCustomerId(A_CUSTOMER_ID));
        verify(billRepository).findBillByCustomerId(A_CUSTOMER_ID);
    }
    /*
    @Test
    public void whenReset_thenAllBillsAreDeletedFromRepository() {
        billService.reset();

        verify(billRepository).deleteAll();
    }


    private static final TaxRate A_TAX_RATE = new TaxRate(0.15f);
    private static final String A_COUNTRY = "CA";
    private static final String US_COUNTRY = "US";
    private static final String CANADA_COUNTRY = "CA";
    private static final String A_STATE = "AZ";
    private static final String AN_AREA_NAME = "province";
    private static final String PROVINCE = "province";
    private static final String STATE = "state";
    private static final String A_PROVINCE = "QC";
    @Test
    void whenUpdateConfigWithCountryAndArea_thenFindTaxRateInTaxesRepositoryFromCountryAndArea() {
        when(taxesRepository.findCalculationTypeByCountry(A_COUNTRY)).thenReturn(AN_AREA_NAME);

        billService.updateConfig(A_COUNTRY, A_PROVINCE, A_STATE, mock(TipRate.class));

        verify(taxesRepository).findTaxRate(A_COUNTRY, A_PROVINCE);
    }

    @Test
    void whenUpdateConfigWithCountryWithProvince_thenAreaNameIsProvince() {
        when(taxesRepository.findCalculationTypeByCountry(CANADA_COUNTRY)).thenReturn(PROVINCE);

        billService.updateConfig(CANADA_COUNTRY, A_PROVINCE, A_STATE, mock(TipRate.class));

        assertEquals(A_PROVINCE, billService.getAreaName());
    }

    @Test
    void whenUpdateConfigWithCountryWithState_thenAreaNameIsState() {
        when(taxesRepository.findCalculationTypeByCountry(US_COUNTRY)).thenReturn(STATE);

        billService.updateConfig(US_COUNTRY, A_PROVINCE, A_STATE, mock(TipRate.class));

        assertEquals(A_STATE, billService.getAreaName());
    }

    @Test
    void whenUpdateConfigWithoutNothing_thenAreaNameIsEmpty() {
        when(taxesRepository.findCalculationTypeByCountry("")).thenReturn("");

        billService.updateConfig("", "", "", mock(TipRate.class));

        assertEquals("", billService.getAreaName());
    }

    @Test
    void whenUpdateTaxRate_thenTaxRateIsUpdated() {
        when(taxesRepository.findTaxRate(any(), any())).thenReturn(A_TAX_RATE);

        billService.updateTaxRate();

        assertEquals(A_TAX_RATE, billService.getTaxRate());
    }*/
}