package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.tax.canada;

import ca.ulaval.glo4002.cafe.domain.tax.Area;
import ca.ulaval.glo4002.cafe.domain.tax.CountryTaxRepository;
import ca.ulaval.glo4002.cafe.domain.tax.TaxRate;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class CanadaTax implements CountryTaxRepository {
    private final Map<Area, TaxRate> provinceTaxes;
    public CanadaTax() {
        this.provinceTaxes = Arrays.stream(CanadaProvinces.values())
                .collect(Collectors.toMap(CanadaProvinces::getProvinceCode, CanadaProvinces::getTaxRate));
    }

    @Override
    public TaxRate findTaxRate(Area area) {
        return this.provinceTaxes.get(area);
    }
}
