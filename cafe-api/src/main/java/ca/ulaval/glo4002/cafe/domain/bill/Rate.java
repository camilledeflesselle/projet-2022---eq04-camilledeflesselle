package ca.ulaval.glo4002.cafe.domain.bill;

import java.math.BigDecimal;

public abstract class Rate {
    protected final BigDecimal rate;
    public Rate(double rate) {
        this.rate = new BigDecimal(rate);
    }

    public BigDecimal getRate() {
        return this.rate;
    }
}
