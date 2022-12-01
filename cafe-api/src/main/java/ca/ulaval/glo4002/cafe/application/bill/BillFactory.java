package ca.ulaval.glo4002.cafe.application.bill;

import ca.ulaval.glo4002.cafe.domain.bill.Amount;
import ca.ulaval.glo4002.cafe.domain.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.bill.TaxRate;
import ca.ulaval.glo4002.cafe.domain.bill.TipRate;
import ca.ulaval.glo4002.cafe.domain.order.*;

public class BillFactory {
    public Amount DEFAULT_TIP_RATE = new Amount(0f);

    public Bill createBill(Order customersOrder, TaxRate taxRate) {
        Amount subtotal = new Amount(0f);
        subtotal = getSubtotal(customersOrder, subtotal);
        Amount taxes = subtotal.applyRate(taxRate);
        return new Bill(customersOrder, subtotal, taxes, DEFAULT_TIP_RATE);
    }

    public Bill createBillForGroup(Order customersOrder, TaxRate taxRate, TipRate groupTipRate) {
        Amount subtotal = new Amount(0f);
        subtotal = getSubtotal(customersOrder, subtotal);
        Amount taxes = subtotal.applyRate(taxRate);
        Amount tip = subtotal.applyRate(groupTipRate);
        return new Bill(customersOrder, subtotal, taxes, tip);
    }

    private static Amount getSubtotal(Order customersOrder, Amount subtotal) {
        for (MenuItem item : customersOrder.getMenuItems()) {
            subtotal = subtotal.add(item.getPrice());
        }
        return subtotal;
    }
}
