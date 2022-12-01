package ca.ulaval.glo4002.cafe.domain.seat;

public class Seat {
    private final SeatId seatId;
    private SeatStatus status;
    private String groupName;

    public Seat(int seatId, SeatStatus status) {
        this.seatId = new SeatId(seatId);
        this.status = status;
    }

    public Seat(int seatId) {
        this(seatId, SeatStatus.Available);
    }

    public String getGroupName() {
        return this.groupName;
    }

    public SeatId getId() {
        return seatId;
    }

    public SeatStatus getStatus() {
        return this.status;
    }

    public boolean isAvailable() {
        return this.status == SeatStatus.Available;
    }

    public void assign() {
        this.status = SeatStatus.Occupied;
    }

    public void reserve(String groupName) {
        this.status = SeatStatus.Reserved;
        this.groupName = groupName;
    }

    public void unassign() {
        this.status = SeatStatus.Available;
        this.groupName = null;

    }
}
