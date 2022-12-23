package ca.ulaval.glo4002.cafe.application.inventory;

import ca.ulaval.glo4002.cafe.domain.inventory.IngredientId;

public enum IngredientInLes4Fees {
    Chocolate("Chocolate"),
    Espresso("Espresso"),
    Milk("Milk"),
    Water("Water");

    private final IngredientId id;

    IngredientInLes4Fees(String label) {
        this.id = new IngredientId(label);
    }

    public IngredientId getId() {
        return this.id;
    }

    public boolean contains(String s) {
        for (IngredientInLes4Fees type : IngredientInLes4Fees.values()) {
            if (type.id.getName().equals(s)) {
                return true;
            }
        }
        return false;
    }
}
