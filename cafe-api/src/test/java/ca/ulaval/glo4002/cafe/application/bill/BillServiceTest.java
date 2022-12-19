package ca.ulaval.glo4002.cafe.application.bill;

import ca.ulaval.glo4002.cafe.domain.bill.Amount;
import ca.ulaval.glo4002.cafe.domain.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.bill.IBillRepository;
import ca.ulaval.glo4002.cafe.domain.config.Config;
import ca.ulaval.glo4002.cafe.domain.tax.TaxRate;
import ca.ulaval.glo4002.cafe.domain.config.IConfigRepository;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemId;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BillServiceTest {
    private static final CustomerId A_CUSTOMER_ID = new CustomerId("1");
    private static final Order SOME_CUSTOMER_ORDER = new Order(Arrays.asList(new MenuItem(new MenuItemId("Chocolate"), new Amount(13.25f)), new MenuItem(new MenuItemId("Coca"), new Amount(12.5f))));
    private static final TaxRate A_TAX_RATE = new TaxRate(0.15f);

    private BillFactory billFactory;
    private IBillRepository billRepository;
    private BillService billService;
    private IConfigRepository configRepository;

    @BeforeEach
    public void before() {
        billFactory = mock(BillFactory.class);
        billRepository = mock(IBillRepository.class);
        configRepository = mock(IConfigRepository.class);
        billService = new BillService(billFactory, billRepository, configRepository);
    }

    @Test
    public void whenProcessingBillForCustomer_thenCreateBillWithCustomerOrdersAndTaxRate() {
        Config config = new Config();
        config.setTaxRate(A_TAX_RATE);
        when(configRepository.findConfig()).thenReturn(config);
        billService.processBillForCustomer(A_CUSTOMER_ID, SOME_CUSTOMER_ORDER);
        verify(billFactory).createBill(SOME_CUSTOMER_ORDER, A_TAX_RATE, null);
    }

    @Test
    public void whenProcessingBillForCustomer_thenSaveCreatedBillInRepository() {
        Bill bill = mock(Bill.class);
        Config config = new Config();
        when(configRepository.findConfig()).thenReturn(config);
        when(billFactory.createBill(any(), any(), any())).thenReturn(bill);

        billService.processBillForCustomer(A_CUSTOMER_ID, SOME_CUSTOMER_ORDER);

        verify(billRepository).saveBillByCustomerId(A_CUSTOMER_ID, bill);
    }

    @Test
    public void whenProcessingBillForCustomerWithGroup_thenCreateBillWithCustomerOrdersAndTaxRateAndTipRate() {Bill bill = mock(Bill.class);
        when(billFactory.createBill(any(), any(), any())).thenReturn(bill);
        Config config = new Config();
        when(configRepository.findConfig()).thenReturn(config);

        billService.processBillForGroup(A_CUSTOMER_ID, SOME_CUSTOMER_ORDER);

        verify(billFactory).createBill(SOME_CUSTOMER_ORDER, configRepository.findConfig().getTaxRate(), configRepository.findConfig().getGroupTipRate());

    }

    @Test
    public void whenProcessingBillForCustomerWithGroup_thenSaveCreatedBillInRepository() {
        Bill bill = mock(Bill.class);
        Config config = new Config();
        when(configRepository.findConfig()).thenReturn(config);
        when(billFactory.createBill(any(), any(), any())).thenReturn(bill);

        billService.processBillForGroup(A_CUSTOMER_ID, SOME_CUSTOMER_ORDER);

        verify(billRepository).saveBillByCustomerId(A_CUSTOMER_ID, bill);
    }

    @Test
    public void whenGetBillByCustomerId_thenSearchInBillStorageByCustomerId() {
        billService.getBillByCustomerId(A_CUSTOMER_ID);

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