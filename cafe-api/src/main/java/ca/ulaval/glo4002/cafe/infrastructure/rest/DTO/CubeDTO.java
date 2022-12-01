package ca.ulaval.glo4002.cafe.infrastructure.rest.DTO;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CubeDTO {
    private String name;
    private List<SeatDTO> seats = new ArrayList<>();

    public CubeDTO() {}

    public CubeDTO(String name, List<SeatDTO> seats) {
        this.name = name;
        this.seats = seats;
    }

    public String getName() {
        return this.name;
    }

    public List<SeatDTO> getSeats() {
        return this.seats;
    }
}
