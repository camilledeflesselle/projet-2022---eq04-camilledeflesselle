package ca.ulaval.glo4002.cafe.domain.menu;

public class MenuItemId {
    private String name;

    public MenuItemId(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof MenuItemId id)) return false;
        return this.name.equals(id.name);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}
