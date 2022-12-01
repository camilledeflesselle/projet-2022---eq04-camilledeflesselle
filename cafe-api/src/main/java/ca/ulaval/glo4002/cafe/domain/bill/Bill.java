package ca.ulaval.glo4002.cafe.domain.bill;

import ca.ulaval.glo4002.cafe.domain.order.Order;

public class Bill {
    private final Order order;
    private final Amount subtotal;
    private final Amount taxes;
    private final Amount tip;

    public Bill(Order order, Amount subtotal, Amount taxes, Amount tip) {
        this.order = order;
        this.subtotal = subtotal;
        this.taxes = taxes;
        this.tip = tip;
    }

    public Order getOrder() {
        return this.order;
    }

    public Amount getSubtotal() {
        return this.subtotal;
    }

    public Amount getTotal() {
        return this.subtotal.add(taxes).add(tip);
    }

    public Amount getTaxes() {
        return this.taxes;
    }

    public Amount getTip() {
        return this.tip;
    }
}
