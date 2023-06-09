package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories;

import ca.ulaval.glo4002.cafe.domain.bill.TipRate;
import ca.ulaval.glo4002.cafe.domain.config.Config;
import ca.ulaval.glo4002.cafe.domain.config.ConfigRepository;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategy.GroupReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.tax.TaxRate;

import java.util.ArrayList;
import java.util.List;

public class ConfigRepositoryInMemory implements ConfigRepository {

    private Config config;

    public ConfigRepositoryInMemory() {
        String name = "Les 4-Fées";
        List<String> cubeNames = new ArrayList<>(List.of("Wanda", "Bloom", "Merryweather", "Tinker Bell"));
        int cubeSize = 4;
        TaxRate taxRate = new TaxRate(0f);
        TipRate defaultGroupTipRate = new TipRate(0.15f);
        this.config = new Config(name, cubeNames, cubeSize, taxRate, defaultGroupTipRate, GroupReservationStrategy.Default);
    }

    @Override
    public Config findConfig() {
        return this.config;
    }

    @Override
    public void saveConfig(Config config) {
        this.config = config;
    }
}
