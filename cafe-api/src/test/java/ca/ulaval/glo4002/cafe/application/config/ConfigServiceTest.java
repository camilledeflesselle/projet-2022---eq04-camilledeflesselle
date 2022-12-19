package ca.ulaval.glo4002.cafe.application.config;

import ca.ulaval.glo4002.cafe.domain.config.Config;
import ca.ulaval.glo4002.cafe.domain.config.IConfigRepository;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ConfigServiceTest {

    @Test
    void whenUpdateConfig_thenConfigIsSavedInStorage() {
        IConfigRepository configRepository = mock(IConfigRepository.class);
        ConfigService configService = new ConfigService(configRepository);
        Config config = new Config();

        configService.updateConfig(config);

        verify(configRepository).saveConfig(config);
    }
}