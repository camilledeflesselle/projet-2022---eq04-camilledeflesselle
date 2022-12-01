package ca.ulaval.glo4002.cafe.domain.reservation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class GroupTest {
    private static final String A_GROUP_NAME = "Team 4";

    @Test
    public void whenGroupSizeLessThanTwo_thenCannotInitializeGroup() {
        assertThrows(InvalidGroupSizeException.class, () -> new Group(A_GROUP_NAME, 1));
    }
}
