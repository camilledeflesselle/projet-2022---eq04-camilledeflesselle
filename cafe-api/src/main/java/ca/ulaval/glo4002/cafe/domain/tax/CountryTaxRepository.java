package ca.ulaval.glo4002.cafe.domain.tax;

public interface CountryTaxRepository {
    TaxRate findTaxRate(Area area);
}
