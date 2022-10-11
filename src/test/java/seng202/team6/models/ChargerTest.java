package seng202.team6.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test the Charger class.
 */
public class ChargerTest {

    /**
     * Test the equals when true.
     */
    @Test
    public void testEqualsSucceed() {
        Charger charger1 = new Charger("Type 1", "Operative 1", 3000);
        Charger charger2 = new Charger("Type 1", "Operative 1", 3000);
        assertTrue(charger1.equals(charger2));
    }

    /**
     * Test the equals when false, wrong plug type.
     */
    @Test
    public void testEqualsFailPlugType() {
        Charger charger1 = new Charger("Type 1", "Operative 1", 3000);
        Charger charger2 = new Charger("Type 2", "Operative 1", 3000);
        assertFalse(charger1.equals(charger2));
    }

    /**
     * Test the equals when false, wrong operative.
     */
    @Test
    public void testEqualsFailOperative() {
        Charger charger1 = new Charger("Type 1", "Operative 1", 3000);
        Charger charger2 = new Charger("Type 1", "Operative 2", 3000);
        assertFalse(charger1.equals(charger2));
    }

    /**
     * Test the equals when false, wrong wattage.
     */
    @Test
    public void testEqualsFailWattage() {
        Charger charger1 = new Charger("Type 1", "Operative 1", 3000);
        Charger charger2 = new Charger("Type 1", "Operative 1", 2999);
        assertFalse(charger1.equals(charger2));
    }
}