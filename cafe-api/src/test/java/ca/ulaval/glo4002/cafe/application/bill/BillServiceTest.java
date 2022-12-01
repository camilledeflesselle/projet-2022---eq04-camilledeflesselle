package ca.ulaval.glo4002.cafe.application.bill;

import ca.ulaval.glo4002.cafe.application.bill.BillFactory;
import ca.ulaval.glo4002.cafe.application.bill.BillService;
import ca.ulaval.glo4002.cafe.domain.bill.*;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.order.*;
import ca.ulaval.glo4002.cafe.infrastructure.rest.validators.config.InvalidMenuOrderException;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BillServiceTest {
    private static final CustomerId A_CUSTOMER_ID = new CustomerId("1");
    private static final Order SOME_CUSTOMER_ORDER = new Order(Arrays.asList(new MenuItem("cheese", new Amount(13.25f)), new MenuItem("chocolate", new Amount(12.5f))));
    private static final String AN_ITEM_NAME = "Café";
    private static final String ANOTHER_ITEM_NAME = "Big10";
    private static final TaxRate A_TAX_RATE = new TaxRate(0.15f);
    private static final String A_COUNTRY = "CA";
    private static final String US_COUNTRY = "US";
    private static final String CANADA_COUNTRY = "CA";
    private static final String A_STATE = "AZ";
    private static final String AN_AREA_NAME = "province";
    private static final String PROVINCE = "province";
    private static final String STATE = "state";
    private static final String A_PROVINCE = "QC";

    private BillFactory billFactory;
    private IBillRepository billRepository;
    private ITaxesRepository taxesRepository;
    private IMenuItemRepository menuItemRepository;
    private BillService billService;

    @BeforeEach
    public void before() {
        billFactory = mock(BillFactory.class);
        billRepository = mock(IBillRepository.class);
        taxesRepository = mock(ITaxesRepository.class);
        menuItemRepository = mock(IMenuItemRepository.class);
        billService = new BillService(billFactory, billRepository, taxesRepository, menuItemRepository);
    }

    @Test
    public void whenProcessingBillForCustomer_thenCreateBillWithCustomerOrdersAndTaxRate() {
        billService.processBillForCustomer(A_CUSTOMER_ID, SOME_CUSTOMER_ORDER);

        verify(billFactory).createBill(SOME_CUSTOMER_ORDER, billService.getTaxRate());
    }

    @Test
    public void whenProcessingBillForCustomer_thenSaveCreatedBillInRepository() {
        Bill bill = mock(Bill.class);
        when(billFactory.createBill(any(), any())).thenReturn(bill);

        billService.processBillForCustomer(A_CUSTOMER_ID, SOME_CUSTOMER_ORDER);

        verify(billRepository).saveBillByCustomerId(A_CUSTOMER_ID, bill);
    }

    @Test
    public void whenProcessingBillForCustomerWithGroup_thenCreateBillWithCustomerOrdersAndTaxRateAndTipRate() {
        billService.processBillForGroup(A_CUSTOMER_ID, SOME_CUSTOMER_ORDER);

        verify(billFactory).createBillForGroup(SOME_CUSTOMER_ORDER, billService.getTaxRate(), billService.getTipRate());
    }

    @Test
    public void whenProcessingBillForCustomerWithGroup_thenSaveCreatedBillInRepository() {
        Bill bill = mock(Bill.class);
        when(billFactory.createBillForGroup(any(), any(), any())).thenReturn(bill);

        billService.processBillForGroup(A_CUSTOMER_ID, SOME_CUSTOMER_ORDER);

        verify(billRepository).saveBillByCustomerId(A_CUSTOMER_ID, bill);
    }

    @Test
    public void whenGetBillByCustomerId_thenSearchInBillStorageByCustomerId() {
        billService.getBillByCustomerId(A_CUSTOMER_ID);

        verify(billRepository).findBillByCustomerId(A_CUSTOMER_ID);
    }

    @Test
    public void givenMenuItemNamesWithOneThatIsNotInRepository_whenBuildingMenuItemsList_thenThrowsInvalidMenuOrderException() {
        List<String> menuItemsStr = new ArrayList<>(List.of(AN_ITEM_NAME, ANOTHER_ITEM_NAME));
        when(menuItemRepository.findMenuItemById(ANOTHER_ITEM_NAME)).thenThrow(NotFoundException.class);

        assertThrows(
                InvalidMenuOrderException.class,
                () -> billService.buildMenuItemListFromStr(menuItemsStr)
        );
    }

    @Test
    public void givenMenuItemNamesThatExistInRepository_whenBuildingMenuItemsList_thenEachIsSearchedInRepository() {
        List<String> menuItemsStr = new ArrayList<>(List.of(AN_ITEM_NAME, ANOTHER_ITEM_NAME));
        when(menuItemRepository.findMenuItemById(AN_ITEM_NAME)).thenReturn(mock(MenuItem.class));
        when(menuItemRepository.findMenuItemById(ANOTHER_ITEM_NAME)).thenReturn(mock(MenuItem.class));

        billService.buildMenuItemListFromStr(menuItemsStr);

        verify(menuItemRepository).findMenuItemById(AN_ITEM_NAME);
        verify(menuItemRepository).findMenuItemById(ANOTHER_ITEM_NAME);
    }

    @Test
    public void whenReset_thenAllBillsAreDeletedFromRepository() {
        billService.reset();

        verify(billRepository).deleteAll();
    }

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
    }
}