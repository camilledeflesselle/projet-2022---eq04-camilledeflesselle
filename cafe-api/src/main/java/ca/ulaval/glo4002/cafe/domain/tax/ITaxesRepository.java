package ca.ulaval.glo4002.cafe.domain.tax;

public interface ITaxesRepository {
    TaxRate findTaxRate(Area country, Area area);
}
