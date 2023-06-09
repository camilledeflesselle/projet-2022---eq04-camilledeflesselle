package ca.ulaval.glo4002.cafe.domain.inventory;

public class IngredientId {
    private final String name;

    public IngredientId(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof IngredientId ingredient)) return false;
        return this.name.equals(ingredient.name);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}