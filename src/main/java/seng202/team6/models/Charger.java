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

    /**
     * Constructor of the charger class.
     * @param plugType the plug type of the charger.
     * @param operative whether it is operative or not.
     * @param wattage the wattage value at the charger.
     */
    public Charger(String plugType, String operative, int wattage) {
        this.plugType = plugType;
        this.operative = operative;
        this.wattage = wattage;
    }

    /**
     * Function to get the plug type of the charger.
     * @return plug type of the charger.
     */
    public String getPlugType() {
        return plugType;
    }

    /**
     * Function to get the operative of the charger.
     * @return the operative situation of the charger.
     */
    public String getOperative() {
        return operative;
    }

    /**
     * Function to get the wattage of the charger.
     * @return the wattage of the charger.
     */
    public int getWattage() {
        return wattage;
    }

    /**
     * Overwrite method of the class.
     * @param o the object want to compare.
     * @return boolean value of true or false.
     */
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

    /**
     * Overwrite the hash code of the class.
     * @return the has code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(plugType, operative, wattage);
    }

    public String toString() {
        return getWattage() + "kW " + getPlugType() + ", Status: " + getOperative();
    }
}
