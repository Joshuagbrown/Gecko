package seng202.team6.models;

import java.util.Objects;

/**
 * Represents a vehicle in the system.
 */
public class Vehicle {

    private int vehicleId = -1;
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

    /**

     * Constructor class, takes in vehicle information.
     * @param make The vehicle make, or brand.
     * @param model The model of the vehicle.
     * @param plugType The type of charger on the vehicle.
     * @param year The year the vehicle was produced.
     * @param userId The user that owns the vehicle.
     * @param vehicleId the id of the vehicle from the database.
     */
    public Vehicle(String make, String model, String plugType, int year, int userId,int vehicleId) {
        this.make = make;
        this.model = model;
        this.plugType = plugType;
        this.year = year;
        this.userId = userId;
        this.vehicleId = vehicleId;
    }

    public int getVehicleId() {
        return vehicleId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vehicle vehicle = (Vehicle) o;
        return year == vehicle.year && userId == vehicle.userId && make.equals(vehicle.make)
                && model.equals(vehicle.model) && plugType.equals(vehicle.plugType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(make, model, plugType, year, userId);
    }
}
