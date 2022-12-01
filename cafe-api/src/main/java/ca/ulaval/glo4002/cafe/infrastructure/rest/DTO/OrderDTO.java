package ca.ulaval.glo4002.cafe.infrastructure.rest.DTO;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderDTO {
    private List<String> orders;

    public OrderDTO() {}

    public OrderDTO(List<String> orders) {
        this.orders = orders;
    }

    public List<String> getOrders() {
        return this.orders;
    }
}
