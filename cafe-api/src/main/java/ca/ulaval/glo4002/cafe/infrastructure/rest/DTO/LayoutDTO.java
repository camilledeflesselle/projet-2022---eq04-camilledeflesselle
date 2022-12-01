package ca.ulaval.glo4002.cafe.infrastructure.rest.DTO;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LayoutDTO {
    private String name;
    private List<CubeDTO> cubes = new ArrayList<>();

    public LayoutDTO() {}

    public LayoutDTO(String name, List<CubeDTO> cubes) {
        this.name = name;
        this.cubes = cubes;
    }

    public String getName() {
        return this.name;
    }

    public List<CubeDTO> getCubes() {
        return this.cubes;
    }
}
