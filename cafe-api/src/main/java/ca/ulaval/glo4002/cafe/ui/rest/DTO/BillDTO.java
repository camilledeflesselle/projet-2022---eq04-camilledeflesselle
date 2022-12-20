package ca.ulaval.glo4002.cafe.ui.rest.DTO;

import ca.ulaval.glo4002.cafe.domain.bill.Bill;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BillDTO {
    private List<String> orders;
    private float subtotal;
    private float taxes;
    private float total;
    private float tip;

    public BillDTO() {}

    public BillDTO(Bill bill) {
        this.orders = bill.getOrder().getListOfOrderedItemsStr();
        this.subtotal = bill.getSubtotal().roundToHundredth();
        this.taxes = bill.getTaxes().roundToHundredth();
        this.tip = bill.getTip().roundToHundredth();
        this.total = bill.getTotal().roundToHundredth();
    }
}
