package ca.ulaval.glo4002.cafe.application.menu;

public enum CoffeeType {
    AMERICANO("Americano"),
    CAPPUCCINO("Cappuccino"),
    DARK_ROAST("Dark Roast"),
    FLAT_WHITE("Flat White"),
    ESPRESSO("Espresso"),
    LATTE("Latte"),
    MACCHIATO("Macchiato"),
    MOCHA("Mocha");

    private final String label;

    CoffeeType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
    public CoffeeType fromLabel(String label) {
        for (CoffeeType type : CoffeeType.values()) {
            if (type.label.equals(label)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant " + CoffeeType.class.getCanonicalName() + "." + label);
    }
}
