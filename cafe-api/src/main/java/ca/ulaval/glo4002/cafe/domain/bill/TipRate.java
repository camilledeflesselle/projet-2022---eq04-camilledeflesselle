package ca.ulaval.glo4002.cafe.domain.bill;

public class TipRate extends Rate {
    public TipRate(double rate) {
        super(rate);
    }

    public TipRate add(TipRate tipRate) {
        return new TipRate(this.rate.add(tipRate.rate).doubleValue());
    }
}
