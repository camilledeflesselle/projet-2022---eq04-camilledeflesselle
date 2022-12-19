package ca.ulaval.glo4002.cafe.application.config;

import ca.ulaval.glo4002.cafe.domain.config.Config;
import ca.ulaval.glo4002.cafe.domain.config.IConfigRepository;

public class ConfigService {
    private final IConfigRepository configRepository;

    public ConfigService(IConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    public void updateConfig(Config config) {
        this.configRepository.saveConfig(config);
    }
}
