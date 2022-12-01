package ca.ulaval.glo4002.cafe.domain.reservation;

public class Group {
    private final String groupName;
    private final Integer groupSize;
    private final int MINIMUM_GROUP_SIZE = 2;

    public Group(String groupName, Integer groupSize) {
        if (groupSize < MINIMUM_GROUP_SIZE) throw new InvalidGroupSizeException();
        this.groupName = groupName;
        this.groupSize = groupSize;
    }

    public String getName() {
        return this.groupName;
    }

    public Integer getSize() {
        return this.groupSize;
    }
}
