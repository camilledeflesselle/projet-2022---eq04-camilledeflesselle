package ca.ulaval.glo4002.cafe.application.config;

import ca.ulaval.glo4002.cafe.domain.config.Config;
import ca.ulaval.glo4002.cafe.domain.config.ConfigRepository;

public class ConfigService {
    private final ConfigRepository configRepository;

    public ConfigService(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    public void updateConfig(Config config) {
        this.configRepository.saveConfig(config);
    }
}
