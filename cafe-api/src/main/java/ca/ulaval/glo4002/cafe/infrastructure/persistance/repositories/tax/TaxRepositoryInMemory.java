package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.tax;

import ca.ulaval.glo4002.cafe.domain.tax.Area;
import ca.ulaval.glo4002.cafe.domain.tax.CountryTaxRepository;
import ca.ulaval.glo4002.cafe.domain.tax.TaxRate;
import ca.ulaval.glo4002.cafe.domain.tax.TaxRepository;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class TaxRepositoryInMemory implements TaxRepository {
    private final Map<Area, TaxRate> federalTaxes;
    private final Map<Area, CountryTaxRepository> taxesByCountries;
    public TaxRepositoryInMemory() {
        TaxRepositoryFactory taxRepositoryFactory = new TaxRepositoryFactory();
        this.federalTaxes = Arrays.stream(Country.values())
                .collect(Collectors.toMap(Country::getCountryCode, Country::getTaxRate));
        this.taxesByCountries = Arrays.stream(Country.values())
                .filter(Country::hasAreas)
                .collect(Collectors.toMap(Country::getCountryCode, taxRepositoryFactory::create));
    }

    public TaxRate findTaxRate(Area country, Area area) {
        TaxRate taxRate = this.federalTaxes.get(country);
        if (taxesByCountries.containsKey(country)) {
            CountryTaxRepository countryTaxRepository = taxesByCountries.get(country);
            TaxRate areaTax = countryTaxRepository.findTaxRate(area);
            if (areaTax != null) {
                taxRate = taxRate.add(areaTax);
            } else {
                taxRate = null;
            }
        }
        return taxRate;
    }
}
