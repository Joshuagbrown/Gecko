package seng202.team6.models;

/**
 * Represents a charger at a charging station.
 */
public class Charger {
    private String plugType;
    private String operative;
    private int wattage;

    /**
     * Constructor of the charger class.
     *
     * @param plugType  the plug type of the charger.
     * @param operative whether it is operative or not.
     * @param wattage   the wattage value at the charger.
     */
    public Charger(String plugType, String operative, int wattage) {
        this.plugType = plugType;
        this.operative = operative;
        this.wattage = wattage;
    }

    /**
     * Function to get the plug type of the charger.
     *
     * @return plug type of the charger.
     */
    public String getPlugType() {
        return plugType;
    }

    /**
     * Function to get the operative of the charger.
     *
     * @return the operative situation of the charger.
     */
    public String getOperative() {
        return operative;
    }

    /**
     * Function to get the wattage of the charger.
     *
     * @return the wattage of the charger.
     */
    public int getWattage() {
        return wattage;
    }

    /**
     * Returns the string value of this charger.
     * @return The string value of this charger
     */
    public String toString() {
        return this.wattage + " kW " + plugType + ", Status: " + operative;
    }
}
