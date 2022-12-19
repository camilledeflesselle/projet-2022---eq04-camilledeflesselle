package ca.ulaval.glo4002.cafe.domain.menu;

public class MenuItemId {
    private final String name;
    private final boolean isCustom;

    public MenuItemId(String name) {
        this.name = name;
        this.isCustom = false;
    }

    public MenuItemId(String name, boolean isCustom) {
        this.name = name;
        this.isCustom = isCustom;
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

    public boolean isCustom() {
        return this.isCustom;
    }
}
