package ca.ulaval.glo4002.cafe.domain.bill;

import ca.ulaval.glo4002.cafe.domain.order.Order;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class BillTest {
    Order order = mock(Order.class);
    Amount subtotal = mock(Amount.class);
    Amount taxes = mock(Amount.class);
    Amount tip = mock(Amount.class);

    @Test
    public void whenRequestingTotal_thenAddTaxesAndTipToSubtotal() {
        Bill bill = new Bill(order, subtotal, taxes, tip);
        Amount subtotalAndTaxes = mock(Amount.class);
        when(subtotal.add(taxes)).thenReturn(subtotalAndTaxes);

        bill.getTotal();

        verify(subtotal).add(taxes);
        verify(subtotalAndTaxes).add(tip);
    }
}
