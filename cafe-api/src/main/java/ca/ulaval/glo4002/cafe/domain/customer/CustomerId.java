package ca.ulaval.glo4002.cafe.domain.customer;

import java.util.Objects;

public class CustomerId {
    private final String id;

    public CustomerId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof CustomerId)) return false;
        return Objects.equals(((CustomerId) o).id, this.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    public String getId() {
        return this.id;
    }
}
