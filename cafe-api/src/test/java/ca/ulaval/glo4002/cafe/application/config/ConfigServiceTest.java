package ca.ulaval.glo4002.cafe.application.config;

import ca.ulaval.glo4002.cafe.domain.config.Config;
import ca.ulaval.glo4002.cafe.domain.config.ConfigRepository;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ConfigServiceTest {

    @Test
    void whenUpdateConfig_thenConfigIsSavedInStorage() {
        ConfigRepository configRepository = mock(ConfigRepository.class);
        ConfigService configService = new ConfigService(configRepository);
        Config config = new Config();

        configService.updateConfig(config);

        verify(configRepository).saveConfig(config);
    }
}