package ca.ulaval.glo4002.cafe.infrastructure.rest.DTO;

import ca.ulaval.glo4002.cafe.domain.reservation.Group;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GroupDTO {
    private String groupName;
    private Integer groupSize;

    public GroupDTO() {}

    public GroupDTO(String groupName, Integer groupSize) {
        this.groupName = groupName;
        this.groupSize = groupSize;
    }

    public GroupDTO(Group group) {
        this(group.getName(), group.getSize());
    }

    public String getGroupName() {
        return this.groupName;
    }

    public Integer getGroupSize() {
        return this.groupSize;
    }
}
