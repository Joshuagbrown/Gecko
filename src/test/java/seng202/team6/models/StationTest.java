package seng202.team6.models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StationTest {

    @Test
    void testEquals() {
        Position coordinates = new Position(-43, 171);
        int objectId = 1;
        String name = "testName";
        String notName = "testNotName";
        String operator = "testOperator";
        String owner = "testOwner";
        String address = "testAddress";
        int timeLimit = 10;
        boolean is24Hours = true;
        Charger charger = new Charger("testPlug", "testOperative", 10);
        List<Charger> chargers = new ArrayList<>();
        chargers.add(charger);
        int numberOfCarParks = 1;
        boolean carParkCost = true;
        boolean chargingCost = true;
        boolean hasTouristAttraction = true;
        Station station1 = new Station(coordinates, name, objectId, operator, owner,
                address, timeLimit, is24Hours, chargers, numberOfCarParks, carParkCost, chargingCost, hasTouristAttraction);
        Station station2 = new Station(coordinates, name, objectId, operator, owner,
                address, timeLimit, is24Hours, chargers, numberOfCarParks, carParkCost, chargingCost, hasTouristAttraction);
        boolean equal = station1.equals(station2);
        Station station3 = new Station(coordinates, notName, objectId, operator, owner,
                address, timeLimit, is24Hours, chargers, numberOfCarParks, carParkCost, chargingCost, hasTouristAttraction);
        boolean equal2 = station1.equals(station3);
        assertEquals(true, equal);
        assertEquals(false, equal2);
    }

    @Test
    void testToString() {
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
        String string = "Station Name : " + name + "\n"
                + "Coordinate : " + coordinates.getLatitude() + ","
                + coordinates.getLongitude() + "\n"
                + "ObjectId : " + objectId + "\n"
                + "Operator : " + operator + "\n"
                + "Owner : " + owner + "\n"
                + "Address : " + address + "\n"
                + "Time Limit : " + timeLimit + "\n"
                + "Is 24 Hour : " + is24Hours + "\n"
                + "Charger : " + "\n"
                + "Number Of CarPark : " + numberOfCarParks + "\n"
                + "CarPark Cost : " + carParkCost + "\n"
                + "Charging Cost : " + chargingCost + "\n"
                + "Has Tourist Attraction : " + hasTouristAttraction;
        String testString = "Station Name : " + "testName" + "\n"
                + "Coordinate : " + "-43.0" + ","
                + "171.0" + "\n"
                + "ObjectId : " + "1" + "\n"
                + "Operator : " + "testOperator" + "\n"
                + "Owner : " + "testOwner" + "\n"
                + "Address : " + "testAddress" + "\n"
                + "Time Limit : " + "10" + "\n"
                + "Is 24 Hour : " + "true" + "\n"
                + "Charger : " + "\n"
                + "Number Of CarPark : " + "1" + "\n"
                + "CarPark Cost : " + "true" + "\n"
                + "Charging Cost : " + "true" + "\n"
                + "Has Tourist Attraction : " + "true";
        assertEquals(string, testString);
    }
}