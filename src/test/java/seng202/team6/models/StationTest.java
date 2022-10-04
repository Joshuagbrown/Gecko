package seng202.team6.models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the Station class.
 */
public class StationTest {
    /**
     * Test the toString class.
     */
    @Test
    public void testToString() {
        Position coordinates = new Position(-43, 171);
        int objectId = 1;
        String name = "testName";
        String operator = "testOperator";
        String owner = "testOwner";
        String address = "testAddress";
        int timeLimit = 10;
        boolean is24Hours = true;
        int numberOfCarParks = 1;
        boolean carParkCost = true;
        boolean chargingCost = true;
        boolean hasTouristAttraction = true;
        Station station = new Station(coordinates, name, objectId, operator, owner, address,
                timeLimit, is24Hours, new ArrayList<>(), numberOfCarParks, carParkCost,
                chargingCost, hasTouristAttraction);
        String testString = "Station Name : " + "testName" + "\n"
                + "Coordinate : " + "-43.0" + ","
                + "171.0" + "\n"
                + "ObjectId : " + "1" + "\n"
                + "Operator : " + "testOperator" + "\n"
                + "Owner : " + "testOwner" + "\n"
                + "Address : " + "testAddress" + "\n"
                + "Time Limit : " + "10" + "\n"
                + "Is 24 Hour : " + "true" + "\n"
                + "Chargers : " + "\n\n"
                + "Number Of CarPark : " + "1" + "\n"
                + "CarPark Cost : " + "true" + "\n"
                + "Charging Cost : " + "true" + "\n"
                + "Has Tourist Attraction : " + "true";
        assertEquals(testString, station.toString());
    }
}