package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories;

import ca.ulaval.glo4002.cafe.domain.config.Config;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConfigRepositoryInMemoryTest {

    @Test
    void whenInitialize_thenNameAndSizeAreCorrect() {
        ConfigRepositoryInMemory configRepositoryInMemory = new ConfigRepositoryInMemory();

        assertEquals("Les 4-Fées", configRepositoryInMemory.findConfig().getName());
    }

    @Test
    void whenSaveConfig_thenConfigIsUpdated() {
        ConfigRepositoryInMemory configRepositoryInMemory = new ConfigRepositoryInMemory();
        Config config = new Config();
        configRepositoryInMemory.saveConfig(config);

        assertEquals(config, configRepositoryInMemory.findConfig());
    }
}