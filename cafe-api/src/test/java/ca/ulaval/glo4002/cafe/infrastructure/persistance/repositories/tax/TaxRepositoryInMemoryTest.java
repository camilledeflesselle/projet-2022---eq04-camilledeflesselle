package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.tax;

import ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.tax.canada.CanadaProvinces;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TaxRepositoryInMemoryTest {
    private TaxRepositoryInMemory taxesRepository;

    @BeforeEach
    public void initializeRepository() {
        taxesRepository = new TaxRepositoryInMemory();
    }

    @Test
    public void whenFindTax_thenTaxeIsNotNull() {
        assertNotNull(taxesRepository.findTaxRate(Country.Canada.getCountryCode(), CanadaProvinces.QUEBEC.getProvinceCode()));
    }
}