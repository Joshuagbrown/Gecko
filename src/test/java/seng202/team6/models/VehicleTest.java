package seng202.team6.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test the Vehicle class.
 */
public class VehicleTest {

    /**
     * Test the equals when true.
     */
    @Test
    public void testEqualsSucceed() {
        Vehicle vehicle1 = new Vehicle("Make", "Model", "PlugType", 2011, 1);
        Vehicle vehicle2 = new Vehicle("Make", "Model", "PlugType", 2011, 1);
        assertTrue(vehicle1.equals(vehicle2));
    }

    /**
     * Test the equals when false.
     */
    @Test
    public void testEqualsFailAddress() {
        Vehicle vehicle1 = new Vehicle("Make", "Model", "PlugType", 2011, 1);
        Vehicle vehicle2 = new Vehicle("Make", "Model", "PlugType", 2012, 1);
        assertFalse(vehicle1.equals(vehicle2));
    }
}