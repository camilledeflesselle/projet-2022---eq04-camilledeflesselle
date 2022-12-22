package ca.ulaval.glo4002.cafe.domain.inventory;


public interface InventoryRepository {
    Inventory getInventory();

    void save(Inventory inventory);

    void reset();
}
