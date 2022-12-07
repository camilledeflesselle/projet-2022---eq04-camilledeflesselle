package ca.ulaval.glo4002.cafe.application.bill;

import ca.ulaval.glo4002.cafe.domain.bill.Amount;
import ca.ulaval.glo4002.cafe.domain.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.bill.TaxRate;
import ca.ulaval.glo4002.cafe.domain.bill.TipRate;
import ca.ulaval.glo4002.cafe.domain.order.Order;

public class BillFactory {
    public TipRate DEFAULT_TIP_RATE = new TipRate(0f);

    public Bill createBill(Order customersOrder, TaxRate taxRate, TipRate groupTipRate) {
        if (groupTipRate == null) {
            groupTipRate = DEFAULT_TIP_RATE;
        }
        Amount subtotal = new Amount(0f);
        subtotal = this.getSubtotal(customersOrder, subtotal);
        Amount taxes = subtotal.applyRate(taxRate);
        Amount tip = subtotal.applyRate(groupTipRate);
        return new Bill(customersOrder, subtotal, taxes, tip);
    }

    private Amount getSubtotal(Order order, Amount subtotal) {
        return order.calculateTotal(subtotal);
    }
}
