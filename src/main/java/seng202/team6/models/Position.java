package seng202.team6.models;

import java.util.Objects;

/**
 * Represents a 2D position vector.
 */
public class Position {
    private double latitude;
    private double longitude;

    /**
     * The constructor class of the position.
     * @param first the longitude of a location.
     * @param second the latitude of the location.
     */
    public Position(double first, double second) {
        this.latitude = first;
        this.longitude = second;
    }

    /**
     * Function to get the latitude of the location.
     * @return the latitude of the location.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Function to get the longitude of the location.
     * @return longitude of the location.
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * The override method of equal method.
     * @param o object that want to compare.
     * @return boolean of the result.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return Double.compare(position.latitude, latitude) == 0
                && Double.compare(position.longitude, longitude) == 0;
    }

    /**
     * Get the hash code.
     * @return hash code of the location.
     */
    @Override
    public int hashCode() {
        return Objects.hash(latitude, latitude);
    }


}
