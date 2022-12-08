package ca.ulaval.glo4002.cafe.domain.seat;

import java.util.Objects;

public class SeatId {
    private final int id;

    public SeatId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof SeatId)) return false;
        return Objects.equals(((SeatId) o).id, this.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getId() {
        return id;
    }
}
