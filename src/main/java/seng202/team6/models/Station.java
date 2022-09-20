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
     * The constructor of the station with related information.
     * @param coordinates the coordinate of the station.
     * @param name the name of the station.
     * @param objectId the objectId of the station.
     * @param operator the operator of the station.
     * @param owner the owner of the station.
     * @param address the address of the station.
     * @param timeLimit the timeLimit of the station.
     * @param is24Hours the is24Hours of the station.
     * @param chargers the chargers of the station.
     * @param numberOfCarparks the number of car-parks of the station.
     * @param carparkCost the car-park Cost of the station.
     * @param chargingCost the chargingCost of the station.
     * @param hasTouristAttraction the hasTouristAttraction of the station.
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

    /**
     * Function to get the coordinate of the station.
     * @return the position of the station which include the lat and lng of the station.
     */
    public Position getCoordinates() {
        return coordinates;
    }

    /**
     * Function call to get the name of the station.
     * @return the name of the station.
     */
    public String getName() {
        return name;
    }

    /**
     * Function call to get the object id of the station.
     * @return the object id of the station.
     */
    public int getObjectId() {
        return objectId;
    }

    /**
     * Function call to get the operator of the station.
     * @return the operator of the station.
     */
    public String getOperator() {
        return operator;
    }

    /**
     * Function call to get the owner of the station.
     * @return the owner of the station.
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Function call to get the address of the station.
     * @return the address of the station.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Function call to get the time limit of the station.
     * @return the time limit of the station.
     */
    public int getTimeLimit() {
        return timeLimit;
    }

    /**
     * Function call to get whether the station operate 24 hours or not.
     * @return boolean value of yes or no.
     */
    public boolean is24Hours() {
        return is24Hours;
    }

    /**
     * Function call to get the list of charger of the station.
     * @return the list of charger of the station.
     */
    public List<Charger> getChargers() {
        return chargers;
    }

    /**
     * Function call to get the number of car-park of the station.
     * @return the car-park of the station.
     */
    public int getNumberOfCarParks() {
        return numberOfCarParks;
    }

    /**
     * Function call to get whether the station charged for parking car or not.
     * @return boolean value of yes or no.
     */
    public boolean isCarparkCost() {
        return carParkCost;
    }

    /**
     * Function call to get whether the station charged for charging car or not.
     * @return boolean value of yes or no.
     */
    public boolean isChargingCost() {
        return chargingCost;
    }

    /**
     * Function call to get whether the station has tourist attraction or not.
     * @return boolean value of yes or no.
     */
    public boolean isHasTouristAttraction() {
        return hasTouristAttraction;
    }

    /**
     * Override function of two station equal.
     * @param o object that want to compare.
     * @return boolean value of yes or no.
     */
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

    /**
     * Get the hash code of the station.
     * @return integer hash code.
     */
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

