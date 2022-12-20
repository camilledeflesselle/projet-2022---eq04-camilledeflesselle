package ca.ulaval.glo4002.cafe.ui.rest.DTO;

import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReservationDTO {
    private String groupName;
    private Integer groupSize;

    public ReservationDTO() {}

    public ReservationDTO(Reservation reservation) {
        this.groupName = reservation.getGroupName();
        this.groupSize = reservation.getSize();
    }

    public String getGroupName() {
        return this.groupName;
    }
}
