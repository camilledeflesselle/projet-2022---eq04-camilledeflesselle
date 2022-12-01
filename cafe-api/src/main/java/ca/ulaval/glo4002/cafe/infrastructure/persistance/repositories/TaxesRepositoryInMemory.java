package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories;

import ca.ulaval.glo4002.cafe.domain.bill.ITaxesRepository;
import ca.ulaval.glo4002.cafe.domain.bill.TaxRate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static java.util.Map.entry;

public class TaxesRepositoryInMemory implements ITaxesRepository {
    private final Map<String, TaxRate> federalTaxes;
    private final Map<String, TaxRate> stateTaxes;
    private final Map<String, TaxRate> provinceTaxes;
    private final Map<String, String> calculationTypeByCountries;

    public TaxesRepositoryInMemory() {
        this.federalTaxes = Map.ofEntries(
                entry("CA", new TaxRate(5f)),
                entry("US", new TaxRate(0f)),
                entry("CL", new TaxRate(19f)),
                entry("None", new TaxRate(0f))
        );

        this.provinceTaxes = Map.ofEntries(
                entry("AB", new TaxRate(0f)),
                entry("BC", new TaxRate(7f)),
                entry("MB", new TaxRate(7f)),
                entry("NB", new TaxRate(10f)),
                entry("NL", new TaxRate(10f)),
                entry("NT", new TaxRate(0f)),
                entry("NS", new TaxRate(10f)),
                entry("NU", new TaxRate(0f)),
                entry("ON", new TaxRate(8f)),
                entry("PE", new TaxRate(10f)),
                entry("QC", new TaxRate(9.975f)),
                entry("SK", new TaxRate(6f)),
                entry("YT", new TaxRate(0f))
        );

        this.stateTaxes = Map.ofEntries(
                entry("AL", new TaxRate(4f)),
                entry("AZ", new TaxRate(5.6f)),
                entry("CA", new TaxRate(7.25f)),
                entry("FL", new TaxRate(6f)),
                entry("ME", new TaxRate(5.5f)),
                entry("TX", new TaxRate(6.25f))
        );



        this.calculationTypeByCountries = Map.ofEntries(
                entry("US", "state"),
                entry("CA", "province")
        );
    }

    public TaxRate findTaxRate(String country, String areaType) {
        TaxRate taxRate = this.federalTaxes.get(country);

        switch (country) {
            case "US":
                taxRate = taxRate.add(this.stateTaxes.get(areaType));
                break;
            case "CA":
                taxRate = taxRate.add(this.provinceTaxes.get(areaType));
                break;
        }
        return taxRate;
    }

    public List<String> findCountries() {
        return new ArrayList<>(this.federalTaxes.keySet());
    }

    public String findCalculationTypeByCountry(String country) {
        return calculationTypeByCountries.getOrDefault(country, "");
    }

    public List<String> findTerritoriesNamesByCountry(String country) {
        List<String> territories = new ArrayList<>();
        switch (country) {
            case "US" -> territories.addAll(this.stateTaxes.keySet());
            case "CA" -> territories.addAll(this.provinceTaxes.keySet());
        }
        return territories;
    }
}
