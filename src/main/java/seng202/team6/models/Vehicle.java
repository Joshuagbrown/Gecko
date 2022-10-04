package seng202.team6.models;

/**
 * Represents a vehicle in the system.
 */
public class Vehicle {

    String make;
    String model;
    String plugType;
    int year;

    int userId;

    /**

     * Constructor class, takes in vehicle information.
     * @param make The vehicle make, or brand
     * @param model The model of the vehicle
     * @param plugType The type of charger on the vehicle
     * @param year The year the vehicle was produced
     * @param userId The user that owns the vehicle
     */
    public Vehicle(String make, String model, String plugType, int year, int userId) {
        this.make = make;
        this.model = model;
        this.plugType = plugType;
        this.year = year;
        this.userId = userId;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPlugType() {
        return plugType;
    }

    public void setPlugType(String plugType) {
        this.plugType = plugType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}