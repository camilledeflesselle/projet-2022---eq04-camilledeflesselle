package ca.ulaval.glo4002.cafe.domain.tax;

public interface CountryTaxesRepository {
    TaxRate findTaxRate(Area area);
}
