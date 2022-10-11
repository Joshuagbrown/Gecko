package seng202.team6.models;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the Journey class.
 */
public class JourneyTest {

    /**
     * Test the equals when true.
     */
    @Test
    public void testEqualsSucceed() {
        List<String> addresses1 = Arrays.asList("3 Pine St", "5 Dixon St", "18 Geraldineson St", "1 Worcester Ln");
        List<String> addresses2 = Arrays.asList("3 Pine St", "5 Dixon St", "18 Geraldineson St", "1 Worcester Ln");
        Journey journey1 = new Journey(addresses1, "Lucas");
        Journey journey2 = new Journey(addresses2, "Lucas");
        assertTrue(journey2.equals(journey1));
    }

    /**
     * Test the equals when false, wrong address.
     */
    @Test
    public void testEqualsFailAddress() {
        List<String> addresses1 = Arrays.asList("3 Pine St", "5 Dixon St", "18 Geraldineson St", "1 Worcester Ln");
        List<String> addresses2 = Arrays.asList("2 Pine St", "5 Dixon St", "18 Geraldineson St", "1 Worcester Ln");
        Journey journey1 = new Journey(addresses1, "Lucas");
        Journey journey2 = new Journey(addresses2, "Lucas");
        assertFalse(journey2.equals(journey1));
    }

    /**
     * Test the equals when false, wrong name.
     */
    @Test
    public void testEqualsFailName() {
        List<String> addresses1 = Arrays.asList("3 Pine St", "5 Dixon St", "18 Geraldineson St", "1 Worcester Ln");
        List<String> addresses2 = Arrays.asList("3 Pine St", "5 Dixon St", "18 Geraldineson St", "1 Worcester Ln");
        Journey journey1 = new Journey(addresses1, "Luca");
        Journey journey2 = new Journey(addresses2, "Lucas");
        assertFalse(journey2.equals(journey1));
    }
}