package seng202.team6.models;

import java.util.List;

/**
 * Represents a charging station
 */
public class Station {
    private Position coordinates;
    private int objectId;
    private String name;
    private String operator;
    private String owner;
    private String address;
    private int timeLimit;
    private List<Charger> chargers;

    /**
     * We currently only instantiate a few of the fields.
     * This will change in the future
     */
    public Station(Position coordinates, String name) {
        this.coordinates = coordinates;
        this.name = name;
    }
}
