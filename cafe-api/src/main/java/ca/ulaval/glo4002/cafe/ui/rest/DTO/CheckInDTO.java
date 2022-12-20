package ca.ulaval.glo4002.cafe.ui.rest.DTO;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CheckInDTO {
    private String customerId;
    private String customerName;
    private String groupName;
    private int seatNumber;

    public CheckInDTO() {}

    public CheckInDTO(String customerId, String customerName, String groupName, Integer seatNumber) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.groupName = groupName;
        this.seatNumber = seatNumber;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public String getGroupName() {
        return this.groupName;
    }
}
