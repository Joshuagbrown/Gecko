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
    private List<Charger> chargers;

    /**
     * We currently only instantiate a few of the fields.
     * This will change in the future
     */
    public Station(Position coordinates, String name) {
        this.coordinates = coordinates;
        this.name = name;
    }

    public Position getCoordinates() {
        return coordinates;
    }

    public String getName() {
        return name;
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
        return objectId == station.objectId && timeLimit == station.timeLimit && Objects.equals(coordinates, station.coordinates) && Objects.equals(name, station.name) && Objects.equals(operator, station.operator) && Objects.equals(owner, station.owner) && Objects.equals(address, station.address) && Objects.equals(chargers, station.chargers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates, objectId, name, operator, owner, address, timeLimit, chargers);
    }

    public String toString(){

        return name ;
    }
}
