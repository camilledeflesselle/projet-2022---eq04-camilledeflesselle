package ca.ulaval.glo4002.cafe.domain.config;

public interface IConfigRepository {
    Config findConfig();

    void saveConfig(Config config);
}
