package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaxesRepositoryInMemoryTest {
    private TaxesRepositoryInMemory taxesRepository;

    @BeforeEach
    public void initializeRepository() {
        taxesRepository = new TaxesRepositoryInMemory();
    }

    @Test
    public void whenFindCountries_thenFindCAandUSandCLandNone() {
        List<String> countries = taxesRepository.findCountries();
        assertTrue(countries.contains("CA"));
        assertTrue(countries.contains("US"));
        assertTrue(countries.contains("CL"));
        assertTrue(countries.contains("None"));
    }

    @Test
    public void whenFindCalculationTypeOfUS_thenReturnsState() {
        String calculationType = taxesRepository.findCalculationTypeByCountry("US");
        assertEquals("state", calculationType);
    }

    @Test
    public void whenFindCalculationTypeOfCA_thenReturnsProvince() {
        String calculationType = taxesRepository.findCalculationTypeByCountry("CA");
        assertEquals("province", calculationType);
    }

    @Test
    public void whenFindCalculationTypeOfCountryDifferentOfUSOrCA_thenReturnsEmptyString() {
        String calculationType = taxesRepository.findCalculationTypeByCountry("FR");
        assertEquals("", calculationType);
    }

    @Test
    public void whenFindTerritoriesNamesOfUs_thenReturnsAllStatesOfUs() {
        List<String> territories = taxesRepository.findTerritoriesNamesByCountry("US");
        assertTrue(territories.containsAll(List.of("AL", "AZ", "CA", "FL", "ME", "TX")));
    }

    @Test
    public void whenFindTerritoriesNamesOfCa_thenReturnsAllProvincesOfCanada() {
        List<String> territories = taxesRepository.findTerritoriesNamesByCountry("CA");
        assertTrue(territories.containsAll(List.of("AB", "BC", "MB", "NB", "NL", "NT", "NS", "NU", "ON", "PE", "QC", "SK")));
    }
}