package seng202.team6.models;

import java.util.List;
import java.util.Objects;

/**
 * Represents a charging station.
 */
public class Station {
    private Position coordinates;
    private int objectId;
    private String name;
    private String operator;
    private String owner;
    private String address;
    private int timeLimit;
    private boolean is24Hours;
    private List<Charger> chargers;
    private int numberOfCarParks;
    private boolean carParkCost;
    private boolean chargingCost;
    private boolean hasTouristAttraction;

    /**
     * We currently only instantiate a few of the fields.
     * This will change in the future
     */
    public Station(Position coordinates, String name, int objectId, String operator, String owner,
                   String address, int timeLimit, boolean is24Hours, List<Charger> chargers,
                   int numberOfCarparks, boolean carparkCost, boolean chargingCost,
                   boolean hasTouristAttraction) {

        this.coordinates = coordinates;
        this.name = name;
        this.objectId = objectId;
        this.operator = operator;
        this.owner = owner;
        this.address = address;
        this.timeLimit = timeLimit;
        this.is24Hours = is24Hours;
        this.chargers = chargers;
        this.numberOfCarParks = numberOfCarparks;
        this.carParkCost = carparkCost;
        this.chargingCost = chargingCost;
        this.hasTouristAttraction = hasTouristAttraction;
    }

    public Position getCoordinates() {
        return coordinates;
    }

    public String getName() {
        return name;
    }

    public int getObjectId() {
        return objectId;
    }

    public String getOperator() {
        return operator;
    }

    public String getOwner() {
        return owner;
    }

    public String getAddress() {
        return address;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public boolean is24Hours() {
        return is24Hours;
    }

    public List<Charger> getChargers() {
        return chargers;
    }

    public int getNumberOfCarParks() {
        return numberOfCarParks;
    }

    public boolean isCarparkCost() {
        return carParkCost;
    }

    public boolean isChargingCost() {
        return chargingCost;
    }

    public boolean isHasTouristAttraction() {
        return hasTouristAttraction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Station station = (Station) o;
        return objectId == station.objectId && timeLimit == station.timeLimit
                && is24Hours == station.is24Hours && numberOfCarParks == station.numberOfCarParks
                && carParkCost == station.carParkCost && chargingCost == station.chargingCost
                && hasTouristAttraction == station.hasTouristAttraction
                && Objects.equals(coordinates, station.coordinates)
                && Objects.equals(name, station.name)
                && Objects.equals(operator, station.operator)
                && Objects.equals(owner, station.owner)
                && Objects.equals(address, station.address)
                && Objects.equals(chargers, station.chargers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates, objectId, name, operator, owner, address,
                timeLimit, is24Hours, chargers, numberOfCarParks, carParkCost,
                chargingCost, hasTouristAttraction);
    }

    /**
     * Funtion which give a string explanation of station.
     * @return station information in string.
     */
    public String toString() {

        return "Station Name : " + name + "\n"
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
    }
}

