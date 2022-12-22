package ca.ulaval.glo4002.cafe.domain.tax;

public interface TaxRepository {
    TaxRate findTaxRate(Area country, Area area);
}
