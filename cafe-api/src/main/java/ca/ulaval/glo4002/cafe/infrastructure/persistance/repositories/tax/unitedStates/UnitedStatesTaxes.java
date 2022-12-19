package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.tax.unitedStates;

import ca.ulaval.glo4002.cafe.domain.tax.Area;
import ca.ulaval.glo4002.cafe.domain.tax.CountryTaxesRepository;
import ca.ulaval.glo4002.cafe.domain.tax.TaxRate;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class UnitedStatesTaxes implements CountryTaxesRepository {
    private final Map<Area, TaxRate> provinceTaxes;
    public UnitedStatesTaxes() {
        this.provinceTaxes = Arrays.stream(UnitedStates.values())
                .collect(Collectors.toMap(UnitedStates::getStateCode, UnitedStates::getTaxRate));
    }

    @Override
    public TaxRate findTaxRate(Area area) {
        return this.provinceTaxes.get(area);
    }
}
