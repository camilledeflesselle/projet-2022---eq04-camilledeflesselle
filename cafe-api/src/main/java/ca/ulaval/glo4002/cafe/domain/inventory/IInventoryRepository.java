package ca.ulaval.glo4002.cafe.domain.inventory;


public interface IInventoryRepository {
    Inventory getInventory();

    void save(Inventory inventory);

    void reset();
}
