package seng202.team6.models;

import java.util.Objects;

/**
 * Represents a charger at a charging station.
 */
public class Charger {
    private int chargerId = -1;
    private String plugType;
    private String operative;
    private int wattage;

    /**
     * Constructor of the charger class.
     *
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
     * Constructor for a charger with a chargerId too, useful for database updates.
     *
     * @param plugType the plug type of the charger.
     * @param operative whether it is operative or not.
     * @param wattage the wattage value at the charger.
     * @param chargerId charger id of the charger from the database
     */
    public Charger(String plugType, String operative, int wattage, int chargerId) {
        this(plugType, operative, wattage);
        this.chargerId = chargerId;
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
     * Function to get charger ID.
     * @return the charger id
     */
    public int getChargerId() {
        return chargerId;
    }


    /**
     * Function to set Wattage.
     * @param watts the new number of watts
     */
    public void setWattage(int watts) {
        wattage = watts;
    }


    /**
     * Funciton to set the plugType of the charger.
     * @param type the new charger type
     */
    public void setPlugType(String type) {
        plugType = type;
    }

    /**
     * Funciton to set the new operative status of the charger.
     * @param op the new operative status
     */
    public void setOperative(String op) {
        operative = op;
    }


    /**
     * This does NOT take into account chargerId.
     * @param o Object to compare
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Charger charger = (Charger) o;
        return wattage == charger.wattage && plugType.equals(charger.plugType) && operative.equals(charger.operative);
    }

    /**
     * This does NOT take into account chargerId.
     */
    @Override
    public int hashCode() {
        return Objects.hash(plugType, operative, wattage);
    }

    /**
     * Returns the string value of this charger.
     * @return The string value of this charger
     */
    public String toString() {
        return this.wattage + " kW " + plugType + ", Status: " + operative;
    }
}
