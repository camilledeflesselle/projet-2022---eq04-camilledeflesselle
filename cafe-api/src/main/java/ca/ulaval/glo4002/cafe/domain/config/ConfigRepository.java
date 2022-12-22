package ca.ulaval.glo4002.cafe.domain.config;

public interface ConfigRepository {
    Config findConfig();

    void saveConfig(Config config);
}
