package ca.ulaval.glo4002.cafe.application.bill;

import ca.ulaval.glo4002.cafe.domain.bill.Amount;
import ca.ulaval.glo4002.cafe.domain.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.bill.BillFactory;
import ca.ulaval.glo4002.cafe.domain.bill.TipRate;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemId;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.tax.TaxRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BillFactoryTest {
    private static final Amount NULL_AMOUNT = new Amount(0);
    private static final Amount POSITIVE_AMOUNT = new Amount(7.7f);
    private static final boolean CUSTOMER_HAS_GROUP = true;

    private final TaxRate A_TAX_RATE = new TaxRate(0.1);
    private final TipRate A_GROUP_TIP_RATE = new TipRate(0.3);
    private final List<MenuItem> SOME_MENU_ITEMS = new ArrayList<>();
    private final Order SOME_CUSTOMER_ORDERS = new Order(SOME_MENU_ITEMS);

    Bill bill;
    BillFactory billFactory;

    @BeforeEach
    public void setUp(){
        billFactory = new BillFactory();
    }

    @Test
    public void whenCreateBillForCustomerWithoutGroup_thenReturnsBillObject(){
        assertEquals(Bill.class, billFactory.createBill(SOME_CUSTOMER_ORDERS, A_TAX_RATE, A_GROUP_TIP_RATE, !CUSTOMER_HAS_GROUP).getClass());
    }

    @Test
    public void whenCreateBillWithoutGroup_thenBillTipIsNull(){
        bill = billFactory.createBill(SOME_CUSTOMER_ORDERS, A_TAX_RATE, A_GROUP_TIP_RATE, !CUSTOMER_HAS_GROUP);
        assertEquals(NULL_AMOUNT, bill.getTip());
    }

    @Test
    public void whenCreateBillWithoutMenuItems_thenBillSubtotalIsNull(){
        bill = billFactory.createBill(SOME_CUSTOMER_ORDERS, A_TAX_RATE, A_GROUP_TIP_RATE, CUSTOMER_HAS_GROUP);
        assertEquals(NULL_AMOUNT, bill.getSubtotal());
    }

    @Test
    public void whenCreateBillWithOneMenuItem_thenBillSubtotalIsMenuItemPrice(){
        MenuItem ONE_ITEM = new MenuItem(new MenuItemId("Dark Roast"), POSITIVE_AMOUNT);
        SOME_MENU_ITEMS.add(ONE_ITEM);
        Order customersOrder = new Order(SOME_MENU_ITEMS);

        bill = billFactory.createBill(customersOrder, A_TAX_RATE, A_GROUP_TIP_RATE, CUSTOMER_HAS_GROUP);

        assertEquals(POSITIVE_AMOUNT, bill.getSubtotal());
    }

    @Test
    public void whenCreateBillWithOrderOfTwoMenuItems_thenBillSubtotalIsTheSumOfPrices(){
        MenuItem ONE_ITEM = new MenuItem(new MenuItemId("Latte"), POSITIVE_AMOUNT);
        MenuItem SECOND_ITEM = new MenuItem(new MenuItemId("Espresso"), POSITIVE_AMOUNT);
        SOME_MENU_ITEMS.add(ONE_ITEM);
        SOME_MENU_ITEMS.add(SECOND_ITEM);
        Order customersOrder = new Order(SOME_MENU_ITEMS);

        bill = billFactory.createBill(customersOrder, A_TAX_RATE, A_GROUP_TIP_RATE, CUSTOMER_HAS_GROUP);

        assertEquals(POSITIVE_AMOUNT.add(POSITIVE_AMOUNT), bill.getSubtotal());
    }

    @Test
    public void whenCreateBillWithOrderOfOneMenuItemsAndTaxRate_thenTaxesAreTheSubtotalApplyingATaxRate(){
        MenuItem ONE_ITEM = new MenuItem(new MenuItemId("Latte"), new Amount(10f));
        SOME_MENU_ITEMS.add(ONE_ITEM);
        Order customersOrder = new Order(SOME_MENU_ITEMS);

        bill = billFactory.createBill(customersOrder, A_TAX_RATE, A_GROUP_TIP_RATE, CUSTOMER_HAS_GROUP);

        Amount expectedTaxes = bill.getSubtotal().applyRate(A_TAX_RATE);
        assertEquals(expectedTaxes, bill.getTaxes());
    }

    @Test
    public void whenCreateBillForAGroup_thenReturnsBillObject(){
        assertEquals(Bill.class, billFactory.createBill(SOME_CUSTOMER_ORDERS, A_TAX_RATE, A_GROUP_TIP_RATE, CUSTOMER_HAS_GROUP).getClass());
    }

    @Test
    public void whenCreateBillForAGroupWithOrderAndTipRate_thenBillTipIsSubtotalApplyingTipRate() {
        MenuItem ONE_ITEM = new MenuItem(new MenuItemId("Latte"), POSITIVE_AMOUNT);
        SOME_MENU_ITEMS.add(ONE_ITEM);
        Order customersOrder = new Order(SOME_MENU_ITEMS);

        bill = billFactory.createBill(customersOrder, A_TAX_RATE, A_GROUP_TIP_RATE, CUSTOMER_HAS_GROUP);

        Amount expectedTip = bill.getSubtotal().applyRate(A_GROUP_TIP_RATE);
        assertEquals(expectedTip, bill.getTip());
    }
}