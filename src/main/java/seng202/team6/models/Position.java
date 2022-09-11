package seng202.team6.models;

import java.util.Objects;

/**
 * Represents a 2D position vector.
 */
public class Position {
    private double first;
    private double second;

    public Position(double first, double second) {
        this.first = first;
        this.second = second;
    }

    public double getFirst() {
        return first;
    }

    public double getSecond() {
        return second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return Double.compare(position.first, first) == 0 && Double.compare(position.second, second) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, first);
    }


}
