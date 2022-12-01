package ca.ulaval.glo4002.cafe.domain.inventory;

public class Ingredient {
    private final String name;
    private int quantity;

    public Ingredient(String name, int i) {
        this.name = name;
        this.quantity = i;
    }

    public static void resetIngredientCount() {
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Ingredient ingredient)) return false;
        return this.name.equals(ingredient.name);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    public void use(Integer quantity) {
        this.quantity -= quantity;
    }

    public Integer getQuantity() {
        return this.quantity;
    }
}
