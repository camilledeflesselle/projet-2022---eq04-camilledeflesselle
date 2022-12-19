package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.tax.canadian;

import ca.ulaval.glo4002.cafe.domain.tax.Area;
import ca.ulaval.glo4002.cafe.domain.tax.CountryTaxesRepository;
import ca.ulaval.glo4002.cafe.domain.tax.TaxRate;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class CanadianTaxes implements CountryTaxesRepository {
    private final Map<Area, TaxRate> provinceTaxes;
    public CanadianTaxes() {
        this.provinceTaxes = Arrays.stream(CanadianProvinces.values())
                .collect(Collectors.toMap(CanadianProvinces::getProvinceCode, CanadianProvinces::getTaxRate));
    }

    @Override
    public TaxRate findTaxRate(Area area) {
        return this.provinceTaxes.get(area);
    }
}
