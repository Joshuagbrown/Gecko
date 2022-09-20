package seng202.team6.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChargerTest {

    @Test
    void testEquals() {
        String plugType = "testPlug";
        String plugType2 = "testPlug2";
        String operative = "testOperative";
        int wattage = 10;
        Charger charger1 = new Charger(plugType, operative, wattage);
        Charger charger2 = new Charger(plugType, operative, wattage);
        Charger charger3 = new Charger(plugType2, operative, wattage);
        boolean equals1 = charger1.equals(charger2);
        boolean equals2 = charger1.equals(charger3);
        assertEquals(true, equals1);
        assertEquals(false, equals2);
    }
}