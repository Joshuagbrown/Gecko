package seng202.team6.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void testEquals() {
        Position position1 = new Position(-43, 171);
        Position position2 = new Position(-43, 171);
        Position position3 = new Position(-43, 181);
        boolean equal = position1.equals(position2);
        boolean equal2 = position1.equals(position3);
        assertEquals(true, equal);
        assertEquals(false, equal2);
    }

}