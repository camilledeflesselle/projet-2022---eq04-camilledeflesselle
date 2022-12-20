package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories;

import org.junit.jupiter.api.BeforeEach;

class InventoryRepositoryInMemoryTest {
    private InventoryRepositoryInMemory inventoryRepository;

    @BeforeEach
    public void initializeRepository() {
        inventoryRepository = new InventoryRepositoryInMemory();
    }


}