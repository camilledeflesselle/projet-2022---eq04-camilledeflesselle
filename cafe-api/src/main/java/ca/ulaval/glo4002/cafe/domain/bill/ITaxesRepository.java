package ca.ulaval.glo4002.cafe.domain.bill;

import java.util.List;

public interface ITaxesRepository {
    List<String> findCountries();

    String findCalculationTypeByCountry(String country);

    List<String> findTerritoriesNamesByCountry(String country);

    TaxRate findTaxRate(String country, String areaType);
}
