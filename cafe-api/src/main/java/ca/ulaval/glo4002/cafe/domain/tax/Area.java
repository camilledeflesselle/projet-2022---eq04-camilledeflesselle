package ca.ulaval.glo4002.cafe.domain.tax;

import java.util.Objects;

public class Area {
    private final String name;

    public Area(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Area)) return false;
        return Objects.equals(((Area) o).name, this.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }

    public String getName() {
        return name;
    }
}
