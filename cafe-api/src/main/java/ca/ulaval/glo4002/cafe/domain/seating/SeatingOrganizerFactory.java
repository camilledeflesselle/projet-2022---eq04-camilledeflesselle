package ca.ulaval.glo4002.cafe.domain.seating;

import ca.ulaval.glo4002.cafe.domain.cube.Cube;

import java.util.List;

public class SeatingOrganizerFactory {
    public SeatingOrganizer createSeatingOrganizer(List<Cube> cubes) {
        return new SeatingOrganizer(cubes);
    }
}
