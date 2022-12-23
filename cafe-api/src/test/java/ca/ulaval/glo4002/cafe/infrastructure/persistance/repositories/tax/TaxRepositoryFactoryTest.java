package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.tax;

import ca.ulaval.glo4002.cafe.domain.tax.CountryTaxRepository;
import ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.tax.canada.CanadaTax;
import ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.tax.unitedStates.UnitedStatesTax;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaxRepositoryFactoryTest {

    @Test
    void whenCreateCanadaTaxRepository_thenCanadaTaxRepositoryIsReturned() {
        TaxRepositoryFactory taxRepositoryFactory = new TaxRepositoryFactory();
        CountryTaxRepository countryTaxRepository = taxRepositoryFactory.create(Country.Canada);
        assertTrue(countryTaxRepository instanceof CanadaTax);
    }

    @Test
    void whenCreateUnitedStatesTaxRepository_thenUnitedStatesTaxRepositoryIsReturned() {
        TaxRepositoryFactory taxRepositoryFactory = new TaxRepositoryFactory();
        CountryTaxRepository countryTaxRepository = taxRepositoryFactory.create(Country.UnitedStates);
        assertTrue(countryTaxRepository instanceof UnitedStatesTax);
    }
}