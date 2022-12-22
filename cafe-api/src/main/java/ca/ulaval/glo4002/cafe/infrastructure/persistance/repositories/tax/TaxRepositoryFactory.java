package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.tax;

import ca.ulaval.glo4002.cafe.domain.tax.CountryTaxRepository;
import ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.tax.canada.CanadaTax;
import ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.tax.unitedStates.UnitedStatesTax;

public class TaxRepositoryFactory {

    public CountryTaxRepository create(Country country) {
        switch (country) {
            case Canada:
                return new CanadaTax();
            default:
                return new UnitedStatesTax();
        }
    }
}
