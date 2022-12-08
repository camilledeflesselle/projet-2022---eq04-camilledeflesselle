package ca.ulaval.glo4002.cafe.infrastructure.rest.DTO;

import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CustomerDTO {
    private String name;
    private int seatNumber;
    private String groupName;

    public CustomerDTO() {}

    public CustomerDTO(String name, SeatId seatId, String groupName) {
        this.name = name;
        this.seatNumber = seatId != null ? seatId.getId() : -1;
        this.groupName = groupName;
    }

    public String getName() {
        return this.name;
    }

    public String getGroupName() {
        return this.groupName;
    }
}
