package ca.ulaval.glo4002.cafe.application.inventory;

public enum IngredientType {
    Chocolate("Chocolate"),
    Espresso("Espresso"),
    Milk("Milk"),
    Water("Water");

    private final String label;

    IngredientType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public static boolean contains(String s) {
        for (IngredientType type : IngredientType.values()) {
            if (type.label.equals(s)) {
                return true;
            }
        }
        return false;
    }
}
