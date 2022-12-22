package ca.ulaval.glo4002.cafe.ui.rest.DTO;

import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import ca.ulaval.glo4002.cafe.domain.seat.SeatStatus;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SeatDTO {
    private Integer number;
    private SeatStatus status;
    private String customerId;
    private String groupName;

    public SeatDTO() {}

    public SeatDTO(SeatId seatId, SeatStatus status, CustomerId customerId, String groupName) {
        this.number = seatId != null ? seatId.getId() : null;
        this.status = status;
        this.customerId = customerId != null ? customerId.getId() : null;
        this.groupName = groupName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getGroupName() {
        return this.groupName;
    }
}
