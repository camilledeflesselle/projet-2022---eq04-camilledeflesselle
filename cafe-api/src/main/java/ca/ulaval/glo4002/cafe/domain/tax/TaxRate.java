package ca.ulaval.glo4002.cafe.domain.tax;

import ca.ulaval.glo4002.cafe.domain.bill.Rate;

public class TaxRate extends Rate {

    public TaxRate(double rate) {
        super(rate);
    }

    public TaxRate add(TaxRate taxRate) {
        return new TaxRate(this.rate.add(taxRate.rate).doubleValue());
    }
}
