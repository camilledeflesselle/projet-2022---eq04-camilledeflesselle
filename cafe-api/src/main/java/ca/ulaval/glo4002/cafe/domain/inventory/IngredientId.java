package ca.ulaval.glo4002.cafe.domain.inventory;

import ca.ulaval.glo4002.cafe.domain.seat.SeatId;

import java.util.Objects;

public class IngredientId {
    private final String id;

    public IngredientId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof IngredientId)) return false;
        return Objects.equals(((IngredientId) o).id, this.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
