package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories.tax;

import ca.ulaval.glo4002.cafe.domain.tax.Area;
import ca.ulaval.glo4002.cafe.domain.tax.TaxRate;

public enum Country {
    CANADA("CA", 5f),
    UNITED_STATES("US", 0f),
    CL("CL", 19f),
    NONE("None", 0f);

    private final Area countryCode;
    private final TaxRate taxRate;

    Country(String countryCode, float taxRate) {
        this.countryCode = new Area(countryCode);
        this.taxRate = new TaxRate(taxRate);
    }

    public static Country toEnum(String country) {
        for (Country c : Country.values()) {
            if (c.countryCode.equals(new Area(country))) {
                return c;
            }
        }
        return null;
    }

    public Area getCountryCode() {
        return countryCode;
    }

    public TaxRate getTaxRate() {
        return taxRate;
    }
}
