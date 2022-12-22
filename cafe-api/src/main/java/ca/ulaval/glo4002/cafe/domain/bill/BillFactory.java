package ca.ulaval.glo4002.cafe.domain.bill;

import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.domain.tax.TaxRate;

public class BillFactory {
    public TipRate DEFAULT_TIP_RATE = new TipRate(0f);

    public Bill createBill(Order customersOrder, TaxRate taxRate, TipRate tipRate, boolean customerHasGroup) {
        TipRate groupTipRate = customerHasGroup ? tipRate : DEFAULT_TIP_RATE;
        Amount subtotal = this.getSubtotal(customersOrder);
        Amount taxes = subtotal.applyRate(taxRate);
        Amount tip = subtotal.applyRate(groupTipRate);
        return new Bill(customersOrder, subtotal, taxes, tip);
    }

    private Amount getSubtotal(Order order) {
        return order.calculateTotal();
    }
}
