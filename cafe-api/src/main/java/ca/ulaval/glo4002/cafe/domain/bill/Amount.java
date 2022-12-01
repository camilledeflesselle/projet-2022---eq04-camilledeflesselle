package ca.ulaval.glo4002.cafe.domain.bill;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Amount {
    private final BigDecimal value;

    public Amount(float value) {
        this.value = new BigDecimal(Float.toString(value));
    }

    private Amount(BigDecimal value) {
        this.value = value;
    }

    public float roundToHundredth() {
        return value.setScale(2, RoundingMode.CEILING).floatValue();
    }

    public Amount add(Amount amount) {
        BigDecimal res = this.value.add(amount.value);
        return new Amount(res);
    }

    public Amount applyRate(Rate rate) {
        BigDecimal hundred = new BigDecimal(Float.toString(100f));
        return new Amount(rate.getRate().multiply(this.value).divide(hundred));
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Amount amount)) return false;
        return this.value.compareTo(amount.value) == 0;
    }
}
