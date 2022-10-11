package seng202.team6.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test the Position class.
 */
public class PositionTest {

    /**
     * Test the equals when true.
     */
    @Test
    public void testEqualsSucceed() {
        Position position1 = new Position(1,1);
        Position position2 = new Position(1,1);
        assertTrue(position1.equals(position2));
    }

    /**
     * Test the equals when false.
     */
    @Test
    public void testEqualsFailAddress() {
        Position position1 = new Position(1,1);
        Position position2 = new Position(2,2);
        assertFalse(position1.equals(position2));
    }
}