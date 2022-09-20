package seng202.team6.models;

import java.util.Objects;

/**
 * Represents a charger at a charging station.
 */
public class Charger {
    /* TODO: make into an enum */
    private String plugType;
    private String operative;
    private int wattage;

    public Charger(String plugType, String operative, int wattage) {
        this.plugType = plugType;
        this.operative = operative;
        this.wattage = wattage;
    }

    public String getPlugType() {
        return plugType;
    }

    public String getOperative() {
        return operative;
    }

    public int getWattage() {
        return wattage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Charger charger = (Charger) o;
        return operative == charger.operative && wattage == charger.wattage
                && plugType.equals(charger.plugType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plugType, operative, wattage);
    }
}
