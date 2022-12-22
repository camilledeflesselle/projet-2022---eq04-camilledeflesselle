package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.tax;

import ca.ulaval.glo4002.cafe.domain.tax.Area;
import ca.ulaval.glo4002.cafe.domain.tax.CountryTaxesRepository;
import ca.ulaval.glo4002.cafe.domain.tax.ITaxesRepository;
import ca.ulaval.glo4002.cafe.domain.tax.TaxRate;
import ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.tax.canadian.CanadianTaxes;
import ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.tax.unitedStates.UnitedStatesTaxes;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Map.entry;

public class TaxesRepositoryInMemory implements ITaxesRepository {
    private final Map<Area, TaxRate> federalTaxes;
    private final Map<Area, CountryTaxesRepository> taxesByCountries;
    public TaxesRepositoryInMemory() {
        this.federalTaxes = Arrays.stream(Country.values())
                .collect(Collectors.toMap(Country::getCountryCode, Country::getTaxRate));
        this.taxesByCountries = Map.ofEntries(
                entry(Country.CANADA.getCountryCode(), new CanadianTaxes()),
                entry(Country.UNITED_STATES.getCountryCode(), new UnitedStatesTaxes())
        );
    }

    public TaxRate findTaxRate(Area country, Area area) {
        TaxRate taxRate = this.federalTaxes.get(country);
        if (taxesByCountries.containsKey(country)) {
            CountryTaxesRepository countryTaxesRepository = taxesByCountries.get(country);
            TaxRate areaTax = countryTaxesRepository.findTaxRate(area);
            if (areaTax != null) {
                taxRate = taxRate.add(areaTax);
            } else {
                taxRate = null;
            }
        }
        return taxRate;
    }
}
