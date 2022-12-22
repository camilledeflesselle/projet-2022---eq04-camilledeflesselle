package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.tax.canadian;

import ca.ulaval.glo4002.cafe.domain.tax.Area;
import ca.ulaval.glo4002.cafe.domain.tax.CountryTaxRepository;
import ca.ulaval.glo4002.cafe.domain.tax.TaxRate;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class CanadianTax implements CountryTaxRepository {
    private final Map<Area, TaxRate> provinceTaxes;
    public CanadianTax() {
        this.provinceTaxes = Arrays.stream(CanadianProvinces.values())
                .collect(Collectors.toMap(CanadianProvinces::getProvinceCode, CanadianProvinces::getTaxRate));
    }

    @Override
    public TaxRate findTaxRate(Area area) {
        return this.provinceTaxes.get(area);
    }
}
